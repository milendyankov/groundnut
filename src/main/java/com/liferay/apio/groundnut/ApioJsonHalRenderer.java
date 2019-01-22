package com.liferay.apio.groundnut;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.TemplateRenderer;

import elemental.json.JsonObject;
import elemental.json.impl.JsonUtil;

public class ApioJsonHalRenderer implements ApioRenderer {

	@Override
	public void renderJson(String json, HasComponents component) {
//		Gson gson = new Gson();
//		Map mapResult = gson.fromJson(json, Map.class);
//		List elements = (List) mapResult.get("elements");
//
//		if (elements != null) {
//			Grid<Map<String, Object>> grid = new Grid<>();
//
//			for (Object object : elements) {
//				Map<String, Object> element = (Map) object;
//				for (String key : element.keySet()) {
//					if (grid.getColumnByKey(key) == null) {
//						final String gridemplate = "<div style='text-align: center'>[[item." + key + "]]</div>";
//						Column<Map<String, Object>> column = grid.addColumn(TemplateRenderer
//								.<Map<String, Object>>of(gridemplate).withProperty(key, row -> row.get(key)));
//						column.setKey(key);
//						column.setHeader(key);
//					}
//				}
//			}
//
//			grid.setDataProvider(new CustomDataProvider(elements));
//			component.add(grid);
//		}
		component.add(new Label("render hal+json here"));
	}
	
	@Override
	public void renderMenuItem(String json, MenuPanel menuPanel) {
		JsonObject mainScopesJsonObject = JsonUtil.parse(json);
		JsonObject links = mainScopesJsonObject.getObject("_links");
		for (int i = 0; i < links.keys().length; i++) {
			String key = links.keys()[i];
			String value = null;
			try {
				value = URLEncoder.encode(links.getObject(key).getString("href"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			menuPanel.addView(value, key, VaadinIcon.FOLDER_O.create());
		}
	}

	class CustomDataProvider extends ListDataProvider<Map<String, Object>> {

		public CustomDataProvider(Collection<Map<String, Object>> items) {
			super(items);
		}
		
	}

}
