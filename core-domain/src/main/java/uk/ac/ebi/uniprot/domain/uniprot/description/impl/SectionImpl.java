package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.NameType;
import uk.ac.ebi.uniprot.domain.uniprot.description.Section;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SectionImpl implements Section {
    private final List<Name> names;

    public SectionImpl(List<Name> names) {
        if ((names != null) && !names.isEmpty())
            this.names = Collections.unmodifiableList(names);
        else
            this.names = Collections.emptyList();
    }

    @Override
    public List<Name> getNames() {
        return names;
    }
    
    @Override
    public boolean isValidSection() {
        List<Name> recNames = getNamesByType(NameType.RECNAME);
        if(recNames.size()>1)
            return false;
        List<Name> altNames = getNamesByType(NameType.ALTNAME);
        if(recNames.isEmpty() && !altNames.isEmpty())
            return false;
        return true;
    }

    @Override
    public List<Name> getNamesByType(NameType type) {
        return names.stream().filter(val -> val.getNameType() == type)
                .collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((names == null) ? 0 : names.hashCode());
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
        SectionImpl other = (SectionImpl) obj;
        if (names == null) {
            if (other.names != null)
                return false;
        } else if (!names.equals(other.names))
            return false;
        return true;
    }

   

}
