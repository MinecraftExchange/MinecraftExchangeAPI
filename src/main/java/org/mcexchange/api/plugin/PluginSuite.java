package org.mcexchange.api.plugin;

import java.util.List;

/**
 * When packaging an ExchangePlugin for use, you can set the "Exchange-Main"
 * attribute of its Manifest to point towards an implementation of PluginSuite
 * instead of an implementation of ExchangePlugin.
 * <br />
 * <br />
 * A PluginSuite is used to create the ExchangePlugin(s) in a jar if
 * the jar contains either one or more ExchangePlugins or if the jar's
 * ExchangePlugins require special configuration (such as fetching an
 * existing instance or passing arguments to the constructor).
 */
public interface PluginSuite {
	public List<ExchangePlugin> getPlugins();
}
