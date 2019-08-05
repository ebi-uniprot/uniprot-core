package org.uniprot.core.cv.xdb;

import java.io.Serializable;

public final class DBXRefTypeAttribute implements Serializable {
    private static final long serialVersionUID = -4851911006519587041L;
    private String name;
    private String xmlTag;
    private String uriLink;

    private DBXRefTypeAttribute() {

    }

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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((uriLink == null) ? 0 : uriLink.hashCode());
        result = prime * result + ((xmlTag == null) ? 0 : xmlTag.hashCode());
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
        DBXRefTypeAttribute other = (DBXRefTypeAttribute) obj;
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
        if (xmlTag == null) {
            return other.xmlTag == null;
        } else return xmlTag.equals(other.xmlTag);
    }

}
