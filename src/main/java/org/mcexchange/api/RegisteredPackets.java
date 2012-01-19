package org.mcexchange.api;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.mcexchange.api.plugin.PacketPlugin;

/**
 * Used to provide access to built-in packets. And to register additional packets. One instance of this class
 * is associated with every Connection instance and provides access to its Packet objects.
 */
public class RegisteredPackets {
	private final BidiMap packets = new DualHashBidiMap();
	private final MultiValueMap extra = new MultiValueMap();
	private final DisconnectPacket disconnect;
	private final MessagePacket message;
	private final JoinLeavePacket joinleave;
	
	/**
	 * Creates a new RegisteredPackets instance for the given Connection. Nothing is stopping you 
	 * from creating another instance for a Connection, though a Connection will only use the one
	 * it creates for itself.
	 */
	public RegisteredPackets(Connection sc) {
		disconnect = new DisconnectPacket(sc);
		assignId((byte)-127, disconnect);
		message = new MessagePacket(sc);
		assignId((byte)-126, message);
		joinleave = new JoinLeavePacket(sc);
		assignId((byte)-125, joinleave);
	}
	
	/**
	 * Maps a Packet to an id.
	 */
	public void assignId(byte id, Packet packet) {
		packets.put(id,packet);
	}
	
	/**
	 * Gets an id's packet (none if no Packet has been mapped
	 * to this id).
	 */
	public Packet getPacket(byte id) {
		return (Packet) packets.get(id);
	}
	
	/**
	 * Gets a packet's id or throws an IllegalArgumentException if the Packet
	 * doesn't have an id.
	 */
	public byte getId(Packet packet) {
		if(packets.getKey(packet)==null) throw new IllegalArgumentException("The specified packet does not have an id.");
		return (Byte) packets.getKey(packet);
	}
	
	/**
	 * Unmaps a Packet to an id.
	 */
	public void removeId(Packet packet) {
		packets.remove(packet);
	}
	
	/**
	 * Registers a PacketPlugin to receive notification when the specified Packet
	 * is received.
	 */
	public void assignPlugin(Packet packet, PacketPlugin p) {
		assignPlugin(getId(packet), p);
	}
	
	/**
	 * Registers a PacketPlugin to receive notification when the specified Packet
	 * is received.
	 */
	public void assignPlugin(byte id, PacketPlugin p) {
		extra.put(id, p);
	}
	
	/**
	 * Gets all PacketPlugins registered to receive notification when the specified
	 * Packet is received.
	 */
	public Collection<PacketPlugin> getPlugins(Packet p) {
		return getPlugins(getId(p));
	}
	
	/**
	 * Gets all PacketPlugins registered to receive notification when the specified
	 * Packet is received.
	 */
	@SuppressWarnings("unchecked")
	public Collection<PacketPlugin> getPlugins(byte b) {
		return extra.getCollection(b)==null ? new ArrayList<PacketPlugin>() : extra.getCollection(b);
	}
	
	/**
	 * Unregisters a PacketPlugin to receive notification when the specified Packet
	 * is received.
	 */
	public void unregisterPlugin(Packet packet, PacketPlugin p) {
		unregisterPlugin(getId(packet),p);
	}
	
	/**
	 * Unregisters a PacketPlugin to receive notification when the specified Packet
	 * is received.
	 */
	public void unregisterPlugin(byte id, PacketPlugin p) {
		extra.remove(id, p);
	}
	
	public Packet getDisconnect() {
		return disconnect;
	}
	
	public MessagePacket getMessage() {
		return message;
	}
	
	public JoinLeavePacket getJoinLeave() {
		return joinleave;
	}
}
