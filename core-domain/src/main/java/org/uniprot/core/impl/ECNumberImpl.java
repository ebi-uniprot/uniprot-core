package org.uniprot.core.impl;

import java.util.regex.Pattern;

import org.uniprot.core.ECNumber;
import org.uniprot.core.util.Utils;

public class ECNumberImpl implements ECNumber {
    private static final Pattern EC_PATTERN = Pattern.compile("\\d{1,2}(\\.(\\-|n?\\d{1,3})){3}");
    private static final long serialVersionUID = -9050725609382601827L;
    private String value;

    private ECNumberImpl() {
        this.value = "";
    }

    public ECNumberImpl(String value) {
        this.value = value;
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
    public boolean isValid() {
        return EC_PATTERN.matcher(value).matches();
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
        ECNumberImpl other = (ECNumberImpl) obj;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }

}
