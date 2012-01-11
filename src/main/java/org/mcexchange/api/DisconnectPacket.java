package org.mcexchange.api;

import java.nio.ByteBuffer;

/**
 * When this packet is recieved, it signifies that the sender is closing the
 * connection and that the reciever should do likewise. It currently is not
 * very effective.
 */
public class DisconnectPacket extends Packet {
	
	public DisconnectPacket(Connection connection) {
		super(connection);
	}

	public void run() {
		System.out.println("disconnect.");
		Thread.currentThread().interrupt();
	}

	@Override
	public void read(ByteBuffer s) { }

	@Override
	public void write(ByteBuffer s) { }
	
}
