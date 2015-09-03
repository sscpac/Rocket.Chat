Template.chatRoomItem.helpers

	alert: ->
		if FlowRouter.getParam('_id') isnt this.rid or not document.hasFocus()
			return this.alert

	unread: ->
		if (FlowRouter.getParam('_id') isnt this.rid or not document.hasFocus()) and this.unread > 0
			return this.unread

	isDirectRoom: ->
		return this.t is 'd'

	userStatus: ->
		return 'status-' + Session.get('user_' + this.name + '_status') if this.t is 'd'
		return ''

	name: ->
		return this.name

	roomIcon: ->
		switch this.t
			when 'd' then return 'icon-at'
			when 'c' then return 'icon-hash'
			when 'p' then return 'icon-lock'

	active: ->
		if FlowRouter.getParam('_id')? and FlowRouter.getParam('_id') is this.rid
			return 'active'

	canLeave: ->
		roomData = Session.get('roomData' + this.rid)

		return false unless roomData

		if (roomData.cl? and not roomData.cl) or roomData.t is 'd' or (roomData.usernames?.indexOf(Meteor.user().username) isnt -1 and roomData.usernames?.length is 1)
			return false
		else
			return true

	canRelabel: ->
		return this.t is 'd' or this.t is 'p'


	bannerText: ->
		return Template.instance().bannerText.get()

	bannerTextAbbreviated: ->
		banner = Template.instance().bannerTextAbbreviated.get()
		# ensure the banner data doesn't overrun the side panel
		if this.name.length + banner.length > 30
			len = 30 - this.name.length
			return banner.substring(0, len) + '...'
		else
			return banner

	multipleSameUser: ->
		result = false
		me = Meteor.user().username
		otherUser = _.without(Template.instance().room.get().usernames, me)[0]
		if otherUser?
			count = ChatRoom.find({t: 'd', usernames: [me, otherUser]}).count()
			if count > 1
				result = true
		return result


Template.chatRoomItem.rendered = ->
	if not (FlowRouter.getParam('_id')? and FlowRouter.getParam('_id') is this.data.rid) and not this.data.ls
		KonchatNotification.newRoom(this.data.rid)


Template.chatRoomItem.onCreated ->
	instance = this

	# store banner data in reactive vars so they update dynamically in template with method response
	instance.bannerText = new ReactiveVar ''
	instance.bannerTextAbbreviated = new ReactiveVar ''
	instance.room = new ReactiveVar {}

	# whenever there's a change in the room (eg, relabel), update banner data
	instance.autorun ->
		room = ChatRoom.findOne({_id: instance.data.rid})
		if room?.accessPermissions?
			instance.room.set room
			Meteor.call 'getSecurityBanner', instance.room.get().accessPermissions, (error, result) ->
				unless error
					instance.bannerText.set result.text
					instance.bannerTextAbbreviated.set result.textAbbreviated
	

Template.chatRoomItem.events
	'click .label-room': (e) ->
		e.stopPropagation()
		e.preventDefault()
		data = {relabelRoom: this.rid}
		if this.t is 'd'
			SideNav.setFlex "directMessagesFlex", data
		else if this.t is 'p'
			SideNav.setFlex "privateGroupsFlex", data
		SideNav.openFlex()

	'click .open-room': (e) ->
		menu.close()

	'click .hide-room': (e) ->
		e.stopPropagation()
		e.preventDefault()

		if (FlowRouter.getRouteName() is 'room' and FlowRouter.getParam('_id') is this.rid)
			FlowRouter.go 'home'

		Meteor.call 'hideRoom', this.rid

	'click .leave-room': (e) ->
		e.stopPropagation()
		e.preventDefault()

		if (FlowRouter.getRouteName() is 'room' and FlowRouter.getParam('_id') is this.rid)
			FlowRouter.go 'home'

		RoomManager.close this.rid

		Meteor.call 'leaveRoom', this.rid
