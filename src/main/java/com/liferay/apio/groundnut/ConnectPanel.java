package com.liferay.apio.groundnut;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

public class ConnectPanel extends HorizontalLayout {

	public ConnectPanel() {
		setDefaultVerticalComponentAlignment(Alignment.BASELINE);

		TextField url;
		TextField username;
		PasswordField password;
		ComboBox<String> format;
		Button connect;

		add(url = new TextField("URL"));
		url.setWidth("30em");
		url.setValue("http://localhost:8080/o/api");

		add(username = new TextField("Username"));
		username.setWidth("15em");
		username.setValue("test@liferay.com");

		add(password = new PasswordField("Password"));
		password.setWidth("15em");
		password.setValue("test");

		add(format = new ComboBox<>("Format"));
		format.setWidth("15em");
		format.setItems("application/json", "application/ld+json", "application/hal+json");
		format.setValue("application/json");

		add(connect = new Button("Connect"));
		connect.addClickListener(event -> connect(url.getValue(), username.getValue(), password.getValue(), format.getValue()));
		connect.setWidth("15em");
		getElement().addEventListener("keypress",
						event -> connect(url.getValue(), username.getValue(), password.getValue(), format.getValue()))
				.setFilter("event.key == 'Enter'");
		connect.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
		
	}

	private void connect(String uri, String username, String password, String format) {
		String result = BackendEntryPoint.instance().get(uri, username, password, format);
		if (result == null) {
			Notification.show("Can not connect to server!", 5000, Position.TOP_CENTER);
		} else {
			SessionUtil.saveConnectInSession(uri, username, password, format);
			fireEvent(new ConnectEvent(this, false, result));
		}
	}
	
    public Registration addConnectListener(ComponentEventListener<ConnectEvent> listener) {
        return addListener(ConnectEvent.class, listener);
    }
	
	public class ConnectEvent extends ComponentEvent<ConnectPanel> {

		private String entryPointResponse;
		
		public ConnectEvent(ConnectPanel source, boolean fromClient, String entryPointResponse) {
			super(source, fromClient);
			this.entryPointResponse = entryPointResponse;
		}

		public String getEntryPointResponse() {
			return entryPointResponse;
		}
		
	}
}
