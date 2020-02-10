package org.uniprot.core.cv.xdb;

import java.io.Serializable;
import java.util.Objects;

public final class DBXRefTypeAttribute implements Serializable {
    private static final long serialVersionUID = -4851911006519587041L;
    private String name;
    private String xmlTag;
    private String uriLink;

    private DBXRefTypeAttribute() {}

    public DBXRefTypeAttribute(String name, String xmlTag, String uriLink) {
        this.name = name;
        this.xmlTag = xmlTag;
        this.uriLink = uriLink;
    }

    public String getName() {
        return name;
    }

    public String getXmlTag() {
        return xmlTag;
    }

    public String getUriLink() {
        return uriLink;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.xmlTag, this.uriLink);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        DBXRefTypeAttribute other = (DBXRefTypeAttribute) obj;
        return Objects.equals(this.name, other.name)
                && Objects.equals(this.uriLink, other.uriLink)
                && Objects.equals(this.xmlTag, other.xmlTag);
    }
}
