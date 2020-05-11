package org.uniprot.core.uniprotkb.evidence.impl;

import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.util.Utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EvidencedValueImpl extends HasEvidencesImpl implements EvidencedValue {
    private String value;

    // no arg constructor for JSON deserialization
    EvidencedValueImpl() {
        this.value = "";
    }

    protected EvidencedValueImpl(String value, List<Evidence> evidences) {
        super(evidences);
        this.value = Utils.emptyOrString(value);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EvidencedValueImpl that = (EvidencedValueImpl) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return getDisplayed(" ");
    }

    @Override
    public String getDisplayed(String separator) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        if (!getEvidences().isEmpty()) {
            sb.append(separator)
                    .append(
                            getEvidences().stream()
                                    .map(Evidence::getValue)
                                    .collect(Collectors.joining(", ", "{", "}")));
        }
        return sb.toString();
    }
}
