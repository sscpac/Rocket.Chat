/**
 * Template displays modifiable Classification, SAP, SCI, and Release Caveat security label UI.
 * - Classification is a single select, SAP, SCI, Release Caveat are multiple select.
 * - Uses JQuery 'chosen' plugin to generate UI elements.  Single selects are regular
 *   drop downs, but multiple selects show selections as "tags".
 * - Template renders UI as regular HTML select elements then runs JQuery chosen plugin
 *   during onRendered Meteor callback.  Plugin hides regular select boxes and inserts its
 *   own custom UI.
 * - Plugin generates selection change events that are propagated to parent template via
 *   callbacks.
 * - Make an label required by adding its id to both the selected AND disabled list.
 * - Template expects data context with the following:
 *   1. securityLabels  - array of access permission objects (classification, sap, sci)
 *   2. onSelectionChanged(params) - callback function executed when the user selects/deselects
 *      a security label.  params.selected contains new selection.  params.deselected contains
 *      deselected labels.
 *   3. isOptionSelected(id) - callback function that determines if the label with id is currently
 *      selected.  Use this callback to pre-select security labels.  e.g. when your're editing
 *      a conversation's security labels.
 *   4. isOptionDisabled(id) - callback function that determines if the label with id is currently
 *      disabled.  Use this callback to disabled security label selection.
 */

var classification = { type: 'classification'};
var sap = { type: 'SAP'};
var sci = { type: 'SCI'};
var releaseCaveat = { type: 'Release Caveat'};


Template.securityLabels.onCreated( function() {

	Meteor.subscribe('accessPermissions');

	var self = this;

	self.securityLabelsInitialized = new ReactiveVar(false);
	self.selectedClassificationLabelId = new ReactiveVar('');
	self.selectedSciLabelIds = new ReactiveVar([]);
	self.selectedSciLabelNames = {};
	self.selectedSapLabelIds = new ReactiveVar([]);
	self.selectedSapLabelNames = {};
	self.selectedReltoLabelIds = new ReactiveVar([]);
	self.selectedReltoLabelNames = {};

	self.selectedLabelIds = [];
	self.disabledLabelIds = [];
	self.allowedLabels = [];

	self.allowedLabelIds = [];



	/**
	 * Retrieve security labels of the specified type.
	 * @param  {string} type equality based on type property
	 * @return {[object]} Array of security labels of the specified type.
	 */
	this.getLabels = function(type) {
		return _.filter(this.data.securityLabels, function(label) {
			return label.type === type;
		});
	}

	/**
	 * Listens for JQuery 'chosen' plugin events fired when the user selects/deselects security labels.
	 * Executes parent template callback function to notify of selection change event.
	 * @param  {Object} event  describes the change event
	 * @param  {object} params selected/deselected value
	 */
	this.labelSelectionChanged = function(event, params ) {
		// JQuery plugin triggers selection change events, but doesn't fire deselection
		// for single select fields (e.g. Classification).  So we check if it's a single select
		// and fire deselect
		if( !this.multiple ) {
			// kludge, but easier to deselect other options than to remember what was selected
			_.each($(this.options), function( option ){
				self.data.onSelectionChanged({deselected : option.value });
			});
		}
		self.data.onSelectionChanged(params);
	}


	Meteor.call('getAllowedConversationPermissions', { userIds: [] }, function(error, result) {
		if(error) {
			alert(error);
		}
		else {
			//# create shallow copies.  Adding id to both selected and disabled makes them "required"
			//# in the UI because it selects the permission and doesn't allow the user to remove it.
			self.selectedLabelIds = (result.selectedIds || []).slice(0);
			self.disabledLabelIds = (result.disabledIds || []).slice(0);
			//# initially select the default classification
			//# TODO: fix the following line
			//#instance.selectedLabelIds.push Meteor.settings.public.permission.classification.default
			self.allowedLabels = result.allowed || [];
			self.allowedLabelIds = _.pluck(self.allowedLabels, '_id');
			//# Meteor will automatically re-run helper methods that populate select boxes.
			self.securityLabelsInitialized.set(true);
		}
	});
});

