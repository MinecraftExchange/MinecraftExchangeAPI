package org.mcexchange.api;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * A Packet is a method of grouping data to be sent to the client or server.
 */
public abstract class Packet implements Runnable {
	private final Connection cc;
	
	/**
	 * Creates a Packet for the given ClientConnection.
	 */
	public Packet(Connection connection) {
		cc = connection;
	}
	
	public Connection getConnection() {
		return cc;
	}
	
	/**
	 * Reads the Packet's data from the buffer. The buffer is at position 0
	 * when given and is filled with the packet's data.
	 */
	public abstract void read(ByteBuffer b) throws IOException;
	
	/**
	 * Writes the Packet to the buffer. The buffer is at position 3 if no
	 * length has been specified for this packet, or 1 if one has. The first
	 * 3 bytes will be filled in once the data has been written, so don't
	 * store anything there. On a side note, each packet is limited to 500
	 * bytes including the initial 1 or 3.
	 */
	public abstract void write(ByteBuffer b) throws IOException;
}
