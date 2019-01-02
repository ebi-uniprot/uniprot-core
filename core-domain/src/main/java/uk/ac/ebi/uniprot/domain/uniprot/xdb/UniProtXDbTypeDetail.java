package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import java.util.ArrayList;
import java.util.List;

public class UniProtXDbTypeDetail {
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((uriLink == null) ? 0 : uriLink.hashCode());
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
        UniProtXDbTypeDetail other = (UniProtXDbTypeDetail) obj;
        if (attributes == null) {
            if (other.attributes != null)
                return false;
        } else if (!attributes.equals(other.attributes))
            return false;
        if (category != other.category)
            return false;
        if (displayName == null) {
            if (other.displayName != null)
                return false;
        } else if (!displayName.equals(other.displayName))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (uriLink == null) {
            if (other.uriLink != null)
                return false;
        } else if (!uriLink.equals(other.uriLink))
            return false;
        return true;
    }


}
