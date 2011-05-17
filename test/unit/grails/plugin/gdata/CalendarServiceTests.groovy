package grails.plugin.gdata

import com.google.gdata.data.DateTime
import com.google.gdata.data.calendar.CalendarEntry
import com.google.gdata.data.calendar.CalendarEventEntry
import com.google.gdata.data.calendar.HiddenProperty

class CalendarServiceTests extends GroovyTestCase {

	private CalendarService service = new CalendarService()

	private static String username
	private static String password

	@Override
	protected void setUp() {
		super.setUp()
		loadCredentials()
		Security.setCredentials username, password

		def ctx = [calendarService: service]
		MetaclassUtils.enhanceAll ctx
	}

	@Override
	protected void tearDown() {
		super.tearDown()
		Security.resetCredentials()
	}

	void testListCalendars() {

		List<CalendarEntry> calendars = service.getUserCalendars()

		assertNotNull calendars
		assertEquals 1, calendars.size()
		CalendarEntry calendar = calendars[0]
		assertEquals username, calendar.getTitle().getPlainText()
	}

	void testCreateCalendar() {

		String title = 'test schedule'
		String summary = 'test summary'

		try {
			def entry = service.createCalendar(
				title: title,
				summary: summary,
				hidden: false)

			assertNotNull entry
			assertEquals title, entry.getTitle().text
			assertEquals summary, entry.getSummary().text
			assertEquals HiddenProperty.FALSE, entry.getHidden()
		}
		finally {
			try {
				service.deleteCalendar title
			}
			catch (e) {
				e.printStackTrace()
			}
		}
	}

	void testGetCalendarEntries() {

		List<CalendarEventEntry> entries = service.getCalendarEntries()

		assertNotNull entries
		assertTrue entries.size() >= 2
		['GR8Conf Europe', 'SpringOne 2GX 2011'].each { title ->
			assertTrue entries*.getTitle()*.getPlainText().contains(title)
		}
	}

	void testCreateCalendarEntry() {

		String title = 'test entry'
		String summary = 'test entry summary'
		String location = 'test location'
		def event

		try {
			event = service.createEvent(
				DateTime.parseDateTime('2011-06-01T10:00:00'),
				[title: title, summary: summary, location: location])

			assertNotNull event
			assertEquals title, event.getTitle().text
//			assertEquals summary, event.getSummary().text
		}
		finally {
			try {
				service.deleteEvent event
			}
			catch (e) {
				e.printStackTrace()
			}
		}
	}

	void testUpdateCalendarEntry() {

		def event

		try {
			String title = 'test entry'
			event = service.createEvent(
				DateTime.parseDateTime('2011-06-01T10:00:00'),
				[title: title])

			assertEquals title, event.getTitle().text
			assertNull event.getLocations()[0].getValueString()

			String location = 'test location'
			service.updateEvent(event, [title: title + ' updated', location: location])

			assertEquals title + ' updated', event.getTitle().text
//			assertEquals location, event.getLocations()[0].getValueString()
		}
		finally {
			try {
				service.deleteEvent event
			}
			catch (e) {
				e.printStackTrace()
			}
		}
	}

	private static void loadCredentials() {
		if (!username) {
			def properties = new Properties()
			properties.load new FileReader('test/test.auth.properties')
			username = properties.getProperty('username')
			password = properties.getProperty('password')
		}
	}
}
