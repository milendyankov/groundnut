package com.liferay.apio.groundnut;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;

@Route(value = "main", layout = MainLayout.class)
public class MainScopeView extends VerticalLayout implements HasUrlParameter<String> {

	private static final long serialVersionUID = 1L;

	private VerticalLayout appPage = new VerticalLayout();
	private VerticalLayout restPage = new VerticalLayout();

	private TextField urlField = new TextField("URL");
	private TextArea payloadField = new TextArea("Payload");

	public MainScopeView() {
		Tab appView = new Tab("App view");
		Tab restView = new Tab("REST view");
		Tabs tabs = new Tabs(appView, restView);

		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(appView, appPage);
		tabsToPages.put(restView, restPage);

		appPage.setSizeFull();
		restPage.setSizeFull();
		restPage.setVisible(false);

		tabs.addSelectedChangeListener(event -> {
			appPage.setVisible(false);
			restPage.setVisible(false);
			tabsToPages.get(tabs.getSelectedTab()).setVisible(true);
		});

		urlField.setReadOnly(true);
		urlField.setWidth("100%");
		payloadField.setReadOnly(true);
		payloadField.setSizeFull();

		restPage.add(urlField);
		restPage.add(payloadField);

		add(tabs);
		add(appPage);
		add(restPage);

	}

	@Override
	public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {
		appPage.removeAll();

		String url = null;
		try {
			url = URLDecoder.decode(parameter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		if (SessionUtil.getULR() == null) {
			event.rerouteTo(WelcomeView.class);
			return;
		}

		String result = BackendEntryPoint.instance().get(url, SessionUtil.getUser(), SessionUtil.getPassword(),
				SessionUtil.getFormat());

		if (result != null) {
			urlField.setValue(url);
			payloadField.setValue(result);
	
			ApioRenderer renderer = ApioRenderers.get(SessionUtil.getFormat());
	
			renderer.renderJson(result, appPage);
		} else {
			appPage.add(new TextField("Ooops! Something went wrong!"));
		}
			

	}

}
