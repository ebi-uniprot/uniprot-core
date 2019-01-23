package uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceLine;

import java.time.LocalDate;
import java.util.Objects;

public class EvidenceLineImpl implements EvidenceLine {
    private String evidence;
    private LocalDate createDate;
    private String curator;

    private EvidenceLineImpl() {
        curator = "";
        evidence = "";
    }

    public EvidenceLineImpl(String evidence, LocalDate createDate, String curator) {
        this.evidence = evidence;
        this.createDate = createDate;
        if (curator == null) {
            curator = "";
        }
        this.curator = curator;
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
        return Objects.equals(evidence, that.evidence) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(curator, that.curator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evidence, createDate, curator);
    }
}
