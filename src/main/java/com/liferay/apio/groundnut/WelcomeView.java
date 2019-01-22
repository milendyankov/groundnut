package com.liferay.apio.groundnut;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Welcome")
public class WelcomeView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public WelcomeView() {
		
		add(new H1("Welcome to Apio Groundnut!"));
		add(new Span("A \"universal\" web UI for Apio based services!"));
	}
}
