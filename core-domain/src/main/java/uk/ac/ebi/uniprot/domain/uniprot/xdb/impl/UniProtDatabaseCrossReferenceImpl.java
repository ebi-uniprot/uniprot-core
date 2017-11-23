package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtIsoformId;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtIsoformIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ValueImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDatabaseCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DbXRefAttribute;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UniProtDatabaseCrossReferenceImpl implements UniProtDatabaseCrossReference {

    private static final String DASH = "-";
    private final DatabaseType type;
    private final String id;
    private final String description;
    private final Optional<DbXRefAttribute> thirdAttribute;
    private final Optional<DbXRefAttribute> fourthAttribute;
    private final Optional<UniProtIsoformId> isoformId;
    private final List<Evidence> evidences;

    public static DbXRefAttribute createDbXRefAttribute(String val) {
        return new DbXRefAttributeImpl(val);
    }

    public UniProtDatabaseCrossReferenceImpl(DatabaseType type,
        String id, String description) {
        this(type, id, description, (String) null, null, null);
    }

    public UniProtDatabaseCrossReferenceImpl(DatabaseType type,
        String id, String description, String thirdAttribute) {
        this(type, id, description, thirdAttribute, null, null);
    }

    public UniProtDatabaseCrossReferenceImpl(DatabaseType type,
        String id, String description, String thirdAttribute,
        String fourthAttribute, String isoformId) {
        this(type, id, description, thirdAttribute, fourthAttribute, isoformId, null);
    }

    public UniProtDatabaseCrossReferenceImpl(DatabaseType type,
        String id, String description, String thirdAttribute,
        String fourthAttribute, String isoformId, List<Evidence> evidences) {
        this.type = type;
        this.id = id;
        this.description = description;
        this.thirdAttribute =
                createOptionalAttribute(thirdAttribute != null ? createDbXRefAttribute(thirdAttribute) : null, 3, type);
        this.fourthAttribute = createOptionalAttribute(
                fourthAttribute != null ? createDbXRefAttribute(fourthAttribute) : null, 4, type);
        if (isoformId == null) {
            this.isoformId = Optional.empty();
        } else
            this.isoformId = Optional.of(new UniProtIsoformIdImpl(isoformId));
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(evidences);
        }
    }

    public UniProtDatabaseCrossReferenceImpl(DatabaseType type,
        String id, String description, DbXRefAttribute thirdAttribute,
        DbXRefAttribute fourthAttribute, UniProtIsoformId isoformId) {
        this(type, id, description, thirdAttribute, fourthAttribute, isoformId, null);
    }

    public UniProtDatabaseCrossReferenceImpl(DatabaseType type,
        String id, String description, DbXRefAttribute thirdAttribute,
        DbXRefAttribute fourthAttribute, UniProtIsoformId isoformId, List<Evidence> evidences) {
        this.type = type;
        this.id = id;
        this.description = description;
        this.thirdAttribute = createOptionalAttribute(thirdAttribute, 3, type);
        this.fourthAttribute = createOptionalAttribute(fourthAttribute, 4, type);
        if (isoformId == null) {
            this.isoformId = Optional.empty();
        } else
            this.isoformId = Optional.of(isoformId);
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(evidences);
        }
    }

    private Optional<DbXRefAttribute> createOptionalAttribute(DbXRefAttribute attribute, int position,
            DatabaseType type) {
        if (type.getNumberOfAttribute() >= position) {
            if (attribute == null) {
                return Optional.of(new DbXRefAttributeImpl(DASH));
            } else {
                return Optional.of(attribute);
            }
        } else {
            return Optional.empty();
        }
    }

    @Override
    public DatabaseType getDatabase() {
        return type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Optional<DbXRefAttribute> getThirdAttribute() {
        return thirdAttribute;
    }

    @Override
    public Optional<DbXRefAttribute> getFourthAttribute() {
        return fourthAttribute;
    }

    @Override
    public Optional<UniProtIsoformId> getIsoformId() {
        return isoformId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDatabase().getDisplayName());
        sb.append(getId()).append("; ");
        sb.append(getDescription());
        if (thirdAttribute.isPresent()) {
            sb.append("; ");
            sb.append(thirdAttribute.get().getValue());
            if (fourthAttribute.isPresent()) {
                sb.append("; ");
                sb.append(fourthAttribute.get().getValue());
            }
        }
        sb.append(".");

        if ((isoformId.isPresent())) {
            sb.append(" [").append(isoformId.get().getValue()).append("]");
        }
        return sb.toString();
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + ((fourthAttribute == null) ? 0 : fourthAttribute.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isoformId == null) ? 0 : isoformId.hashCode());
        result = prime * result + ((thirdAttribute == null) ? 0 : thirdAttribute.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UniProtDatabaseCrossReferenceImpl other = (UniProtDatabaseCrossReferenceImpl) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (evidences == null) {
            if (other.evidences != null)
                return false;
        } else if (!evidences.equals(other.evidences))
            return false;
        if (fourthAttribute == null) {
            if (other.fourthAttribute != null)
                return false;
        } else if (!fourthAttribute.equals(other.fourthAttribute))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isoformId == null) {
            if (other.isoformId != null)
                return false;
        } else if (!isoformId.equals(other.isoformId))
            return false;
        if (thirdAttribute == null) {
            if (other.thirdAttribute != null)
                return false;
        } else if (!thirdAttribute.equals(other.thirdAttribute))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    static class DbXRefAttributeImpl extends ValueImpl implements DbXRefAttribute {
        public DbXRefAttributeImpl(String value) {
            super(value);
        }
    }
}
