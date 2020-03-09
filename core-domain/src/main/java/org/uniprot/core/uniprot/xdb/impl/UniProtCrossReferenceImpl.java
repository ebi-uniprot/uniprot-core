package org.uniprot.core.uniprot.xdb.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.Property;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtDatabase;
import org.uniprot.core.util.Utils;

public class UniProtCrossReferenceImpl extends CrossReferenceImpl<UniProtDatabase>
        implements UniProtCrossReference {
    private static final long serialVersionUID = -3661768450999840694L;
    private static final String SEMICOLON = "; ";
    private static final String DASH = "-";
    private String isoformId;
    private List<Evidence> evidences;

    UniProtCrossReferenceImpl() {
        super(null, "", Collections.emptyList());
        evidences = Collections.emptyList();
    }

    UniProtCrossReferenceImpl(
            UniProtDatabase database,
            String id,
            List<Property> properties,
            String isoformId,
            List<Evidence> evidences) {
        super(database, id, properties);
        this.isoformId = isoformId;
        this.evidences = Utils.unmodifiableList(evidences);
    }

    public String getIsoformId() {
        return isoformId;
    }

    @Override
    public boolean hasIsoformId() {
        return Utils.notNullNotEmpty(this.isoformId);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullNotEmpty(this.evidences);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDatabaseName()).append(SEMICOLON);
        sb.append(getId()).append(SEMICOLON);
        if (this.getProperties().isEmpty()) {
            sb.append(DASH);
        } else {
            sb.append(
                    this.getProperties().stream()
                            .map(Property::getValue)
                            .collect(Collectors.joining(SEMICOLON)));
        }
        sb.append(".");

        if (isoformId != null && !isoformId.isEmpty()) {
            sb.append(" [").append(isoformId).append("]");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UniProtCrossReferenceImpl that = (UniProtCrossReferenceImpl) o;
        return Objects.equals(isoformId, that.isoformId)
                && Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isoformId, evidences);
    }

    private String getDatabaseName() {
        if (getDatabase() != null) return getDatabase().getName();
        return "";
    }
}
