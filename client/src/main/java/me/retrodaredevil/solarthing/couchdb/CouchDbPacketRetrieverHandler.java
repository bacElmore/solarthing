package me.retrodaredevil.solarthing.couchdb;

import me.retrodaredevil.solarthing.InstantType;
import me.retrodaredevil.solarthing.JsonPacketReceiver;
import me.retrodaredevil.solarthing.packets.collection.PacketCollection;
import me.retrodaredevil.solarthing.packets.handling.PacketHandleException;
import me.retrodaredevil.solarthing.packets.handling.PacketHandler;

@Deprecated
public class CouchDbPacketRetrieverHandler implements PacketHandler {

	private final CouchDbPacketRetriever couchDbPacketRetriever;
	private final JsonPacketReceiver jsonPacketReceiver;

	public CouchDbPacketRetrieverHandler(CouchDbPacketRetriever couchDbPacketRetriever, JsonPacketReceiver jsonPacketReceiver){
		this.couchDbPacketRetriever = couchDbPacketRetriever;
		this.jsonPacketReceiver = jsonPacketReceiver;
	}
	@Override
	public void handle(PacketCollection packetCollection, InstantType instantType) throws PacketHandleException {
		jsonPacketReceiver.receivePackets(couchDbPacketRetriever.query());
	}
}
