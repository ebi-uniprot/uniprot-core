package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import uk.ac.ebi.uniprot.domain.DatabaseType;

import java.io.Serializable;
import java.util.List;

public final class UniProtXDbType implements DatabaseType, Serializable {
    private static final long serialVersionUID = 201534956573963997L;
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

    public DBXRefTypeAttribute getAttribute(int position) {
        DBXRefTypeAttribute toReturn = null;
        List<DBXRefTypeAttribute> attributes = UniProtXDbTypes.INSTANCE.getType(name).getAttributes();
        if (attributes.size() > position) {
            toReturn = attributes.get(position);
        }
        return toReturn;
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
            return other.name == null;
        } else return name.equals(other.name);
    }

}
