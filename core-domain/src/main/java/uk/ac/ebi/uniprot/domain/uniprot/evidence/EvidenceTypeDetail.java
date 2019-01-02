package uk.ac.ebi.uniprot.domain.uniprot.evidence;

public class EvidenceTypeDetail {
    private String name;
    private String displayName;
    private String uriLink;


    private EvidenceTypeDetail() {

    }

    public EvidenceTypeDetail(String name,
                              String displayName,
                              String uriLink) {
        this.name = name;
        this.displayName = displayName;
        if (uriLink == null) {
            this.uriLink = "";
        } else
            this.uriLink = uriLink;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        EvidenceTypeDetail other = (EvidenceTypeDetail) obj;
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
