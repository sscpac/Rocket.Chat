Meteor.startup ->
	Migrations.add
		version: 12
		up: ->
			# Set oldest user as admin, if none exists yet
			admin = Meteor.users.findOne { admin: true }, { fields: { _id: 1 } }
			# do nothing. 