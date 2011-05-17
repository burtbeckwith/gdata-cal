package grails.plugin.gdata

import com.google.gdata.client.calendar.CalendarQuery
import com.google.gdata.client.calendar.CalendarService as GoogleCalendarService
import com.google.gdata.data.DateTime
import com.google.gdata.data.calendar.CalendarEntry
import com.google.gdata.data.calendar.CalendarEventEntry
import com.google.gdata.data.calendar.CalendarEventFeed
import com.google.gdata.data.calendar.CalendarFeed
import com.google.gdata.data.extensions.When
import com.google.gdata.data.extensions.Where
import com.google.gdata.util.AuthenticationException

class CalendarService {

	// TODO make settable
	private static final String METAFEED_URL_BASE = 'https://www.google.com/calendar/feeds/'
	private static final String OWNCALENDARS_FEED_URL_SUFFIX = '/owncalendars/full'
	private static final String EVENT_FEED_URL_SUFFIX = '/private/full'

	static transactional = false

	List<CalendarEntry> getUserCalendars() {
		URL url = new URL(METAFEED_URL_BASE + Security.username + OWNCALENDARS_FEED_URL_SUFFIX)
		getCalendars url
	}

	CalendarEntry createCalendar(Map properties) {
		CalendarEntry calendar = new CalendarEntry()
		setPropertyValues calendar, properties

		URL url = new URL(METAFEED_URL_BASE + 'default/owncalendars/full')
		createCalendarService().insert url, calendar
	}

	void updateCalendar(CalendarEntry calendar, Map properties) {
		setPropertyValues calendar, properties
		calendar.update()
	}

	void updateCalendar(String title, Map properties) {
		CalendarEntry calendar = findByTitle(title)
		if (!calendar) {
			// TODO
			return
		}

		updateCalendar calendar, properties
	}

	void deleteCalendar(CalendarEntry calendar) {
		calendar?.delete()
	}

	void deleteCalendar(String title) {
		deleteCalendar findByTitle(title)
	}

	CalendarEventEntry createEvent(DateTime start, DateTime end = null, Map properties) {
		CalendarEventEntry event = buildEvent(start, end, properties)
		URL url = new URL(METAFEED_URL_BASE + Security.username + EVENT_FEED_URL_SUFFIX)
		createCalendarService().insert url, event
	}

	CalendarEventEntry createEvent(Date start, Date end = null, TimeZone zone,
			boolean dateOnly, Map properties) {

		DateTime startTime = new DateTime(start, zone)
		startTime.dateOnly = dateOnly

		DateTime endTime
		if (end) {
			endTime = new DateTime(end, zone)
			endTime.dateOnly = dateOnly
		}
	
		CalendarEventEntry event = buildEvent(startTime, endTime, properties)
		URL url = new URL(METAFEED_URL_BASE + Security.username + EVENT_FEED_URL_SUFFIX)
		createCalendarService().insert url, event
	}

	void updateEvent(CalendarEventEntry event, Map properties) {
		String location = properties.remove('location')
		if (location) {
			event.addLocation new Where('', '', location)
		}
		setPropertyValues event, properties
		event.update()
	}

	void deleteEvent(CalendarEventEntry event) {
		event?.delete()
	}

	CalendarEventEntry getEvent(String id) {
		getCalendarEntries().find { it.id == id }
	}

	List<CalendarEventEntry> getCalendarEntries() {
		URL url = new URL(METAFEED_URL_BASE + Security.username + EVENT_FEED_URL_SUFFIX)
		createCalendarService().getFeed(url, CalendarEventFeed).entries
	}

	List<CalendarEventEntry> getCalendarEntries(Date min, Date max) {
		URL url = new URL(METAFEED_URL_BASE + Security.username + EVENT_FEED_URL_SUFFIX)
		CalendarQuery query = new CalendarQuery(url)
		query.setMinimumStartTime new DateTime(min)
		query.setMaximumStartTime new DateTime(max)
		createCalendarService().query(query, CalendarEventFeed).getEntries()
	}

	private CalendarEntry findByTitle(String title) {
		getUserCalendars().find { it.getTitle().text == title }
	}

	private List<CalendarEntry> getCalendars(URL feedUrl) {
		return createCalendarService().getFeed(feedUrl, CalendarFeed).entries
	}

	private static GoogleCalendarService createCalendarService() throws AuthenticationException {
		GoogleCalendarService service = new GoogleCalendarService('gr8conf-gdata-plugin')
		service.setUserCredentials Security.username, Security.password
		service
	}

	private CalendarEventEntry buildEvent(DateTime startTime, DateTime endTime = null, Map properties) {

		String location = properties.remove('location')

		CalendarEventEntry event = new CalendarEventEntry()
		if (location) {
			event.addLocation new Where('', '', location)
		}
		setPropertyValues event, properties

		if (startTime) {
			event.addTime new When(startTime: startTime, endTime: endTime)
		}

		event
	}

	private void setPropertyValues(object, properties) {
		properties.each { name, value -> object[name] = value }
	}
}
