Jedis = this.Jedis || {};
Meteor.startup( function() {
	var settings;
	var directoryService;
	var users = [];
	var addToGeneralInterval;

	Meteor.users.find().observe({ 
		added: addUserToLocationChannel,
		changed: updateUserLocationChannel,
		removed: removeUserFromLocationChannel
		}
	)

	console.log("Loading Default System Settings");
	try {
		// JSON.parse is very syntax sensitive.  e.g. trailing comma with no following value 
	 	// will cause an error. 
	 	settings = JSON.parse(Assets.getText('defaultSettings.json')) || {};
		defineSettings(settings);

	} catch(err) {
	 	console.log('Error loading default settings: ' + err.message);
	}

	directoryService = new DirectoryService(settings.ldap);
	Jedis.accessManager = new AccessManager(directoryService);
	Jedis.accessManager.loadAccessPermissions();

	Jedis.accountManager = new AccountManager(directoryService);
	Jedis.accountManager.loadUsers();
	adminUserId = settings.mac.adminUsername;
	if( adminUserId ) {
		console.log('Set ' + adminUserId + ' as admin');
		Jedis.accountManager.setAdmin(adminUserId);
	}
	// need to add users to the 'GENERAL' channel (created in initialData), but 
	// it may not have been created yet.  So we need to periodically test if it 
	// exists then add them when it does.  
	// !! the delay value has to be long enough to account for adding all the users
	// otherwise it may try to re-insert the user into general  
	addToGeneralInterval = Meteor.setInterval( function() {
		var room = ChatRoom.findOne({_id:'GENERAL'})
		if( room ) {
			// returns non-jedis users that will be added to GENERAL room if they aren't already added
			users = Meteor.users.find().fetch();

			// based on setUsername.coffee that adds uesrs after registration.  We can't reuse it because
			// it checks Meteor.userId which doesn't apply to what we're doing
			addUsersToRoom(users, 'GENERAL', false);
			Meteor.clearInterval(addToGeneralInterval);
		}
	}, 10000)

	// Register our custom login manager that authenticates via LDAP with Meteor's accounts package
	Accounts.registerLoginHandler(Jedis.accountManager.authId, Jedis.accountManager.loginHandler);	
});

var addUsersToLocation = function() {
	var owner = Meteor.users.findOne({_id:'testadmin'});
	var rawUsers = Meteor.users.rawCollection();
	var pipeline = [{'$group': {_id:'$profile.location', usernames: {$push : "$username"}}}]
	Meteor.wrapAsync( rawUsers.aggregate, rawUsers)(pipeline, function(err, usersByLocation) {
		if( err ) {
			console.log( err )
		} else {
			usersByLocation.forEach(function(location) {
				var channel = ChatRoom.findOne({name:location._id, t:'c'});
				if( channel ) {
					// location exists so add user if they don't already belong
					addUsersToRoom(location.usernames, channel._id, false)
				} else {
					// location doesn't exist so create new channel with users
					createChannel(owner, location._id, location.usernames);
				}
			})
		}

	})
}

var addUserToLocationChannel = function(user) {
	var channel;
	var chId;
	if( _.isUndefined(user.profile) || _.isUndefined(user.profile.location) ) {
		console.log('User missing location in profile')
		return
	}
	location = user.profile.location
	slugLocation = _.slugify(location)
	channel = ChatRoom.findOne({name:slugLocation, t:'c'})
	if( _.isUndefined(channel) ) {
		chId = createChannel(slugLocation,location, [user.username]);
	} else {
		chId = channel._id;
	}
	addUsersToRoom( [user], chId, false)
}

var updateUserLocationChannel = function(newUser, oldUser) {
	var oldLocation = '';
	var newLocation = '';

	if( !_.isUndefined(oldUser.profile) && !_.isUndefined(oldUser.profile.location)) {
		oldLocation = oldUser.profile.location;
	}
	if( !_.isUndefined(newUser.profile) && !_.isUndefined(newUser.profile.location)) {
		newLocation = newUser.profile.location;
	}
	if( oldLocation === newLocation ) {
		return
	}

	removeUserFromLocationChannel(oldUser);
	addUserToLocationChannel(newUser);
}

var removeUserFromLocationChannel = function(user) {
	// similar to removeUserFromRoom.coffee, but we don't use the currently logged
	// in user
	if( _.isUndefined(user.profile) || _.isUndefined(user.profile.location) ) {
		console.log('User missing location in profile')
		return
	}
	console.log('remove user from location: ' + user.profile.location);
	room = ChatRoom.findOne({name: user.profile.location});
	if( _.isUndefined(room) ) {
		console.log('Unable to remove ' + user.username + ' from old location ' + user.profile.location + ' because it was not found.')
		return;
	}
	if (room.t !== 'c') {
		throw new Meteor.Error(403, 'Not allowed');
	}

	ChatRoom.update({_id:room._id}, {$pull: {usernames: user.username}});

	ChatSubscription.remove({ 'u._id': user.username, rid: room._id });

	/* 
	ChatMessage.insert
		rid: room._id
		ts: (new Date)
		t: 'ru'
		msg: user.name
		u:
			_id: Meteor.userId()
			username: Meteor.user().username
	*/
}

