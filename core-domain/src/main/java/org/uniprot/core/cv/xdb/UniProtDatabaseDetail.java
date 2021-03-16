package org.uniprot.core.cv.xdb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UniProtDatabaseDetail implements Serializable {

    private static final long serialVersionUID = 8751881513996820892L;

    private static final UniProtDatabaseAttribute DEFAULT_ATTRIBUTE =
            new UniProtDatabaseAttribute("Description", "description", null);
    private String name;
    private String displayName;
    private UniProtDatabaseCategory category;
    private String uriLink;
    private List<UniProtDatabaseAttribute> attributes;

    private boolean implicit = false;
    private String linkedReason = null;
    private String idMappingName;

    UniProtDatabaseDetail() {
        this.attributes = new ArrayList<>();
        this.attributes.add(DEFAULT_ATTRIBUTE);
    }

    public UniProtDatabaseDetail(
            String name,
            String displayName,
            UniProtDatabaseCategory category,
            String uriLink,
            List<UniProtDatabaseAttribute> attributes,
            boolean implicit,
            String linkedReason,
            String idMappingName) {
        super();
        this.name = name;
        this.displayName = displayName;
        this.category = category;
        this.uriLink = uriLink;

        this.implicit = implicit;
        this.linkedReason = linkedReason;
        this.attributes = new ArrayList<>();
        if ((attributes != null) && !attributes.isEmpty()) this.attributes.addAll(attributes);
        else this.attributes.add(DEFAULT_ATTRIBUTE);
        this.idMappingName = idMappingName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UniProtDatabaseCategory getCategory() {
        return category;
    }

    public String getUriLink() {
        return uriLink;
    }

    public List<UniProtDatabaseAttribute> getAttributes() {
        return attributes;
    }

    public boolean isImplicit() {
        return implicit;
    }

    public String getLinkedReason() {
        return linkedReason;
    }

    public String getIdMappingName() {
        return idMappingName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.attributes,
                this.category,
                this.displayName,
                this.name,
                this.uriLink,
                this.implicit,
                this.linkedReason,
                this.idMappingName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        UniProtDatabaseDetail other = (UniProtDatabaseDetail) obj;

        return Objects.equals(this.attributes, other.attributes)
                && Objects.equals(this.category, other.category)
                && Objects.equals(this.displayName, other.displayName)
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.uriLink, other.uriLink)
                && Objects.equals(this.implicit, other.implicit)
                && Objects.equals(this.linkedReason, other.linkedReason)
                && Objects.equals(this.idMappingName, other.idMappingName);
    }
}
