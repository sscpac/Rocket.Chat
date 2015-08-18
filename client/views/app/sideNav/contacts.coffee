Template.contacts.helpers
	contacts: ->
		return Meteor.users.find({},{ sort: {'profile.last_name': 1}});
