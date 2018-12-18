package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;

import java.util.Collections;
import java.util.List;

public class ProteinNameImpl implements ProteinName {
    private Name fullName;
    private List<Name> shortNames;
    private List<EC> ecNumbers;

    private ProteinNameImpl() {
        shortNames = Collections.emptyList();
        ecNumbers = Collections.emptyList();
    }

    public ProteinNameImpl(
            Name fullName,
            List<Name> shortNames,
            List<EC> ecNumbers) {
        this.fullName = fullName;
        if ((shortNames == null) || (shortNames.isEmpty())) {
            this.shortNames = Collections.emptyList();
        } else {
            this.shortNames = Collections.unmodifiableList(shortNames);
        }
        if ((ecNumbers == null) || (ecNumbers.isEmpty())) {
            this.ecNumbers = Collections.emptyList();
        } else {
            this.ecNumbers = Collections.unmodifiableList(ecNumbers);
        }
    }

    @Override
    public Name getFullName() {
        return fullName;
    }

    @Override
    public List<Name> getShortNames() {
        return shortNames;
    }

    @Override
    public List<EC> getEcNumbers() {
        return ecNumbers;
    }

    @Override
    public boolean isValid() {
        return (getFullName() != null) &&
                ((getFullName().getValue() != null)
                        && !getFullName().getValue().isEmpty());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ecNumbers == null) ? 0 : ecNumbers.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + ((shortNames == null) ? 0 : shortNames.hashCode());
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
        ProteinNameImpl other = (ProteinNameImpl) obj;
        if (ecNumbers == null) {
            if (other.ecNumbers != null)
                return false;
        } else if (!ecNumbers.equals(other.ecNumbers))
            return false;
        if (fullName == null) {
            if (other.fullName != null)
                return false;
        } else if (!fullName.equals(other.fullName))
            return false;
        if (shortNames == null) {
            if (other.shortNames != null)
                return false;
        } else if (!shortNames.equals(other.shortNames))
            return false;
        return true;
    }

}
