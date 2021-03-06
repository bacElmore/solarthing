package me.retrodaredevil.solarthing.solar.outback.fx.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.retrodaredevil.solarthing.annotations.*;
import me.retrodaredevil.solarthing.packets.ChangePacket;
import me.retrodaredevil.solarthing.packets.Modes;
import me.retrodaredevil.solarthing.solar.event.SolarEventPacketType;
import me.retrodaredevil.solarthing.solar.event.SupplementarySolarEventPacket;
import me.retrodaredevil.solarthing.solar.outback.SupplementaryOutbackPacket;
import me.retrodaredevil.solarthing.solar.outback.fx.OperationalMode;


@JsonDeserialize(as = ImmutableFXOperationalModeChangePacket.class)
@JsonTypeName("FX_OPERATIONAL_MODE_CHANGE")
@JsonExplicit
public interface FXOperationalModeChangePacket extends SupplementarySolarEventPacket, SupplementaryOutbackPacket, ChangePacket {
	@DefaultFinal
	@Override
	default @NotNull SolarEventPacketType getPacketType(){
		return SolarEventPacketType.FX_OPERATIONAL_MODE_CHANGE;
	}

	@JsonProperty("operationalModeValue")
	int getOperationalModeValue();
	@JsonProperty("previousOperationalModeValue")
	@Nullable Integer getPreviousOperationalModeValue();

	@GraphQLInclude("operationalMode")
	default OperationalMode getOperationalMode(){ return Modes.getActiveMode(OperationalMode.class, getOperationalModeValue()); }
	@GraphQLInclude("previousOperationalMode")
	default @Nullable OperationalMode getPreviousOperationalMode(){
		Integer previous = getPreviousOperationalModeValue();
		if(previous == null){
			return null;
		}
		return Modes.getActiveMode(OperationalMode.class, previous);
	}

	@Override
	default boolean isLastUnknown() {
		return getPreviousOperationalModeValue() == null;
	}
}
