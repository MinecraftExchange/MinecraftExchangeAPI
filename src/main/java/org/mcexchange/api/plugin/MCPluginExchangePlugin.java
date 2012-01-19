package org.mcexchange.api.plugin;

/**
 * This is a special subinterface of ExchangePlugin that marks the given class as also a plugin for
 * the Minecraft Server its running in. The Client will load an instance of this class from the list
 * of Minecraft Server plugins instead of instantiating a new instance,
 */
public interface MCPluginExchangePlugin extends ExchangePlugin { }
