package grails.plugin.gdata

/**
 * Ensures that the user is authenticated.
 *
 * @author Burt Beckwith
 */
class CalendarFilters {

	private static final KEY_USERNAME = '_calendar_username_'
	private static final KEY_PASSWORD = '_calendar_password_'

	def filters = {

		auth(controller: 'calendar', action: 'index|events') {
			before = {

				String username = session[KEY_USERNAME]
				if (!username) {
					session['__CAL_REDIRECT_URI__'] = request.forwardURI
					redirect controller: 'calendar', action: 'login'
					return false
				}

				String password = session[KEY_PASSWORD]
				Security.setCredentials username, password
			}
		}
	}
}
