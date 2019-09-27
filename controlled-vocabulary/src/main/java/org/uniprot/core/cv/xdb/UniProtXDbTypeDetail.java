package org.uniprot.core.cv.xdb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UniProtXDbTypeDetail implements Serializable {
    private static final long serialVersionUID = 8751881513996820892L;
    private static final DBXRefTypeAttribute DEFAULT_ATTRIBUTE = new DBXRefTypeAttribute("Description",
                                                                                         "description", null);
    private String name;
    private String displayName;
    private DatabaseCategory category;
    private String uriLink;
    private List<DBXRefTypeAttribute> attributes;

    private UniProtXDbTypeDetail() {
        this.attributes = new ArrayList<>();
        this.attributes.add(DEFAULT_ATTRIBUTE);
    }

    public UniProtXDbTypeDetail(String name, String displayName, DatabaseCategory category,
                                String uriLink, List<DBXRefTypeAttribute> attributes) {
        super();
        this.name = name;
        this.displayName = displayName;
        this.category = category;
        this.uriLink = uriLink;

        this.attributes = new ArrayList<>();
        if ((attributes != null) && !attributes.isEmpty())
            this.attributes.addAll(attributes);
        else
            this.attributes.add(DEFAULT_ATTRIBUTE);

    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public DatabaseCategory getCategory() {
        return category;
    }

    public String getUriLink() {
        return uriLink;
    }

    public List<DBXRefTypeAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.attributes, this.category,
                this.displayName, this.name, this.uriLink);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        UniProtXDbTypeDetail other = (UniProtXDbTypeDetail) obj;

        return Objects.equals(this.attributes, other.attributes)
                &&
                Objects.equals(this.category, other.category)
                &&
                Objects.equals(this.displayName, other.displayName)
                &&
                Objects.equals(this.name, other.name)
                &&
                Objects.equals(this.uriLink, other.uriLink);

    }
}
