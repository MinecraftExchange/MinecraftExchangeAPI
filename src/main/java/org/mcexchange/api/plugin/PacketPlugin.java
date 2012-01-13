package org.mcexchange.api.plugin;

import java.util.Map;

import org.mcexchange.api.Connection;
import org.mcexchange.api.Packet;

/**
 * A PacketPlugin adds one or more Packets.
 */
public interface PacketPlugin extends ExchangePlugin {
	
	/**
	 * This method should return a Map of Packets to their id for the
	 * given Connection. New Packets should be created every time this
	 * method is called. It is recommended that the id for each Packet
	 * is configurable to avoid any incompatibilities. In addition, if,
	 * for some reason, the Packet(s) should only be registered for
	 * certain connections, then this method can return an empty array
	 * to indicate no new Packet's should be registered.
	 */
	public Map<Packet,Byte> getPackets(Connection c);
	
	/**
	 * This method is called when one of the Packets registered
	 * via getPackets is received. This method is called after
	 * the given Packet's "read()" method, but before its "run()"
	 * method.
	 */
	public void onPacketRecieved(Connection c, Packet p);
}
