package org.mcexchange.api;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * A basic packet. This packet is assigned a message, and when the packet is recieved,
 * its message is printed to the console.
 */
public class MessagePacket extends Packet {
	private String message;

	public MessagePacket(Connection connection) {
		super(connection);
	}

	public void run() {
		System.out.println(message);
	}

	@Override
	public void read(ByteBuffer s) throws IOException {
		message = NioUtil.readString(s);
	}

	@Override
	public void write(ByteBuffer s) throws IOException {
		NioUtil.writeString(s, message);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