Template.securityLabels.helpers( {

	/**
	 * Determine if the security label is selected or not.
	 * @return {Boolean} 'selected' if label is selected.  Otherwise ''
	 */
	optionSelected: function() {
		return Template.instance().data.isOptionSelected(this._id) ? 'selected' : '';
	},
	/**
	 * Determine if the security label is disabled or not.
	 * @return {Boolean} 'disabled' if label is disabled.  Otherwise ''
	 */
	optionDisabled: function() {
		return Template.instance().data.isOptionDisabled(this._id) ? 'disabled' : '';
	},
	isRelabeling : function(permisstionType) {
		var bVal = false;
		if (Template.instance().data.isRelabeling ) {
			bVal = true;
			if (permisstionType==='C') {
				// TODO: determine if classification is less then the current one
			}
		}
		return bVal ? 'disabled' : '';
	},
	/**
	 * Retrieve allowed classification label choices.  Only the classification(s) that all members have in
	 * common  are returned.
	 * @return {[object]} represents allowed classification label choices
	 */
	classificationLabels : function() {
		return _.sortBy(Template.instance().getLabels(classification.type), 'label');
	},

	/**
	 * Retrieve allowed SAP label choices.  Only the SAP(s) that all members have in
	 * common  are returned.
	 * @return {[object]} represents allowed SAP label choices
	 */
	sapLabels: function() {
		return _.sortBy(Template.instance().getLabels(sap.type), 'label');
	},
	/**
	 * Retrieve allowed SCI label choices.  Only the SCI(s) that all members have in
	 * common  are returned.
	 * @return {[object]} represents allowed SCI label choices
	 */
	sciLabels: function() {
		return _.sortBy(Template.instance().getLabels(sci.type), 'label');
	},
	/**
	 * Retrieve allowed Release Caveat label choices.  Only the Release Caveat(s) that all members have in
	 * common  are returned.
	 * @return {[object]} represents allowed Release Caveat label choices
	 */
	releaseCaveatLabels: function() {
		return _.sortBy(Template.instance().getLabels(releaseCaveat.type), 'label');
	},
	/**
	 * Reactive value to determine if specified permission type has values.
	 * @param  {string}  type access permission type.
	 * @return {Boolean}      true if there are permissions to display
	 */
	hasOptions: function(type) {
		return Template.instance().getLabels(type).length > 0 ;
	},
	/**
	 * Reactive value used to disable/enable permission type's select box.
	 * @param  {string} type access permission type.  SAP|SCI
	 * @return {string} 'disabled' if no permission options for the specified type. Otherwise empty string
	 */
	selectDisabled : function(type) {
		return Template.instance().getLabels(type).length > 0 ? '' : 'disabled';
	},




	classificationAutocompleteSettings: function() {
		return {
			limit: 4,
			rules: [
				{
					token: '#',
					collection: 'AccessPermissions',
					subscription: 'accessPermissions',
					field: 'label',
					template: Template.labelSearch,
					noMatchTemplate: Template.labelSearchEmpty,
					matchAll: true,
					filter: {
						type: 'classification'
					}
				}
			]
		};
	},

	classificationTitle: function() {
		return 'Classification';
	},



	sapAutocompleteSettings: function() {
		return {
			limit: 10,
			rules: [
				{
					token: '#',
					collection: 'AccessPermissions',
					subscription: 'accessPermissions',
					field: 'label',
					template: Template.labelSearch,
					noMatchTemplate: Template.labelSearchEmpty,
					matchAll: true,
					filter: {
						type: 'SAP',
						$and: [
							{ _id: { $nin: Template.instance().selectedSciLabelIds.get() }},
							{ _id: { $in:  Template.instance().allowedLabelIds }}
						]
					}
				}
			]
		};
	},

	sapTitle: function() {
		return 'SAP Labels';
	},

	selectedSapLabels: function() {
		return Template.instance().selectedSapLabelIds.get();
	},

	sapLabel: function() {
		return Template.instance().selectedSapLabelNames[this.valueOf()];
	},



	sciAutocompleteSettings: function() {
		return {
			limit: 10,
			rules: [
				{
					token: '#',
					collection: 'AccessPermissions',
					subscription: 'accessPermissions',
					field: 'label',
					template: Template.labelSearch,
					noMatchTemplate: Template.labelSearchEmpty,
					matchAll: true,
					filter: {
						type: 'SCI',
						$and: [
							{ _id: { $nin: Template.instance().selectedSciLabelIds.get() }},
							{ _id: { $in:  Template.instance().allowedLabelIds }}
						]
					}
				}
			]
		};
	},

	securityLabelsInitialized: function() {
		return Template.instance().securityLabelsInitialized.get();
	},

	sciTitle: function() {
		return 'SCI Labels';
	},

	selectedSciLabels: function() {
		return Template.instance().selectedSciLabelIds.get();
	},

	sciLabel: function() {
		return Template.instance().selectedSciLabelNames[this.valueOf()];
	},



	reltoAutocompleteSettings: function() {
		return {
			limit: 10,
			rules: [
				{
					token: '#',
					collection: 'AccessPermissions',
					subscription: 'accessPermissions',
					field: 'label',
					template: Template.labelSearch,
					noMatchTemplate: Template.labelSearchEmpty,
					matchAll: true,
					filter: {
						type: 'Release Caveat',
						$and: [
							{ _id: { $nin: Template.instance().selectedSciLabelIds.get() }},
							{ _id: { $in:  Template.instance().allowedLabelIds }}
						]
					}
				}
			]
		};
	},

	reltoTitle: function() {
		return 'Release Caveats';
	},

	selectedReltoLabels: function() {
		return Template.instance().selectedReltoLabelIds.get();
	},

	reltoLabel: function() {
		return Template.instance().selectedReltoLabelNames[this.valueOf()];
	},

});



