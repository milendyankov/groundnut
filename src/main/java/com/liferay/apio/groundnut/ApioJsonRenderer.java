package com.liferay.apio.groundnut;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.RouterLink;

import elemental.json.JsonObject;
import elemental.json.impl.JsonUtil;

public class ApioJsonRenderer implements ApioRenderer {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void renderJson(String json, HasComponents component) {
		Gson gson = new Gson();
		Map mapResult = gson.fromJson(json, Map.class);
		List elements = (List) mapResult.get("elements");

		if (elements != null) {
			Grid<Map<String, Object>> grid = new Grid<>();

			for (Object object : elements) {
				Map<String, Object> element = (Map) object;
				for (String key : element.keySet()) {
					if (grid.getColumnByKey(key) == null) {
						Column<Map<String, Object>> column = grid.addColumn(
							new ComponentRenderer<Component, Map<String, Object>>(row -> {
								if (row.get(key) == null) return new Text("");
								String value = row.get(key).toString();
								if (value.startsWith(SessionUtil.getULR())) {
									String url = null;
									try {
										url = URLEncoder.encode(value, "UTF-8");
									} catch (UnsupportedEncodingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									return new RouterLink(value, MainScopeView.class, url);
								} else {
									return new Text(value);
								}
							})
						);
						column.setKey(key);
						column.setHeader(key);
					}
				}
			}

			grid.setDataProvider(new CustomDataProvider(elements));
			component.add(grid);
		}
	}

	@Override
	public void renderMenuItem(String json, MenuPanel menuPanel) {
		JsonObject mainScopesJsonObject = JsonUtil.parse(json);
		for (int i = 0; i < mainScopesJsonObject.keys().length; i++) {
			String key = mainScopesJsonObject.keys()[i];
			String value = null;
			try {
				value = URLEncoder.encode(mainScopesJsonObject.getString(key), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			menuPanel.addView(value, key, VaadinIcon.FOLDER_O.create());
		}
	}

	class CustomDataProvider extends ListDataProvider<Map<String, Object>> {

		private static final long serialVersionUID = 1L;

		public CustomDataProvider(Collection<Map<String, Object>> items) {
			super(items);
		}

	}

}
