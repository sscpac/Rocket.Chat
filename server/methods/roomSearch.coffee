Meteor.methods
	roomSearch: (searchText, searchContentsFlag) ->

		# get calling username
		currentUser = Meteor.user().username

		console.log '[methods] roomSearch -> '.green, ' current user:', currentUser, ' searchText:', searchText, ' searchContentsFlag:', searchContentsFlag

		contentMatchingRoomIds = []

		# if the searchContentsFlag is set, look for the search text in the messages
		# of all rooms of which the user is a member
		if searchContentsFlag

			# find all rooms of which user is a member
			roomIds = _.pluck(ChatSubscription.find({'u.username': currentUser}, {fields: {rid: 1}}).fetch(), 'rid')

			# search the above rooms for the specified text (ignore case)
			for roomId in roomIds
				matchCount = ChatMessage.find({rid: roomId, msg: {$regex: searchText, $options: 'i'}}, {fields: {msg: 1}}).count()
				# if any matches found, add that room id to the list
				if matchCount > 0
					contentMatchingRoomIds.push roomId


		# return all rooms of which the user is a member, AND either the room is one of those
		# matched above or the room's name matches the search text
		result = ChatSubscription.find({
			$and: [
				{'u.username': currentUser},
				{
					$or: [
						{rid: {$in: contentMatchingRoomIds}},
						{name: {$regex: searchText, $options: 'i'}}
					]
				}
			]
		}, {
			sort: {ts: -1}
		}).fetch();

		return result