package org.mcexchange.api.plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ExchangePlugin is a marker interface that shows that the given class provides extra
 * functionality to MinecraftExchange. To provide specific functionality, the plugin
 * must implement a type of ExchangePlugin. All types of ExchangePlugin are compatible.
 * Different subinterfaces of ExchangePlugin work differently based on there intended use.
 */
public interface ExchangePlugin {
	public static final Map<Class<? extends ExchangePlugin>,List<ExchangePlugin>> plugins = new HashMap<Class<? extends ExchangePlugin>, List<ExchangePlugin>>();
}
