package org.uniprot.core.uniprot.description.impl;

import static org.uniprot.core.util.Utils.unmodifiableList;

import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.impl.ECNumberImpl;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class ECImpl extends ECNumberImpl implements EC {
    private static final long serialVersionUID = -5418175231477354719L;
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    ECImpl() {
        this("", null);
    }

    public ECImpl(String value, List<Evidence> evidences) {
        super(value);
        this.evidences = unmodifiableList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullOrEmpty(this.evidences);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + evidences.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        ECImpl other = (ECImpl) obj;
        return evidences.equals(other.evidences);
    }

    @Override
    public String getDisplayed(String separator) {
        StringBuilder sb = new StringBuilder();
        sb.append(getValue());
        if (!evidences.isEmpty()) {
            sb.append(separator)
                    .append(
                            evidences.stream()
                                    .map(Evidence::getValue)
                                    .collect(Collectors.joining(", ", "{", "}")));
        }
        return sb.toString();
    }
}
