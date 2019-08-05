package org.uniprot.core.cv.ec.impl;

import java.util.Objects;

import org.uniprot.core.cv.ec.EC;

/**
 * Created 15/03/19
 *
 * @author Edd
 */
public class ECImpl implements EC {
    private final String id;
    private final String label;

    public ECImpl(String id, String label) {
        this.id = id;
        this.label = label;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ECImpl ec = (ECImpl) o;
        return Objects.equals(id, ec.id) &&
                Objects.equals(label, ec.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }

    public static class Builder {
        private String id;
        private String label;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public EC build() {
            return new ECImpl(id, label);
        }
    }
}
