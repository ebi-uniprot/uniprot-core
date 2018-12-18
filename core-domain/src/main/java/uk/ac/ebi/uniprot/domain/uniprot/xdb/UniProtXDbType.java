package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import uk.ac.ebi.uniprot.domain.DatabaseType;

public final class UniProtXDbType implements DatabaseType {
    private String name;

    private UniProtXDbType() {

    }

    public UniProtXDbType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public UniProtXDbTypeDetail getDetail() {
        return UniProtXDbTypes.INSTANCE.getType(name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        UniProtXDbType other = (UniProtXDbType) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
