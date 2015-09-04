Meteor.methods
	eraseRoom: (rid) ->
		console.log '[methods] eraseRoom -> '.green, 'userId:', Meteor.userId(), 'arguments:', arguments

		room = ChatRoom.findOne rid
		user = Meteor.users.findOne Meteor.userId()

		unless user
			throw new Meteor.Error 401, 'User not found'
		unless room
			throw new Meteor.Error 400, 'Room not found'

		isCreator = room.u._id is user._id

		if user.admin is true or isCreator
			console.log '[methods] eraseRoom -> '.green, 'Erasing message, subscriptions, room'	

			# send room deleted message so that other users that have the room open will know to close it
			ChatMessage.insert
				rid: room._id
				ts: (new Date)
				t: 'dr'
				msg: room.displayName
				u:
					_id: Meteor.userId()
					username: Meteor.user().username

			ChatMessage.remove({rid: rid})
			ChatSubscription.remove({rid: rid})
			ChatRoom.remove(rid)

		else 
			console.log '[methods] eraseRoom -> '.red, 'Unauthorized'	
			switch room.t 
				when 'p'
					type = 'private group'
				when 'c'
					type = 'channel'
				when 'd'
					type = 'direct message'
			
			throw new Meteor.Error 401, 'Unauthorized to delete ', type
