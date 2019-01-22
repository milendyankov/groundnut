package com.liferay.apio.groundnut;

import com.vaadin.flow.component.HasComponents;

public interface ApioRenderer {

	void renderJson(String json, HasComponents component);

	void renderMenuItem(String json, MenuPanel component);

}