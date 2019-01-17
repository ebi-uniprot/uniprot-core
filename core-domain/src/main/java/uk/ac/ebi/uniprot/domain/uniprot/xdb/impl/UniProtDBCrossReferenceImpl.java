package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;


import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.citation2.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UniProtDBCrossReferenceImpl extends DBCrossReferenceImpl<UniProtXDbType> implements UniProtDBCrossReference {

    private static final String SEMICOLON = "; ";
    private static final String DASH = "-";
    private String isoformId;
    private List<Evidence> evidences;

    private UniProtDBCrossReferenceImpl() {
        super(new DBCrossReferenceBuilder<>());
        evidences = Collections.emptyList();
    }

    public UniProtDBCrossReferenceImpl(UniProtXDbType database, String id, List<Property> properties, String isoformId) {
        this(database, id, properties, isoformId, Collections.emptyList());
    }

    public UniProtDBCrossReferenceImpl(UniProtXDbType database, String id, List<Property> properties, String isoformId, List<Evidence> evidences) {
        super(new DBCrossReferenceBuilder<UniProtXDbType>()
                      .databaseType(database)
                      .id(id)
                      .properties(properties));
        this.isoformId = isoformId;
        this.evidences = Utils.unmodifierList(evidences);
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
                            .map(val -> val.getValue())
                            .collect(Collectors.joining(SEMICOLON)));
        }
        sb.append(".");

        if (isoformId != null && !isoformId.isEmpty()) {
            sb.append(" [").append(isoformId).append("]");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((isoformId == null) ? 0 : isoformId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UniProtDBCrossReferenceImpl other = (UniProtDBCrossReferenceImpl) obj;
        if (isoformId == null) {
            if (other.isoformId != null)
                return false;
        } else if (!isoformId.equals(other.isoformId))
            return false;
        return true;
    }

    private String getDatabaseName() {
        return getDatabaseType().getName();
    }
}
