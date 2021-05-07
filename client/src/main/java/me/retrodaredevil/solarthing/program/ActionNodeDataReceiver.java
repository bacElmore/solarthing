package me.retrodaredevil.solarthing.program;

import me.retrodaredevil.action.Action;
import me.retrodaredevil.action.ActionMultiplexer;
import me.retrodaredevil.action.Actions;
import me.retrodaredevil.solarthing.DataSource;
import me.retrodaredevil.solarthing.PacketGroupReceiver;
import me.retrodaredevil.solarthing.SolarThingConstants;
import me.retrodaredevil.solarthing.actions.ActionNode;
import me.retrodaredevil.solarthing.actions.environment.ActionEnvironment;
import me.retrodaredevil.solarthing.actions.environment.InjectEnvironment;
import me.retrodaredevil.solarthing.actions.environment.VariableEnvironment;
import me.retrodaredevil.solarthing.commands.packets.open.CommandOpenPacket;
import me.retrodaredevil.solarthing.commands.packets.open.CommandOpenPacketType;
import me.retrodaredevil.solarthing.commands.packets.open.RequestCommandPacket;
import me.retrodaredevil.solarthing.packets.Packet;
import me.retrodaredevil.solarthing.packets.collection.TargetPacketGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class ActionNodeDataReceiver implements PacketGroupReceiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionNodeDataReceiver.class);
	private final VariableEnvironment variableEnvironment = new VariableEnvironment();
	private final ActionMultiplexer actionMultiplexer = new Actions.ActionMultiplexerBuilder().build();

	private final Map<String, ActionNode> actionNodeMap;

	public ActionNodeDataReceiver(Map<String, ActionNode> actionNodeMap) {
		this.actionNodeMap = actionNodeMap;
	}

	protected abstract void updateInjectEnvironment(DataSource dataSource, InjectEnvironment.Builder injectEnvironmentBuilder);

	public Action getActionUpdater() {
		return actionMultiplexer;
	}

	@Override
	public void receivePacketGroup(String sender, TargetPacketGroup packetGroup) {
		for (Packet packet : packetGroup.getPackets()) {
			if (packet instanceof CommandOpenPacket) {
				CommandOpenPacket commandOpenPacket = (CommandOpenPacket) packet;
				if (commandOpenPacket.getPacketType() == CommandOpenPacketType.REQUEST_COMMAND) {
					RequestCommandPacket requestCommand = (RequestCommandPacket) commandOpenPacket;
					receiveData(sender, packetGroup.getDateMillis(), requestCommand.getCommandName());
				}
			}
		}
	}

	private void receiveData(String sender, long dateMillis, String data) {
		ActionNode requested = actionNodeMap.get(data);
		if(requested != null){
			DataSource dataSource = new DataSource(sender, dateMillis, data);
			InjectEnvironment.Builder injectEnvironmentBuilder = new InjectEnvironment.Builder();
			updateInjectEnvironment(dataSource, injectEnvironmentBuilder);
			Action action = requested.createAction(new ActionEnvironment(variableEnvironment, new VariableEnvironment(), injectEnvironmentBuilder.build()));
			actionMultiplexer.add(action);
			LOGGER.info(SolarThingConstants.SUMMARY_MARKER, sender + " has requested command sequence: " + data);
		} else {
			LOGGER.info(SolarThingConstants.SUMMARY_MARKER, "Sender: " + sender + " has requested unknown command: " + data);
		}
	}

}
