Meteor.publish 'fullUsers', (filter, limit) ->
	unless this.userId
		return this.ready()

	user = Meteor.users.findOne this.userId
	### 
	# chat-locker allows non-admins to view non-sensitive user information
	if user.admin isnt true
		return this.ready()
	###

	filter = _.trim filter
	if filter
		if limit is 1
			query = { username: filter }
		else
			filterReg = new RegExp filter, "i"
			query = { $or: [ { username: filterReg }, { name: filterReg }, { "emails.address": filterReg } ] }
	else
		query = {}

	console.log '[publish] fullUsers'.green, filter, limit

	###
		Please note this returns the information for all users back to the client.
		Make sure to not add any more fields that are sensitive like access inside
		the profile or the entire profile object which would contain the access.
	###
	Meteor.users.find query,
		fields:
			name: 1
			username: 1
			emails: 1
			phone: 1
			status: 1
			statusDefault: 1
			statusConnection: 1
			avatarOrigin: 1
			admin: 1
			utcOffset: 1
			language: 1
			lastLogin: 1
			active: 1
			'profile.first_name' : 1
			'profile.last_name' : 1
			'profile.statusMessages' : 1
		limit: limit
		sort: { username: 1 }