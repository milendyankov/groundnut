package com.liferay.apio.groundnut;

import java.util.HashMap;
import java.util.Map;

public class ApioRenderers {

	private static Map<String, ApioRenderer> renderes = new HashMap<>();

	static {
		renderes.put("application/json", new ApioJsonRenderer());
		renderes.put("application/ld+json", new ApioJsonLdRenderer());
		renderes.put("application/hal+json", new ApioJsonHalRenderer());
	}

	public static ApioRenderer get(String format) {
		return renderes.get(format);
	}
}
