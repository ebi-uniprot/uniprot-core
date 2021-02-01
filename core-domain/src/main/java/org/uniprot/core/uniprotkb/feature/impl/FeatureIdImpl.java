package org.uniprot.core.uniprotkb.feature.impl;

import java.util.*;
import java.util.regex.Pattern;

import org.uniprot.core.uniprotkb.feature.FeatureId;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;
import org.uniprot.core.util.Utils;

public class FeatureIdImpl implements FeatureId {

    private static final Map<UniprotKBFeatureType, List<Pattern>> FEATUREID_REGEX_MAP =
            new EnumMap<>(UniprotKBFeatureType.class);
    private static final Pattern PRO_PATTERN = Pattern.compile("PRO_(\\d+)");
    private static final Pattern CAR_PATTERN = Pattern.compile("CAR_(\\d+)");
    private static final Pattern VSP_PATTERN = Pattern.compile("VSP_(\\d+)");
    private static final Pattern VAR_PATTERN = Pattern.compile("VAR_(\\d+)");
    private static final long serialVersionUID = 2993129119240452004L;

    static {
        FEATUREID_REGEX_MAP.put(UniprotKBFeatureType.CHAIN, Collections.singletonList(PRO_PATTERN));
        FEATUREID_REGEX_MAP.put(
                UniprotKBFeatureType.CARBOHYD,
                Arrays.asList(PRO_PATTERN, CAR_PATTERN, VSP_PATTERN));
        FEATUREID_REGEX_MAP.put(
                UniprotKBFeatureType.PEPTIDE, Collections.singletonList(PRO_PATTERN));
        FEATUREID_REGEX_MAP.put(
                UniprotKBFeatureType.PROPEP, Collections.singletonList(PRO_PATTERN));
        FEATUREID_REGEX_MAP.put(
                UniprotKBFeatureType.VAR_SEQ, Collections.singletonList(VSP_PATTERN));
        FEATUREID_REGEX_MAP.put(
                UniprotKBFeatureType.VARIANT, Collections.singletonList(VAR_PATTERN));
    }

    private final String value;

    // no arg constructor for JSON deserialization
    FeatureIdImpl() {
        this.value = "";
    }

    FeatureIdImpl(String value) {
        this.value = Utils.emptyOrString(value);
    }

    public static boolean hasFeatureId(UniprotKBFeatureType type) {
        return FEATUREID_REGEX_MAP.containsKey(type);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isValid(UniprotKBFeatureType type) {
        List<Pattern> patterns = FEATUREID_REGEX_MAP.get(type);
        if (patterns == null) return true;
        if (value == null) return false;
        return patterns.stream().anyMatch(val -> val.matcher(value).matches());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        FeatureIdImpl that = (FeatureIdImpl) obj;
        return Objects.equals(value, that.value);
    }
}
