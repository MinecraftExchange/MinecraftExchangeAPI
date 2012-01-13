package org.mcexchange.api.plugin;

/**
 * An instance of ExchangePluginLoader is used to load all of the plugins.
 */
public interface ExchangePluginLoader {
	/**
	 * This method has two distinct implementations:
	 * <br />
	 * <h3>Client (Minecraft Server)</h3>
	 * This method first loads any build-in plugins. Second, it iterates through a
	 * list of loaded classes. The specifics  of the loaded class list may vary, but 
	 * they are typically the Plugin instances already loaded as Plugins for the
	 * Minecraft Server. Then, if an Instrument was set up, it uses its getAllLoadedClasses()
	 * method to search for ExchangePlugins. Finally, it looks in the "plugins"
	 * directory of its DataFolder for classes that implement ExchangePlugin.
	 * <3>Server (Exchange Server)</h3>
	 * On the server, things are much simpler. First it loads any built-in plugins.
	 * Then it loads all of the implementations of ExchangePlugin it can find in
	 * the "plugins" directory. That's it. :D
	 */
	public void loadPlugins();
}
