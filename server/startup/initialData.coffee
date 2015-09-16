Meteor.startup ->
	Meteor.defer ->
		if not ChatRoom.findOne('name': 'general')?
			ChatRoom.insert
				_id: 'GENERAL'
				default: true
				usernames: []
				ts: new Date()
				t: 'c'
				name: 'general'
				displayName: 'General'
				msgs: 0
				accessPermissions: Jedis.channelPermissions()
				securityLabel : Jedis.legacyLabel(Jedis.channelPermissions())

