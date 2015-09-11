Template.avatarPrompt.onCreated ->
	self = this
	self.upload = new ReactiveVar

Template.avatarPrompt.onRendered ->
	Tracker.afterFlush ->
		SideNav.setFlex "accountFlex"
		SideNav.openFlex()

Template.avatarPrompt.helpers

	upload: ->
		return Template.instance().upload.get()

	username: ->
		return Meteor.user()?.username

	initialsUsername: ->
		return '@'+Meteor.user()?.username

Template.avatarPrompt.events
	'click .select-service': ->
		if @service is 'initials'
			Meteor.call 'resetAvatar'
			updateAvatarOfUsername Meteor.user().username
			toastr.success t('Avatar_changed_successfully')
		else
			Meteor.call 'setAvatarFromService', @blob, @contentType, @service, ->
				updateAvatarOfUsername Meteor.user().username
				toastr.success t('Avatar_changed_successfully')


	'change .avatar-file-input': (event, template) ->
		e = event.originalEvent or event
		files = e.target.files
		if not files or files.length is 0
			files = e.dataTransfer?.files or []

		for blob in files
			if not /image\/.+/.test blob.type
				return

			reader = new FileReader()
			reader.readAsDataURL(blob)
			reader.onloadend = ->
				template.upload.set
					service: 'upload'
					contentType: blob.type
					blob: reader.result
