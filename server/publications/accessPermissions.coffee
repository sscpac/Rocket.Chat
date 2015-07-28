Meteor.publish 'accessPermissions', (selector, options, collName) ->
	unless this.userId
		return this.ready()

	console.log '[publish] accessPermissions -> '.green, 'selector:', selector, 'options:', options, 'collName:', collName

	self = this

	sub = AccessPermissions.find(selector, { limit: 10, fields: { trigraph: 1, label: 1, type: 1 } }).observeChanges
		added: (id, fields) ->
			data = { _id: id, trigraph: fields.trigraph, label: fields.label, type: fields.type }
			self.added("autocompleteRecords", id, data)
		changed: (id, fields) ->
			self.changed("autocompleteRecords", id, fields)
		removed: (id) ->
			self.removed("autocompleteRecords", id)

	this.ready()

	this.onStop ->
		sub?.stop()