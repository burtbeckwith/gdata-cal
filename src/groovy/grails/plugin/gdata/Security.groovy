package grails.plugin.gdata

class Security {

	private static final ThreadLocal<String> USERNAME = new ThreadLocal<String>()
	private static final ThreadLocal<String> PASSWORD = new ThreadLocal<String>()

	static void setCredentials(String username, String password) {
		USERNAME.set username
		PASSWORD.set password
	}

	static void resetCredentials() {
		USERNAME.remove()
		PASSWORD.remove()
	}

	static String getUsername() {
		USERNAME.get()
	}

	static String getPassword() {
		PASSWORD.get()
	}
}
