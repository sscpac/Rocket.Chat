Meteor.methods
	getAvatarSuggestion: ->
		if not Meteor.userId()
			throw new Meteor.Error 203, '[methods] getAvatarSuggestion -> Usuário não logado'

		user = Meteor.user()
		console.log 'Removed'.red

		return []
