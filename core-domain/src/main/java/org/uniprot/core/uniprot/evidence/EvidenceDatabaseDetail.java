package org.uniprot.core.uniprot.evidence;

import java.io.Serializable;

import org.uniprot.core.util.Utils;

public class EvidenceDatabaseDetail implements Serializable {
    private static final long serialVersionUID = 4511309637271346914L;
    private String name;
    private String displayName;
    private EvidenceDatabaseCategory category;
    private String uriLink;

    // no arg constructor for JSON deserialization
    EvidenceDatabaseDetail() {
        uriLink = "";
    }

    public EvidenceDatabaseDetail(
            String name, String displayName, EvidenceDatabaseCategory category, String uriLink) {
        this.name = name;
        this.displayName = displayName;
        this.category = category;
        this.uriLink = Utils.emptyOrString(uriLink);
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUriLink() {
        return uriLink;
    }

    public EvidenceDatabaseCategory getCategory() {
        return category;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((uriLink == null) ? 0 : uriLink.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        EvidenceDatabaseDetail other = (EvidenceDatabaseDetail) obj;
        if (category == null) {
            if (other.category != null) return false;
        } else if (!category.equals(other.category)) return false;
        if (displayName == null) {
            if (other.displayName != null) return false;
        } else if (!displayName.equals(other.displayName)) return false;
        if (name == null) {
            if (other.name != null) return false;
        } else if (!name.equals(other.name)) return false;
        if (!uriLink.equals(other.uriLink)) return false;
        return true;
    }
}
