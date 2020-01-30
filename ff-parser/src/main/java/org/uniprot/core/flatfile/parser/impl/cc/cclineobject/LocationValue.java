package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

public class LocationValue {
    private String value;
    private LocationFlagEnum flag;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocationFlagEnum getFlag() {
        return flag;
    }

    public void setFlag(LocationFlagEnum flag) {
        this.flag = flag;
    }

    public enum LocationFlagEnum {
        BY_SIMILARITY,
        PROBABLE,
        POTENTIAL,
        FLAG;

        public static LocationFlagEnum fromSting(String s) {
            String s1 = s.toLowerCase();
            if (s1.indexOf("by") >= 0 && s.indexOf("similarity") > 0) return BY_SIMILARITY;
            else if (s1.indexOf("probable") >= 0) {
                return PROBABLE;
            } else if (s1.indexOf("potential") >= 0) {
                return POTENTIAL;
            } else throw new RuntimeException(s + " cannot be parsed to the location flag");
        }
    }
}
