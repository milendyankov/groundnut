package com.liferay.apio.groundnut;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MenuPanel extends VerticalLayout {

	public MenuPanel() {
		setClassName("menu-bar");
		setWidth(null);
		
	}

	/**
	 * Add a view to the navigation menu
	 *
	 * @param viewClass that has a {@code Route} annotation
	 * @param caption   view caption in the menu
	 * @param icon      view icon in the menu
	 */
	public void addView(String url, String label, Icon icon) {
		RouterLink routerLink = new RouterLink("", MainScopeView.class, url);
		routerLink.setClassName("menu-link");
		routerLink.add(icon);
		routerLink.add(new Span(label));
		add(routerLink);
	}
}
