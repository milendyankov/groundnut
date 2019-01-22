package com.liferay.apio.groundnut;

import com.vaadin.flow.server.VaadinSession;

public class SessionUtil {
	
	private static final String URL = "url";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String FORMAT = "format";

	public static void saveConnectInSession(String url, String username, String password, String format) {
		VaadinSession vaadinSession = VaadinSession.getCurrent();
		vaadinSession.setAttribute(URL, url);
		vaadinSession.setAttribute(USERNAME, username);
		vaadinSession.setAttribute(PASSWORD, password);
		vaadinSession.setAttribute(FORMAT, format);
	}

	public static String getULR() {
		VaadinSession vaadinSession = VaadinSession.getCurrent();
		return (String)vaadinSession.getAttribute(URL);
	}

	public static String getUser() {
		VaadinSession vaadinSession = VaadinSession.getCurrent();
		return (String)vaadinSession.getAttribute(USERNAME);
	}

	public static String getFormat() {
		VaadinSession vaadinSession = VaadinSession.getCurrent();
		return (String)vaadinSession.getAttribute(FORMAT);
	}

	public static String getPassword() {
		VaadinSession vaadinSession = VaadinSession.getCurrent();
		return (String)vaadinSession.getAttribute(PASSWORD);
	}

	
}