Template.securityLabels.events({


	// Classification
	'autocompleteselect #classification-labels': function(event, instance, doc) {
		instance.selectedClassificationLabelIds.set(doc._id);
	},


	// SAP
	'autocompleteselect #sap-labels': function(event, instance, doc) {
		instance.selectedSapLabelIds.set(instance.selectedSapLabelIds.get().concat(doc._id));
		instance.selectedSapLabelNames[doc._id] = doc.label;
		event.currentTarget.value = '';
		event.currentTarget.focus();
	},
	'click .remove-sap-label': function(e, instance) {
		var self = this;
		var sapLabelIds = _.reject(instance.selectedSapLabelIds.get(), function(_id) {
			return _id === self.valueOf();
		});
		instance.selectedSapLabelIds.set(sapLabelIds);
		$('#sap-labels').focus();
	},


	// SCI
	'autocompleteselect #sci-labels': function(event, instance, doc) {
		instance.selectedSciLabelIds.set(instance.selectedSciLabelIds.get().concat(doc._id));
		instance.selectedSciLabelNames[doc._id] = doc.label;
		event.currentTarget.value = '';
		event.currentTarget.focus();
	},
	'click .remove-sci-label': function(e, instance) {
		var self = this;
		var sciLabelIds = _.reject(instance.selectedSciLabelIds.get(), function(_id) {
			return _id === self.valueOf();
		});
		instance.selectedSciLabelIds.set(sciLabelIds);
		$('#sci-labels').focus();
	},


	// RELTO
	'autocompleteselect #relto-labels': function(event, instance, doc) {
		instance.selectedReltoLabelIds.set(instance.selectedReltoLabelIds.get().concat(doc._id));
		instance.selectedReltoLabelNames[doc._id] = doc.label;
		event.currentTarget.value = '';
		event.currentTarget.focus();
	},
	'click .remove-relto-label': function(e, instance) {
		var self = this;
		var reltoLabelIds = _.reject(instance.selectedReltoLabelIds.get(), function(_id) {
			return _id === self.valueOf();
		});
		instance.selectedReltoLabelIds.set(reltoLabelIds);
		$('#relto-labels').focus();
	},
});
























