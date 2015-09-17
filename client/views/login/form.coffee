Template.loginForm.helpers
	userName: ->
		return Meteor.user()?.username

	btnLoginSave: ->
		switch Template.instance().state.get()
			when 'login'
				return t('Login')

	waitActivation: ->
		return Template.instance().state.get() is 'wait-activation'
		
Template.loginForm.events
	'submit #login-card': (event, instance) ->
		event.preventDefault()

		button = $(event.target).find('button.login')
		RocketChat.Button.loading(button)

		formData = instance.validate()
		if formData
			Meteor.loginWithJEDIS formData.emailOrUsername, formData.pass, (error) ->
				RocketChat.Button.reset(button)
				if error?
					if error.error is 'no-valid-email'
						instance.state.set 'email-verification'
					else
						toastr.error error.reason
					return
				FlowRouter.go 'index'


Template.loginForm.onCreated ->
	instance = @
	@state = new ReactiveVar('login')
	@validate = ->
		formData = $("#login-card").serializeArray()
		formObj = {}
		validationObj = {}

		for field in formData
			formObj[field.name] = field.value

		if instance.state.get() isnt 'login'
			unless formObj['email'] and /\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]+\b/i.test(formObj['email'])
				validationObj['email'] = t('Invalid_email')

		if instance.state.get() isnt 'forgot-password'
			unless formObj['pass']
				validationObj['pass'] = t('Invalid_pass')


		$("#login-card input").removeClass "error"
		unless _.isEmpty validationObj
			button = $('#login-card').find('button.login')
			RocketChat.Button.reset(button)
			$("#login-card h2").addClass "error"
			for key of validationObj
				$("#login-card input[name=#{key}]").addClass "error"
			return false

		$("#login-card h2").removeClass "error"
		$("#login-card input.error").removeClass "error"
		return formObj

Template.loginForm.onRendered ->
	Tracker.autorun =>
		switch this.state.get()
			when 'login'
				Meteor.defer ->
					$('input[name=email]').select().focus()
