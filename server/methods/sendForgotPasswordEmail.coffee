Meteor.methods
	sendForgotPasswordEmail: (email) ->
		throw new Meteor.Error 403, 'Send forgotten password not allowed'
		###	
		user = Meteor.users.findOne {'emails.address': email}

		if user?
			Accounts.sendResetPasswordEmail(user._id, email)
			return true
		return false
		###