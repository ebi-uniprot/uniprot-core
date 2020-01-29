package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

public class LocationValue {
    private String value;
    private CcLineObject.LocationFlagEnum flag;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CcLineObject.LocationFlagEnum getFlag() {
        return flag;
    }

    public void setFlag(CcLineObject.LocationFlagEnum flag) {
        this.flag = flag;
    }
}
