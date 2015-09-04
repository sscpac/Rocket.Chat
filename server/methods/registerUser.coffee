Meteor.methods
	registerUser: (formData) ->
		throw new Meteor.Error 403, 'Account registration not allowed'
