package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import uk.ac.ebi.uniprot.domain.uniprot.description.Field;
import uk.ac.ebi.uniprot.domain.uniprot.description.FieldType;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.NameType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NameImpl implements Name {
    private final NameType type;
    private final List<Field>  fields;
    public NameImpl(NameType type,List<Field>  fields ){
        this.type =type;
        if((fields !=null) && !fields.isEmpty())
            this.fields = Collections.unmodifiableList(fields);
        else
            this.fields = Collections.emptyList();
    }
    @Override
    public NameType getNameType() {
        return type;
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsByType(FieldType fieldType) {
        return fields.stream().filter(val -> val.getType() ==fieldType)
                .collect(Collectors.toList());
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fields == null) ? 0 : fields.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        NameImpl other = (NameImpl) obj;
        if (fields == null) {
            if (other.fields != null)
                return false;
        } else if (!fields.equals(other.fields))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

}
