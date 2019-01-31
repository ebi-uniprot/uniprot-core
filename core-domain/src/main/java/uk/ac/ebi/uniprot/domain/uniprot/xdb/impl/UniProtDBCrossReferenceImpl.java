package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;


import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UniProtDBCrossReferenceImpl extends DBCrossReferenceImpl<UniProtXDbType> implements UniProtDBCrossReference {

    private static final String SEMICOLON = "; ";
    private static final String DASH = "-";
    private String isoformId;
    private List<Evidence> evidences;

    private UniProtDBCrossReferenceImpl() {
        super(null, "", Collections.emptyList());
        evidences = Collections.emptyList();
    }

    public UniProtDBCrossReferenceImpl(UniProtXDbType database, String id, List<Property> properties, String isoformId) {
        this(database, id, properties, isoformId, Collections.emptyList());
    }

    public UniProtDBCrossReferenceImpl(UniProtXDbType database, String id, List<Property> properties, String isoformId, List<Evidence> evidences) {
        super(database, id, properties);
        this.isoformId = isoformId;
        this.evidences = Utils.nonNullUnmodifiableList(evidences);
    }

    public String getIsoformId() {
        return isoformId;
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
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
        UniProtDBCrossReferenceImpl that = (UniProtDBCrossReferenceImpl) o;
        return Objects.equals(isoformId, that.isoformId) &&
                Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isoformId, evidences);
    }

    private String getDatabaseName() {
        return getDatabaseType().getName();
    }
}
