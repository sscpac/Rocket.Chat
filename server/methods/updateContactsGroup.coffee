Meteor.methods
	updateContactsGroup: (arguments) ->

		userId = Meteor.userId()
		if not userId
			throw new Meteor.Error('invalid-user', "[methods] addContacts -> Invalid user")

		name = arguments.name
		users = arguments.users || []

		ChatContacts.update
			owner: userId
			name: name
			$set: {users:users}

		return true
