Template.privateHistory.helpers

	history: ->
		items = Template.instance().searchResult.get()
		return {
			items: items
			length: items.length
		}

	type: ->
		switch this.t
			when 'd' then 'icon-at'
			when 'c' then 'icon-hash'
			when 'p' then 'icon-lock'

	creation: ->
		return moment(this.ts).format('LLL')

	lastMessage: ->
		return moment(this.lm).format('LLL') if this.lm

	roomOf: (rid) ->
		return ChatRoom.findOne rid

Template.privateHistory.events
	'keydown #history-filter': (event) ->
		# 'enter' key
		if event.which is 13
			event.stopPropagation()
			event.preventDefault()

	# 'debounce' input so that we don't flood the server with RPC calls
	'keyup #history-filter': _.debounce (event, instance) ->
		event.stopPropagation()
		event.preventDefault()
		instance.searchFilter.set event.currentTarget.value
	, 200


	'click #history-content-flag': (event, instance) ->
		instance.searchContentsFlag.set $(event.currentTarget).is(':checked')
		$('#history-filter').focus()


Template.privateHistory.onCreated ->
	instance = this

	# make search input and output reactive vars so that searches can occur dynamically
	# and so that the helper function can update the page dynamically
	instance.searchFilter = new ReactiveVar ''
	instance.searchContentsFlag = new ReactiveVar false
	instance.searchResult = new ReactiveVar []

	# whenever there is an update to the search filter text (or the content search option),
	# call the 'roomSearch' server-side method
	instance.autorun ->
		filter = instance.searchFilter.get()
		contentFlag = instance.searchContentsFlag.get()
		Meteor.call 'roomSearch', filter, contentFlag, (error, result) ->
			unless error
				instance.searchResult.set result
