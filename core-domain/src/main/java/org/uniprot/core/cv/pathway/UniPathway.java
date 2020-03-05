package org.uniprot.core.cv.pathway;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Deprecated
// Should be deleted with TRM-23729
public class UniPathway implements Comparable<UniPathway>, Serializable {
    private static final long serialVersionUID = 7579189176261471604L;
    private final String accession;
    private final String name;
    private UniPathway parent;
    private List<UniPathway> children = new ArrayList<>();

    public UniPathway(String accession, String name) {
        this.accession = accession;
        this.name = name;
    }

    public String getAccession() {
        return accession;
    }

    public String getName() {
        return name;
    }

    public void setParent(UniPathway parent) {
        this.parent = parent;
    }

    public void setChildren(List<UniPathway> children) {
        this.children = children;
    }

    public UniPathway getParent() {
        return parent;
    }

    public List<UniPathway> getChildren() {
        return children;
    }

    @Override
    public int compareTo(UniPathway o) {
        return name.compareTo(o.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.accession);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UniPathway other = (UniPathway) obj;
        return Objects.equals(this.accession, other.accession)
                && Objects.equals(this.name, other.name);
    }
}