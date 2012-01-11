package org.mcexchange.api;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

/**
 * Used to provide access to built-in packets. And to register additional packets. One instance of this class
 * is associated with every Connection instance and provides access to its Packet objects.
 */
public class RegisteredPackets {
	private final BidiMap packets = new DualHashBidiMap();
	private final DisconnectPacket disconnect;
	private final MessagePacket message;
	private final JoinLeavePacket joinleave;
	
	/**
	 * Creates a new RegisteredPackets instance for the given Connection. Multiple instances of this class CAN
	 * be created for each Connection, though only one can be used.
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
	 * Unmaps a Packet to an id.
	 */
	public void removeId(Packet packet) {
		packets.remove(packet);
	}
	
	/**
	 * Gets a packet's id (none, if it hasn't been mapped to
	 * one yet).
	 */
	public byte getId(Packet packet) {
		return (Byte) packets.getKey(packet);
	}
	
	/**
	 * Gets an id's packet (none if no Packet has been mapped
	 * to this id).
	 */
	public Packet getPacket(byte id) {
		return (Packet) packets.get(id);
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
