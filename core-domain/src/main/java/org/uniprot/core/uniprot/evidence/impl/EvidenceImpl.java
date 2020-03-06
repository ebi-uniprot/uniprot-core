package org.uniprot.core.uniprot.evidence.impl;

import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;

public class EvidenceImpl implements Evidence {
    private static final long serialVersionUID = 6892404810238028657L;
    private static final String PIPE = "|";
    private static final String COLON = ":";
    private EvidenceCode evidenceCode;
    private CrossReference<EvidenceDatabase> evidenceCrossReference;

    public EvidenceImpl(EvidenceCode evidenceCode, String databaseName, String dbId) {
        this(evidenceCode, new CrossReferenceImpl<>(new EvidenceDatabase(databaseName), dbId));
    }

    // no arg constructor for JSON deserialization
    EvidenceImpl() {}

    public EvidenceImpl(
            EvidenceCode evidenceCode, CrossReference<EvidenceDatabase> evidenceCrossReference) {
        this.evidenceCode = evidenceCode;
        this.evidenceCrossReference = evidenceCrossReference;
    }

    @Override
    public EvidenceCode getEvidenceCode() {
        return evidenceCode;
    }

    @Override
    public CrossReference<EvidenceDatabase> getEvidenceCrossReference() {
        return evidenceCrossReference;
    }

    @Override
    public int compareTo(Evidence o) {
        return this.getValue().compareToIgnoreCase(o.getValue());
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public String getValue() {
        StringBuilder sb = new StringBuilder();
        sb.append(evidenceCode.getCode());
        if (evidenceCrossReference != null) {
            sb.append(PIPE);
            if (evidenceCrossReference.getDatabase().isReference()) {
                sb.append(evidenceCrossReference.getId());
            } else {
                sb.append(
                                evidenceCrossReference
                                        .getDatabase()
                                        .getEvidenceDatabaseDetail()
                                        .getDisplayName())
                        .append(COLON)
                        .append(evidenceCrossReference.getId());
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvidenceImpl evidence = (EvidenceImpl) o;
        return evidenceCode == evidence.evidenceCode
                && Objects.equals(evidenceCrossReference, evidence.evidenceCrossReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evidenceCode, evidenceCrossReference);
    }
}
