package org.uniprot.core.uniprot.feature.impl;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.uniprot.core.uniprot.feature.FeatureId;
import org.uniprot.core.uniprot.feature.FeatureType;
import org.uniprot.core.util.Utils;

public class FeatureIdImpl implements FeatureId {

    private static final Map<FeatureType, List<Pattern>> FEATUREID_REGEX_MAP =
            new EnumMap<>(FeatureType.class);
    private static final Pattern PRO_PATTERN = Pattern.compile("PRO_(\\d+)");
    private static final Pattern CAR_PATTERN = Pattern.compile("CAR_(\\d+)");
    private static final Pattern VSP_PATTERN = Pattern.compile("VSP_(\\d+)");
    private static final Pattern VAR_PATTERN = Pattern.compile("VAR_(\\d+)");
    private static final long serialVersionUID = 2993129119240452004L;

    static {
        FEATUREID_REGEX_MAP.put(FeatureType.CHAIN, Arrays.asList(PRO_PATTERN));
        FEATUREID_REGEX_MAP.put(
                FeatureType.CARBOHYD, Arrays.asList(PRO_PATTERN, CAR_PATTERN, VSP_PATTERN));
        FEATUREID_REGEX_MAP.put(FeatureType.PEPTIDE, Arrays.asList(PRO_PATTERN));
        FEATUREID_REGEX_MAP.put(FeatureType.PROPEP, Arrays.asList(PRO_PATTERN));
        FEATUREID_REGEX_MAP.put(FeatureType.VAR_SEQ, Arrays.asList(VSP_PATTERN));
        FEATUREID_REGEX_MAP.put(FeatureType.VARIANT, Arrays.asList(VAR_PATTERN));
    }

    private String value;

    // no arg constructor for JSON deserialization
    FeatureIdImpl() {
        this.value = "";
    }

    public FeatureIdImpl(String value) {
        this.value = Utils.emptyOrString(value);
    }

    public static boolean hasFeatureId(FeatureType type) {
        return FEATUREID_REGEX_MAP.containsKey(type);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean hasValue() {
        return Utils.notNullNotEmpty(this.value);
    }

    @Override
    public boolean isValid(FeatureType type) {
        List<Pattern> patterns = FEATUREID_REGEX_MAP.get(type);
        if (patterns == null) return true;
        if (value == null) return false;
        return patterns.stream().anyMatch(val -> val.matcher(value).matches());
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
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        FeatureIdImpl other = (FeatureIdImpl) obj;
        return value.equals(other.value);
    }
}
