package org.mcexchange.api.plugin;

/**
 * A PluginPlugin provides new types of ExchangePlugin.
 */
public interface PluginPlugin extends ExchangePlugin {
	/**
	 * Gets the list of ExchangePlugin classes that will be registered.
	 */
	public Class<? extends ExchangePlugin>[] getPlugins();
	
	/**
	 * This method is called once an instance of a class that implements
	 * one of the interfaces gotten from getPlugins() is found. After this
	 * method is called, the object [that this method has been called on]
	 * assumes full responsibility over the object. Any methods that need
	 * to be called need to be handled by the object.
	 */
	public void registerPlugin(ExchangePlugin plugin);
}
