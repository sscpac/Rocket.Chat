Meteor.methods
	contactsDetails: (arguments) ->

		contactDetails = {}
		userId = Meteor.userId()
		if not userId
			throw new Meteor.Error('invalid-user', "[methods] createContactsGroup -> Invalid user")

		name = arguments.name
		users = []
		if name?
			ChatContacts.find({name:name, $or: [{ public: true}, {owner: userId}]}, { sort: { msgs:-1 } }).fetch()
				owner: userId
				listName: name
				users: users
				ts: now
		else
			users = arguments.users
				

		return contactDetails
