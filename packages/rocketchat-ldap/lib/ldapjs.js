MeteorWrapperLdapjs = Npm.require('ldapjs');

Meteor.startup(function() {
	// chat locker currently doesn't support these settings.  if/when we upgrade to latest from RocketChat, then 
	// we can use these settings.
	// RocketChat.settings.add('LDAP_Url', '', { type: 'string', group: 'Accounts', i18nLabel: 'rocketchat-ldap:LDAP_Url' });
	// RocketChat.settings.add('LDAP_Port', '', { type: 'string', group: 'Accounts', i18nLabel: 'rocketchat-ldap:LDAP_Port' });
	// RocketChat.settings.add('LDAP_DN', '', { type: 'string', group: 'Accounts', i18nLabel: 'rocketchat-ldap:LDAP_Dn', public: true });
});