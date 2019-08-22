package org.uniprot.core.uniprot.feature.impl;

import org.uniprot.core.uniprot.feature.FeatureDescription;
import org.uniprot.core.util.Utils;

public class FeatureDescriptionImpl implements FeatureDescription {
    private static final long serialVersionUID = -1958668003264920501L;
    private String value;

    private FeatureDescriptionImpl() {
        value = "";
    }

    public FeatureDescriptionImpl(String value) {
        this.value = Utils.nullToEmpty(value);
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
        FeatureDescriptionImpl other = (FeatureDescriptionImpl) obj;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }

}