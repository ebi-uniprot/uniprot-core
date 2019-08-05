package org.uniprot.core.uniprot.evidence.impl;


import java.util.Objects;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidenceType;

public class EvidenceImpl implements Evidence {
    private static final long serialVersionUID = 6892404810238028657L;
    static final EvidenceType REFERENCE = new EvidenceType("Reference");
    private static final String PIPE = "|";
    private static final String COLON = ":";
    private EvidenceCode evidenceCode;
    private DBCrossReference<EvidenceType> source;

    public EvidenceImpl(EvidenceCode evidenceCode,
                        String databaseName, String dbId) {
        this(evidenceCode, new DBCrossReferenceImpl<>(new EvidenceType(databaseName), dbId));
    }

    private EvidenceImpl() {}

    private EvidenceImpl(String value) {}

    public EvidenceImpl(EvidenceCode evidenceCode, DBCrossReference<EvidenceType> source) {
        this.evidenceCode = evidenceCode;
        this.source = source;
    }

    @Override
    public EvidenceCode getEvidenceCode() {
        return evidenceCode;
    }

    @Override
    public DBCrossReference<EvidenceType> getSource() {
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
            if (source.getDatabaseType().equals(REFERENCE)) {
                sb.append(source.getId());
            } else {
                if (source.getDatabaseType().getDetail() != null)
                    sb.append(source.getDatabaseType().getDetail().getDisplayName()).append(COLON)
                            .append(source.getId());
                else
                    sb.append(source.getDatabaseType().getName()).append(COLON).append(source.getId());
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvidenceImpl evidence = (EvidenceImpl) o;
        return evidenceCode == evidence.evidenceCode &&
                Objects.equals(source, evidence.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evidenceCode, source);
    }
}
