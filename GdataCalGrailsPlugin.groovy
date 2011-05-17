import grails.plugin.gdata.MetaclassUtils;

class GdataCalGrailsPlugin {

	def version = "0.1-SNAPSHOT"
	def grailsVersion = "1.2 > *"
	def dependsOn = [jquery: '1.5 > *']

	def author = "Burt Beckwith"
	def authorEmail = "beckwithb@vmware.com"
	def title = "Google Calendar Plugin"
	def description = 'Google Calendar Plugin'
	def documentation = "http://grails.org/plugin/gdata-cal"

	def doWithDynamicMethods = { ctx ->
		MetaclassUtils.enhanceAll ctx
	}
}
