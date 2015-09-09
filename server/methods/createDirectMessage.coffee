Meteor.methods
	createDirectMessage: (username, accessPermissions) ->
		if not Meteor.userId()
			throw new Meteor.Error 'invalid-user', "[methods] createDirectMessage -> Invalid user"

		console.log '[methods] createDirectMessage -> '.green, 'userId:', Meteor.userId(), 'arguments:', arguments

		me = Meteor.user()

		if me.username is username
			return

		to = Meteor.users.findOne
			username: username

		if not to
			throw new Meteor.Error('invalid-user', "[methods] createDirectMessage -> Invalid target user")

		if not accessPermissions
			throw new Meteor.Error('invalid-argument', "Missing security label")

		if not Jedis.securityLabelIsValid(accessPermissions)
			throw new Meteor.Error('invalid-access-permissions', "Missing required access permissions")

		result = Meteor.call 'canAccessResource', [me._id,to._id], accessPermissions
		if not result.canAccess
			deniedUserList = _.pluck(result.deniedUsers, 'user').join(', ')
			throw new Meteor.Error('invalid-access-permissions', deniedUserList + " cannot participate in a direct message with the specified access permissions")


		# Check if a direct message already exists with the same user and same permissions
		room = ChatRoom.findOne
			t: 'd'
			usernames:
				$all: [me.username, to.username]
			accessPermissions: accessPermissions

		# If already exists, just open that room (return its room id)
		if room
			rid = room._id

		# Otherwise, create a new room, and subscribe both users to it
		else
			now = new Date()

			# Create the new room
			rid = ChatRoom.insert
				usernames: [me.username, to.username]
				ts: now
				t: 'd'
				msgs: 0
				accessPermissions: accessPermissions
				securityLabels: Jedis.legacyLabel accessPermissions
				u:
					_id: me.username


			# Make user I have a subcription to this room
			ChatSubscription.insert
				rid: rid
				ts: now
				ls: now
				name: to.username
				displayName: to.name
				t: 'd'
				open: true
				alert: false
				unread: 0
				u:
					_id: me._id
					username: me.username

			# Make user the target user has a subcription to this room
			ChatSubscription.insert
				rid: rid
				name: me.username
				displayName: me.name
				t: 'd'
				open: false
				alert: false
				unread: 0
				u:
					_id: to._id
					username: to.username

		return {
			rid: rid
		}
