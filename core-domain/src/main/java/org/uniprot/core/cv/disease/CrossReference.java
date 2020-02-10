package org.uniprot.core.cv.disease;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CrossReference implements Serializable {
    private static final long serialVersionUID = -1741904153011477476L;
    private final String databaseType;
    private final String id;
    private final List<String> properties;

    private CrossReference() {
        this(null, null, Collections.emptyList());
    }

    public CrossReference(String databaseType, String id) {
        this(databaseType, id, Collections.emptyList());
    }

    public CrossReference(String databaseType, String id, List<String> properties) {
        this.databaseType = databaseType;
        this.id = id;
        this.properties = properties;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public String getId() {
        return id;
    }

    public List<String> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(databaseType).append("; ").append(id);
        for (String property : properties) {
            sb.append("; ").append(property);
        }
        sb.append(".");

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.databaseType, this.properties);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        CrossReference other = (CrossReference) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.databaseType, other.databaseType)
                && Objects.equals(this.properties, other.properties);
    }
}
