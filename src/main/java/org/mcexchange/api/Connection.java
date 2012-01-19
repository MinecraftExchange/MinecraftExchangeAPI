package org.mcexchange.api;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;

import org.mcexchange.api.plugin.ConnectionPlugin;
import org.mcexchange.api.plugin.ExchangePlugin;
import org.mcexchange.api.plugin.PacketPlugin;

/**
 * Represents a connection from client to server or vise versa.
 */
public class Connection implements Runnable {
	public static final int MAX_PACKET_SIZE = 500;
	public final RegisteredPackets packets;
	
	private final SocketChannel channel;
	private final ByteBuffer read = ByteBuffer.allocateDirect(MAX_PACKET_SIZE);
	private final ByteBuffer write = ByteBuffer.allocateDirect(MAX_PACKET_SIZE);
	
	/**
	 * Creates a new connection with the given client.
	 * @param socket The client socket.
	 * @throws IOException If there is an error opening a connection to
	 * the client.
	 */
	public Connection(SocketChannel channel) throws IOException {
		packets = new RegisteredPackets(this);
		this.channel = channel;
		//register PacketPlugin packets.
		List<ExchangePlugin> pps = ExchangePlugin.plugins.get(PacketPlugin.class);
		if(pps != null) {
			for(ExchangePlugin ep : pps) {
				PacketPlugin pp = (PacketPlugin) ep;
				Map<Byte,Packet> packetmap = pp.getPackets(this);
				for(byte b : packetmap.keySet()) {
					Packet p = packetmap.get(b);
					packets.assignId(b, p);
					packets.assignPlugin(b, pp);
				}
				List<Byte> additional = pp.getNotifyPackets(this);
				for(byte b : additional) packets.assignPlugin(b, pp);
			}
		}
		List<ExchangePlugin> cps = ExchangePlugin.plugins.get(ConnectionPlugin.class);
		if(cps != null) {
			for(ExchangePlugin ep : cps) ((ConnectionPlugin) ep).onConnectionEstablished(this);
		}
		
	}
	
	/**
	 * Reads a Packet from the channel.
	 * @return The sent Packet.
	 */
	public Packet readPacket() {
		try {
			NioUtil.read(channel, read, 1);
			Packet p = packets.getPacket(read.get());
			NioUtil.read(channel, read, 2);
			short length = read.getShort();
			NioUtil.read(channel, read, length);
			p.read(read);
			for(PacketPlugin pp : packets.getPlugins(p)) pp.onPacketRecieved(this, p);
			return p;
		} catch(IOException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
			System.err.println("Client " + this + " received an un-registered packet!");
			System.err.println("Kicking client...");
			Thread.currentThread().interrupt();
		}
		return null;
	}
	
	/**
	 * Closes the connection.
	 */
	public void disconnect() {
		try {
			channel.close();
		} catch (IOException e) {
			System.err.println("Unable to disconnect client:");
			e.printStackTrace();
		}
		System.out.println("Successfully disconnected from client " +  this);
	}
	
	/**
	 * Sends the given Packet.
	 */
	public void sendPacket(Packet p) {
		try {
			write.clear();
			write.put(packets.getId(p));
			write.position(3);
			p.write(write);
			write.flip();
			write.position(1);
			write.putShort((short) ( write.limit()-3));
			NioUtil.writeFull(channel,write);
		} catch(IOException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			System.err.println("Tried to send an unregistered packet to Client " + this + "!");
		}
		
	}
	
	/**
	 * And the magic happens here.
	 */
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			Packet recieved = readPacket();
			if(recieved!=null)recieved.run();
		}
		sendPacket(packets.getDisconnect());
		disconnect();
		List<ExchangePlugin> cps = ExchangePlugin.plugins.get(ConnectionPlugin.class);
		if(cps != null) {
			for(ExchangePlugin ep : cps) ((ConnectionPlugin) ep).onConnectionEnd(this);
		}
	}
}
