Meteor.methods
	addContacts: (rid, contacts) ->
		if not Meteor.userId()
			throw new Meteor.Error('invalid-user', "[methods] sendMessage -> Invalid user")


		return true
