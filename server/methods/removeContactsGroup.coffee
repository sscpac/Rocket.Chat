Meteor.methods
	removeContactsGroup: (arguments) ->

		userId = Meteor.userId()
		if not userId
			throw new Meteor.Error('invalid-user', "[methods] removeContactsGroup -> Invalid user")

		now = new Date()
		name = arguments.name

		ChatContacts.remove
			owner: userId
			name: name

		return true
