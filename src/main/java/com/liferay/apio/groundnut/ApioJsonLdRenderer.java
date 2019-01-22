package com.liferay.apio.groundnut;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.ListDataProvider;

import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.impl.JsonUtil;

public class ApioJsonLdRenderer implements ApioRenderer {

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
		component.add(new Label("render ld+json here"));
	}

	@Override
	public void renderMenuItem(String json, MenuPanel menuPanel) {
		JsonObject mainScopesJsonObject = JsonUtil.parse(json);
		JsonArray links = mainScopesJsonObject.getArray("collection");
		for (int i = 0; i < links.length(); i++) {
			String url = links.getObject(i).getString("@id");
			String label = links.getObject(i).getObject("manages").getString("object");
			try {
				url = URLEncoder.encode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			menuPanel.addView(url, label, VaadinIcon.FOLDER_O.create());
		}
	}

	class CustomDataProvider extends ListDataProvider<Map<String, Object>> {

		public CustomDataProvider(Collection<Map<String, Object>> items) {
			super(items);
		}
		
	}

}
