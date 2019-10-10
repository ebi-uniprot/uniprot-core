package org.uniprot.core.uniprot.description.impl;

import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinRecName;

import java.util.Collections;
import java.util.List;

import static org.uniprot.core.util.Utils.notNullOrEmpty;
import static org.uniprot.core.util.Utils.unmodifiableList;

public class ProteinRecNameImpl implements ProteinRecName {

    private static final long serialVersionUID = 7043082383411142667L;
    private Name fullName;
    private List<Name> shortNames;
    private List<EC> ecNumbers;

    // no arg constructor for JSON deserialization
    ProteinRecNameImpl() {
        shortNames = Collections.emptyList();
        ecNumbers = Collections.emptyList();
    }

    public ProteinRecNameImpl(Name fullName, List<Name> shortNames, List<EC> ecNumbers) {
        this.fullName = fullName;
        this.shortNames = unmodifiableList(shortNames);
        this.ecNumbers = unmodifiableList(ecNumbers);
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
    public boolean hasFullName() {
        return this.fullName != null;
    }

    @Override
    public boolean hasShortNames() {
        return notNullOrEmpty(shortNames);
    }

    @Override
    public boolean hasEcNumbers() {
        return notNullOrEmpty(ecNumbers);
    }

    @Override
    public boolean isValid() {
        return (getFullName() != null)
                && ((getFullName().getValue() != null) && !getFullName().getValue().isEmpty());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ecNumbers.hashCode();
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + shortNames.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ProteinRecNameImpl other = (ProteinRecNameImpl) obj;
        if (!ecNumbers.equals(other.ecNumbers)) return false;
        if (fullName == null) {
            if (other.fullName != null) return false;
        } else if (!fullName.equals(other.fullName)) return false;
        return shortNames.equals(other.shortNames);
    }
}
