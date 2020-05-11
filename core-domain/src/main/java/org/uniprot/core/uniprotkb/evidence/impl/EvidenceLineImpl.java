package org.uniprot.core.uniprotkb.evidence.impl;

import static org.uniprot.core.util.Utils.emptyOrString;

import org.uniprot.core.uniprotkb.evidence.EvidenceLine;

import java.time.LocalDate;
import java.util.Objects;

public class EvidenceLineImpl implements EvidenceLine {
    private String evidence;
    private LocalDate createDate;
    private String curator;

    // no arg constructor for JSON deserialization
    EvidenceLineImpl() {
        curator = "";
        evidence = "";
    }

    EvidenceLineImpl(String evidence, LocalDate createDate, String curator) {
        this.evidence = emptyOrString(evidence);
        this.createDate = createDate;
        this.curator = emptyOrString(curator);
    }

    @Override
    public String getEvidence() {
        return evidence;
    }

    @Override
    public LocalDate getCreateDate() {
        return createDate;
    }

    @Override
    public String getCurator() {
        return curator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvidenceLineImpl that = (EvidenceLineImpl) o;
        return Objects.equals(evidence, that.evidence)
                && Objects.equals(createDate, that.createDate)
                && Objects.equals(curator, that.curator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evidence, createDate, curator);
    }
}
