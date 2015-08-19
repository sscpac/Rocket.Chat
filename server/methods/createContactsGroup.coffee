Meteor.methods
	createContactsGroup: (arguments) ->

		userId = Meteor.userId()
		if not userId
			throw new Meteor.Error('invalid-user', "[methods] createContactsGroup -> Invalid user")

		now = new Date()
		name = arguments.name
		users = arguments.users || []
		createdBy = arguments.createdBy || userId
		isPublic = arguments.public || false

		ChatContacts.insert
			owner: userId
			createdBy: createdBy
			listName: name
			users: users
			public : isPublic
			popularity: 0
			ts: now

		return true
