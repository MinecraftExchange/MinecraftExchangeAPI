package org.mcexchange.api.plugin;

import java.util.List;
import java.util.Map;

import org.mcexchange.api.Connection;
import org.mcexchange.api.Packet;

/**
 * A PacketPlugin adds one or more Packets and/or receives notification
 * when specified packets are received.
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
	public Map<Byte,Packet> getPackets(Connection c);
	
	/**
	 * Gets a list of additional packets this ExchangePlugin wishes to
	 * receive notifications on. Also, if, for some reason, an implementation
	 * wishes to receive notifications on only certain Connections, it
	 * is given the Connection object to which it is registering to listen
	 * for, so that it has the opportunity to not register for its
	 * Packets.
	 * <br />
	 * <br />
	 * This method may NOT return null. If no packets are to be
	 * registered, then simply return an empty list.
	 */
	public List<Byte> getNotifyPackets(Connection c);
	
	/**
	 * This method is called when one of the Packets registered
	 * via getPackets is received. This method is called after
	 * the given Packet's "read()" method, but before its "run()"
	 * method.
	 */
	public void onPacketRecieved(Connection c, Packet p);
}
