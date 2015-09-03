Meteor.publish 'room', (rid) ->
	unless this.userId
		return this.ready()

	console.log '[publish] room ->'.green, 'arguments:', arguments

	# if a room id is specified, make sure it is valid and that the user can access,
	# then return that single room record
	if rid
		if typeof rid isnt 'string'
			return this.ready()

		if not Meteor.call 'canAccessRoom', rid, this.userId
			return this.ready()

		ChatRoom.find
			_id: rid
		,
			fields:
				name: 1
				t: 1
				cl: 1
				u: 1
				usernames: 1
				accessPermissions: 1

	# if no room id is specified, return all room records of which the user is a member
	# (membership implies the user can access)
	else
		ChatRoom.find
			usernames: this.userId
		,
			fields:
				name: 1
				t: 1
				cl: 1
				u: 1
				usernames: 1
				accessPermissions: 1
