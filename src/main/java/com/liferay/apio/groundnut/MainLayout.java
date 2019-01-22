package com.liferay.apio.groundnut;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import com.liferay.apio.groundnut.ConnectPanel.ConnectEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import elemental.json.JsonObject;
import elemental.json.impl.JsonUtil;

@Theme(value = Lumo.class)
@PWA(name = "Apio Groundnut", shortName = "Apio Groundnut")
@StyleSheet("css/styles.css")
public class MainLayout extends VerticalLayout implements RouterLayout, ComponentEventListener<ConnectEvent> {

	private MenuPanel menu;

	private HorizontalLayout contentLayout;
	
	private HorizontalLayout mainLayout;

	public MainLayout() {
		setSizeFull();

		mainLayout = new HorizontalLayout();
		mainLayout.setClassName("main-layout");
		mainLayout.setSizeFull();

		menu = new MenuPanel();

		contentLayout = new HorizontalLayout();
		contentLayout.setClassName("content-layout");
		contentLayout.setSizeFull();
		
		mainLayout.add(menu);
		mainLayout.add(contentLayout);

		ConnectPanel connectPanel = new ConnectPanel();
		connectPanel.addConnectListener(this);
		
		add(connectPanel);
		add(mainLayout);
		
		String url = SessionUtil.getULR();
		if (url != null) {
			String response = BackendEntryPoint.instance().get(url, SessionUtil.getUser(), SessionUtil.getPassword());
			buildMenu(response);
		}
	}
	
    public void showRouterLayoutContent(HasElement content) {
        if (content != null) {
        	contentLayout.getElement().appendChild(Objects.requireNonNull(content.getElement()));
        }
    }

	public void buildMenu(String content) {
		menu.removeAll();
		if (content != null) {
			ApioRenderers.get(SessionUtil.getFormat()).renderMenuItem(content, menu);
		}
	}

	@Override
	public void onComponentEvent(ConnectEvent event) {
		buildMenu(event.getEntryPointResponse());
	}
	

}
