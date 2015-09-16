Meteor.startup ->
	Migrations.add
		version: 12
		up: ->
			# previously set oldest user as admin if none existed.  
			# do nothing. 