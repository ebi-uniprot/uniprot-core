package org.uniprot.core.uniprotkb.evidence.impl;

import static org.uniprot.core.util.Utils.notNullNotEmpty;
import static org.uniprot.core.util.Utils.unmodifiableList;

import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HasEvidencesImpl implements HasEvidences {
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    HasEvidencesImpl() {
        this.evidences = Collections.emptyList();
    }

    protected HasEvidencesImpl(List<Evidence> evidences) {
        this.evidences = unmodifiableList(evidences);
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public boolean hasEvidences() {
        return notNullNotEmpty(this.evidences);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HasEvidencesImpl that = (HasEvidencesImpl) o;
        return evidences.equals(that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evidences);
    }
}
