Meteor.publish 'contacts', ->
	unless this.userId
		return this.ready()

	console.log '[publish] contacts'.green

	Contacts.find
		owner: {owner: {$in: ["public", this.userId]}}

		fields:
			listName: 1
			users: 1
			ts: 1
