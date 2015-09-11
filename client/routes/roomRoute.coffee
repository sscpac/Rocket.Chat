openRoom = (type, name, rid) ->
	Session.set 'openedRoom', null

	BlazeLayout.render 'main', {center: 'loading'}

	Meteor.defer ->
		Tracker.autorun (c) ->

			# rid only supplied for direct messages
			if rid
				id = type + name + rid
			else
				id = type + name
			if RoomManager.open(id).ready() isnt true
				return

			c.stop()

			query =
				t: type
				name: name

			if type is 'd'
				query =
					_id: rid
				#delete query.name
				#query.usernames =
				#	$all: [name, Meteor.user().username]

			room = ChatRoom.findOne(query)
			if not room?
				Session.set 'roomNotFound', {type: type, name: name}
				BlazeLayout.render 'main', {center: 'roomNotFound'}
				return

			mainNode = document.querySelector('.main-content')
			if mainNode?
				for child in mainNode.children
					mainNode.removeChild child if child?
				roomDom = RoomManager.getDomOfRoom(id, room._id)
				mainNode.appendChild roomDom
				if roomDom.classList.contains('room-container')
					roomDom.querySelector('.messages-box > .wrapper').scrollTop = roomDom.oldScrollTop

			Session.set 'openedRoom', room._id

			Session.set 'editRoomTitle', false
			Meteor.call 'readMessages', room._id if Meteor.userId()?
			# KonchatNotification.removeRoomNotification(params._id)

			if Meteor.Device.isDesktop()
				setTimeout ->
					$('.message-form .input-message').focus()
				, 100


roomExit = ->
	mainNode = document.querySelector('.main-content')
	if mainNode?
		for child in mainNode.children
			if child?
				if child.classList.contains('room-container')
					child.oldScrollTop = child.querySelector('.messages-box > .wrapper').scrollTop
				mainNode.removeChild child


FlowRouter.route '/channel/:name',
	name: 'channel'

	action: (params, queryParams) ->
		openRoom 'c', params.name

	triggersExit: [roomExit]


FlowRouter.route '/group/:name',
	name: 'group'

	action: (params, queryParams) ->
		openRoom 'p', params.name

	triggersExit: [roomExit]


FlowRouter.route '/direct/:username/:rid',
	name: 'direct'

	action: (params, queryParams) ->
		openRoom 'd', params.username, params.rid

	triggersExit: [roomExit]