var addUsersToRoom = function( users, roomId, createJoinedMessage) {
	var now = new Date();
	var room = ChatRoom.findOne({_id: roomId})
	if( ! room ) {
		console.log( 'Room with id: ' + roomId + ' not found');
		return
	}
	// add usernames to specified room and create subscription.  
	users.forEach( function( user ) {

		if( ! ChatSubscription.findOne({rid: roomId, 'u._id' : user._id }) ) {
			// Non-existant ChatSubscription implies user missing from ChatRoom
			ChatRoom.update( {_id: roomId }, 
				{
					// can't use addToSet because sort doesn't work.
					$push : 
					{ usernames : 
						{
						$each : [user.username],
						$sort : 1
						}
					}
				});

			ChatSubscription.insert( {
				rid: roomId,
				name: room.name,
				displayName: room.displayName,
				ts: now,
				t: room.t,
				f: true,
				open: true,
				alert: false,
				unread : createJoinedMessage ? 1 : 0,
				u: { _id : user._id, username : user.username }
			});

			if( createJoinedMessage ) {
				ChatMessage.insert({
					rid: roomId,
					ts: now,
					t: 'uj',
					msg : '',
				u: { _id : user._id, username : user.username }
				})
			}

			console.log('Added ' + user.username + ' to room ' + room.name);
		}
	});
}

var createChannel = function(name, displayName, members) {
	// the same as createChannel method, except it doesn't check for logged in user
	if( !(/^[0-9a-z-_\s.]+$/i).test(name)) {
		throw new Meteor.Error( 'name-invalid', 'Channel name is invalid' );
	}

	var now = new Date()

	// avoid duplicate names
	if (ChatRoom.findOne({name:name})) {
		throw new Meteor.Error ('duplicate-name', 'Channel name exists')
	}

	console.log('Creating new channel: ' + name);

	room = {
		usernames: members,
		ts: now,
		t: 'c',
		name: name,
		displayName: displayName,
		msgs: 0,
		accessPermissions: Jedis.channelPermissions(),
		securityLabel : Jedis.legacyLabel(Jedis.channelPermissions())	
	}

	//RocketChat.callbacks.run('beforeCreateChannel', owner, room);

	// create new room
	var rid = ChatRoom.insert( room )

	members.forEach(function(username) {
		member = Meteor.users.findOne({username: username})
		if (member) {

			sub = {
				rid: rid,
				ts: now,
				name: name,
				displayName: displayName,
				t: 'c',
				f: true,
				unread: 0,
				u: {
					_id: member._id,
					username: username
				},
				ls  : now,
				open : true,
				alert : false
			}

			ChatSubscription.insert (sub);
		}
	});

	/*
	Meteor.defer(function() { 
		RocketChat.callbacks.run( 'afterCreateChannel', owner, room);
	});
	 */

	return rid;
}

var defineSettings = function(config) {
	// NOTE: initial values do NOT overwrite existing values. You have to use the admin UI to modify the setting
	// once it exists in the collection
	RocketChat.settings.addGroup( 'MAC');
	// NOTE RocketChat only supports string and boolean values.
	RocketChat.settings.add('Network_Classification', config.mac.networkClassification, { type: 'string', group: 'MAC', public: true });
	RocketChat.settings.add('System_CountryCode', config.mac.systemCountryCode, { type: 'string', group: 'MAC', public: true });	

	/*
	// It doesn't make sense to configure these settings through the UI because they are read in at startup
	// and require a system restart to take effect
	RocketChat.settings.add('Admin Username', config.mac.adminUsername, { type: 'string', group: 'MAC', public: true });	

	RocketChat.settings.addGroup( 'LDAP');
	RocketChat.settings.add('url', config.ldap.url, { type: 'string', group: 'LDAP', public: false });	
	RocketChat.settings.add('userSearchBaseDNTemplate', config.ldap.userSearchBaseDNTemplate, { type: 'string', group: 'LDAP', public: false });	
	RocketChat.settings.add('adminBaseDN', config.ldap.admin.baseDN, { type: 'string', group: 'LDAP', public: false });	
	RocketChat.settings.add('adminCredentials', config.ldap.admin.credentials, { type: 'string', group: 'LDAP', public: false });	
	RocketChat.settings.add('accessDefsBaseDN', config.ldap.accessDefs.baseDN, { type: 'string', group: 'LDAP', public: false });	
	RocketChat.settings.add('accessDefsFilter', config.ldap.accessDefs.opts.filter, { type: 'string', group: 'LDAP', public: false });	
	RocketChat.settings.add('accessDefsScope', config.ldap.accessDefs.opts.scope, { type: 'string', group: 'LDAP', public: false });	
	RocketChat.settings.add('usersBaseDN', config.ldap.users.baseDN, { type: 'string', group: 'LDAP', public: false });	
	RocketChat.settings.add('usersFilter', config.ldap.users.opts.filter, { type: 'string', group: 'LDAP', public: false });	
	RocketChat.settings.add('usersScope', config.ldap.users.opts.scope, { type: 'string', group: 'LDAP', public: false });	
	*/
}


