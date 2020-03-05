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
    private CrossReference<EvidenceDatabase> source;

    public EvidenceImpl(EvidenceCode evidenceCode, String databaseName, String dbId) {
        this(evidenceCode, new CrossReferenceImpl<>(new EvidenceDatabase(databaseName), dbId));
    }

    // no arg constructor for JSON deserialization
    EvidenceImpl() {}

    public EvidenceImpl(EvidenceCode evidenceCode, CrossReference<EvidenceDatabase> source) {
        this.evidenceCode = evidenceCode;
        this.source = source;
    }

    @Override
    public EvidenceCode getEvidenceCode() {
        return evidenceCode;
    }

    @Override
    public CrossReference<EvidenceDatabase> getSource() {
        return source;
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
        if (source != null) {
            sb.append(PIPE);
            if (source.getDatabase().isReference()) {
                sb.append(source.getId());
            } else {
                sb.append(source.getDatabase().getDetail().getDisplayName())
                        .append(COLON)
                        .append(source.getId());
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvidenceImpl evidence = (EvidenceImpl) o;
        return evidenceCode == evidence.evidenceCode && Objects.equals(source, evidence.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evidenceCode, source);
    }
}
