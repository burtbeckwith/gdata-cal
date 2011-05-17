package grails.plugin.gdata

import grails.converters.JSON

import java.text.SimpleDateFormat

import com.google.gdata.data.DateTime
import com.google.gdata.data.calendar.CalendarEventEntry

class CalendarController {

	private static final KEY_USERNAME = '_calendar_username_'
	private static final KEY_PASSWORD = '_calendar_password_'

	def calendarService

	def index = {}

	def events = {
println params
		Date min = new Date(params.start.toLong() * 1000)
		Date max = new Date(params.end.toLong() * 1000)

		def data = []

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
		formatter.setTimeZone(TimeZone.getTimeZone('GMT'))

		for (CalendarEventEntry event : calendarService.getCalendarEntries(min, max)) {
			boolean allDay = event.getTimes()[0].getStartTime().isDateOnly()
			def eventData = [id: event.getId(),
			                 title: event.getTitle().getPlainText(),
								  description: event.getSummary()?.getPlainText(),
			                 start: formatter.format(event.getStartTime()),
								  allDay: allDay,
								  recurring: false]

			if (event.getEndTime()) {
				eventData.end = formatter.format(event.getEndTime())
			}

			data << eventData
		}

		render data as JSON
	}

	def createEvent = {

		boolean allDay = params.allDay == 'on'

		def messages = []

		DateTime startTime
		if (params.startTime) {
			try {
				startTime = DateTime.parseDateTime(params.startTime)
			}
			catch (e) {
				// TODO
				e.printStackTrace()
				messages << 'Please enter a valid start time'
			}
		}
		else {
			messages << 'Please enter a start time'
		}

		DateTime endTime
		if (params.endTime) {
			try {
				endTime = DateTime.parseDateTime(params.endTime)
			}
			catch (e) {
				// TODO
				e.printStackTrace()
				messages << 'Please enter a valid end time'
			}
		}

		if (startTime) {
			CalendarEventEntry event = calendarService.createEvent(
				startTime, endTime,
				[title: params.title,
				 summary: params.description,
				 location: params.location])
		}

		configureMessages messages
		redirect action: 'index'
	}

	def deleteEvent = {
		def event = calendarService.getEvent(params.id)
		if (!event) {
			configureMessages(['No event found with that id'])
		}
		
		calendarService.deleteEvent event
		
		redirect action: 'index'
	}

	def login = {}

	def logout = {
		session.removeAttribute KEY_USERNAME
		session.removeAttribute KEY_PASSWORD
		redirect uri: request.contextPath
	}

	def auth = {
		session[KEY_USERNAME] = params.username
		session[KEY_PASSWORD] = params.password
		redirect url: session['__CAL_REDIRECT_URI__'] ?: request.contextPath
	}

	private void configureMessages(messages) {
		if (messages) {
			flash.message = messages.join('<br/>')
		}
	}
}
