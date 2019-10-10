package org.uniprot.core.uniprot.evidence.impl;

import static org.uniprot.core.util.Utils.emptyOrString;

import java.time.LocalDate;
import java.util.Objects;

import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceLine;

public class EvidenceLineImpl implements EvidenceLine {
    private String evidence;
    private LocalDate createDate;
    private String curator;

    // no arg constructor for JSON deserialization
    EvidenceLineImpl() {
        curator = "";
        evidence = "";
    }

    public EvidenceLineImpl(String evidence, LocalDate createDate, String curator) {
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
    public Evidence toEvidence() {
        return EvidenceHelper.parseEvidenceLine(evidence);
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
