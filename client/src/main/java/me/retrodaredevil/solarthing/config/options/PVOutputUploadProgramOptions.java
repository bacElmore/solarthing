package me.retrodaredevil.solarthing.config.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import me.retrodaredevil.solarthing.annotations.JsonExplicit;
import me.retrodaredevil.solarthing.packets.identification.IdentifierFragmentMatcher;
import me.retrodaredevil.solarthing.packets.identification.IdentifierRepFragment;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("FieldMayBeFinal")
@JsonTypeName("pvoutput-upload")
@JsonExplicit
public class PVOutputUploadProgramOptions extends DatabaseTimeZoneOptionBase implements AnalyticsOption, DatabaseOption, ProgramOptions {
	@JsonProperty(value = "system_id", required = true)
	private int systemId;
	@JsonProperty(value = "api_key", required = true)
	private String apiKey;

	@JsonProperty("required")
	private Map<Integer, List<String>> requiredIdentifierMap = null;

	@JsonProperty(AnalyticsOption.PROPERTY_NAME)
	private boolean isAnalyticsEnabled = AnalyticsOption.DEFAULT_IS_ANALYTICS_ENABLED;

	@JsonProperty("voltage_identifier")
	private IdentifierRepFragment voltageIdentifierFragmentObject = null;
	@JsonProperty("temperature_identifier")
	private IdentifierRepFragment temperatureIdentifierFragmentObject = null;

	@JsonProperty("include_import")
	private boolean includeImport = false;
	@JsonProperty("include_export")
	private boolean includeExport = false;

	@JsonProperty("join_teams")
	private boolean joinTeams = false;

	@Override
	public ProgramType getProgramType() {
		return ProgramType.PVOUTPUT_UPLOAD;
	}
	@Override
	public boolean isAnalyticsOptionEnabled() {
		return isAnalyticsEnabled;
	}

	public int getSystemId() {
		return systemId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public Map<Integer, List<String>> getRequiredIdentifierMap() {
		Map<Integer, List<String>> r = requiredIdentifierMap;
		if (r == null) {
			return Collections.emptyMap();
		}
		return r;
	}
	public IdentifierFragmentMatcher getVoltageIdentifierFragmentMatcher() {
		IdentifierRepFragment object = voltageIdentifierFragmentObject;
		if (object == null) {
			return IdentifierFragmentMatcher.NO_MATCH;
		}
		return object;
	}
	public IdentifierFragmentMatcher getTemperatureIdentifierFragmentMatcher() {
		IdentifierRepFragment object = temperatureIdentifierFragmentObject;
		if (object == null) {
			return IdentifierFragmentMatcher.NO_MATCH;
		}
		return object;
	}

	public boolean isIncludeImport() {
		return includeImport;
	}

	public boolean isIncludeExport() {
		return includeExport;
	}

	public boolean isJoinTeams() {
		return joinTeams;
	}

}
