package me.retrodaredevil.solarthing.solar;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import me.retrodaredevil.solarthing.solar.batteryvoltage.BatteryVoltageOnlyPacket;
import me.retrodaredevil.solarthing.solar.outback.fx.FXStatusPacket;
import me.retrodaredevil.solarthing.solar.outback.mx.MXStatusPacket;
import me.retrodaredevil.solarthing.solar.renogy.rover.RoverStatusPacket;
import me.retrodaredevil.solarthing.solar.tracer.TracerStatusPacket;

@JsonSubTypes({
		@JsonSubTypes.Type(FXStatusPacket.class),
		@JsonSubTypes.Type(MXStatusPacket.class),
		@JsonSubTypes.Type(RoverStatusPacket.class),
		@JsonSubTypes.Type(BatteryVoltageOnlyPacket.class),
		@JsonSubTypes.Type(TracerStatusPacket.class),
})
public interface SolarStatusPacket extends SolarPacket<SolarStatusPacketType> {
}
