package org.mcexchange.api.plugin;

import org.mcexchange.api.Connection;

/**
 * One type of ExchangePlugin is a ConnectionPlugin. A ConnectionPlugin receives
 * notification on when a new Connection is established or unestablished.
 */
public interface ConnectionPlugin extends ExchangePlugin {
	public void onConnectionEstablished(Connection c);
	public void onConnectionEnd(Connection c);
}
