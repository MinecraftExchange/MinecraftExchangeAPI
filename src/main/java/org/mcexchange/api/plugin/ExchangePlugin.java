package org.mcexchange.api.plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ExchangePlugin is a marker interface that shows that the given class provides extra
 * functionality to MinecraftExchange. To provide specific functionality, the plugin
 * must implement a type of ExchangePlugin. All types of ExchangePlugin are compatible.
 * Different subinterfaces of ExchangePlugin work differently based on there intended use.
 * 
 * To use an ExchangePlugin, you must put the ExchangePlugin in a jar and then add the
 * "Exchange-Main" attribute to the jar's manifest file. the Exchange-Main attribute
 * points toward either a class implementing ExchangePlugin or a PluginSuite (see
 * {@link PluginSuite} for more info on when to use a PluginSuite).
 */
public interface ExchangePlugin {
	public static final Map<Class<? extends ExchangePlugin>,List<ExchangePlugin>> plugins = new HashMap<Class<? extends ExchangePlugin>, List<ExchangePlugin>>();
}
