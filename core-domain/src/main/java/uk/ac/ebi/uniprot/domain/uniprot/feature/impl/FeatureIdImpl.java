package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FeatureIdImpl implements FeatureId {
	
	private static final Map<FeatureType, Pattern> FEATUREID_REGEX_MAP = new EnumMap<>(FeatureType.class);
	static {
		FEATUREID_REGEX_MAP.put(FeatureType.CHAIN, Pattern.compile("PRO_(\\d+)"));
		FEATUREID_REGEX_MAP.put(FeatureType.CARBOHYD, Pattern.compile("PRO_(\\d+)"));
		FEATUREID_REGEX_MAP.put(FeatureType.PEPTIDE, Pattern.compile("PRO_(\\d+)"));
		FEATUREID_REGEX_MAP.put(FeatureType.PROPEP, Pattern.compile("PRO_(\\d+)"));
		FEATUREID_REGEX_MAP.put(FeatureType.VAR_SEQ, Pattern.compile("VSP_(\\d+)"));
		FEATUREID_REGEX_MAP.put(FeatureType.VARIANT, Pattern.compile("VAR_(\\d+)"));
	};
	public static FeatureIdImpl newInstance(String value) {
		return new FeatureIdImpl(value);
	}
	private final String value;
	@JsonCreator
	public FeatureIdImpl(@JsonProperty("value") String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}

	public static boolean hasFeatureId(FeatureType type) {
		return FEATUREID_REGEX_MAP.containsKey(type);
	}
	@JsonIgnore
	@Override
	public boolean isValid(FeatureType type) {
		Pattern pattern = FEATUREID_REGEX_MAP.get(type);
		if (pattern == null)
			return true;
		if (value == null)
			return false;
		return pattern.matcher(value).matches();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeatureIdImpl other = (FeatureIdImpl) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
