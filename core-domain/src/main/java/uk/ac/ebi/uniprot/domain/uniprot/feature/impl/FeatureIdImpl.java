package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class FeatureIdImpl implements FeatureId {

    private final static Map<FeatureType, List<Pattern>> FEATUREID_REGEX_MAP = new EnumMap<>(FeatureType.class);
    private final static Pattern PRO_PATTERN = Pattern.compile("PRO_(\\d+)");
    private final static Pattern CAR_PATTERN = Pattern.compile("CAR_(\\d+)");
    private final static Pattern VSP_PATTERN = Pattern.compile("VSP_(\\d+)");
    private final static Pattern VAR_PATTERN = Pattern.compile("VAR_(\\d+)");

    static {
        FEATUREID_REGEX_MAP.put(FeatureType.CHAIN, Arrays.asList(PRO_PATTERN));
        FEATUREID_REGEX_MAP.put(FeatureType.CARBOHYD, Arrays.asList(PRO_PATTERN, CAR_PATTERN, VSP_PATTERN));
        FEATUREID_REGEX_MAP.put(FeatureType.PEPTIDE, Arrays.asList(PRO_PATTERN));
        FEATUREID_REGEX_MAP.put(FeatureType.PROPEP, Arrays.asList(PRO_PATTERN));
        FEATUREID_REGEX_MAP.put(FeatureType.VAR_SEQ, Arrays.asList(VSP_PATTERN));
        FEATUREID_REGEX_MAP.put(FeatureType.VARIANT, Arrays.asList(VAR_PATTERN));
    }

    ;
    private String value;

    private FeatureIdImpl() {
        this.value = "";
    }

    public FeatureIdImpl(String value) {
        this.value = Utils.nullToEmpty(value);
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
        return Utils.notEmpty(this.value);
    }

    @Override
    public boolean isValid(FeatureType type) {
        List<Pattern> patterns = FEATUREID_REGEX_MAP.get(type);
        if (patterns == null)
            return true;
        if (value == null)
            return false;
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
