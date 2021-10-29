package me.retrodaredevil.solarthing.packets.collection;

import me.retrodaredevil.solarthing.annotations.NotNull;
import me.retrodaredevil.solarthing.packets.Packet;

/**
 * Represents a {@link FragmentedPacketGroup} where each packet has the same fragment id.
 * <p>
 * This also implements {@link BasicPacketGroup} indicating that {@link #getDateMillis(Packet)} returns null
 */
public interface InstancePacketGroup extends FragmentedPacketGroup, SourcedPacketGroup, BasicPacketGroup {

	/**
	 * @return The fragmentId, which is the same for each packet
	 */
	int getFragmentId();

	/**
	 * @deprecated Use {@link #getFragmentId()} instead
	 * @param packet The packet to get the fragmentId of
	 * @return {@link #getFragmentId()}
	 */
	@Deprecated
	@Override
	default int getFragmentId(Packet packet) {
		return getFragmentId();
	}

	@Deprecated
	@Override
	default @NotNull String getSourceId(Packet packet) {
		return getSourceId();
	}

	@Override
	default boolean hasSourceId(String sourceId) {
		return sourceId.equals(getSourceId());
	}

	@Override
	default boolean hasFragmentId(int fragmentId) {
		return fragmentId == getFragmentId();
	}
}
