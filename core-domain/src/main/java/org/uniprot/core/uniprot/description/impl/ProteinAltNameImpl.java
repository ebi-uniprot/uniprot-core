package org.uniprot.core.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinAltName;
import org.uniprot.core.util.Utils;

public class ProteinAltNameImpl implements ProteinAltName {

    private static final long serialVersionUID = -4837827085627262838L;
    private Name fullName;
    private List<Name> shortNames;
    private List<EC> ecNumbers;

    private ProteinAltNameImpl() {
        shortNames = Collections.emptyList();
        ecNumbers = Collections.emptyList();
    }

    public ProteinAltNameImpl(Name fullName, List<Name> shortNames, List<EC> ecNumbers) {
        this.fullName = fullName;
        this.shortNames = Utils.unmodifiableList(shortNames);
        this.ecNumbers = Utils.unmodifiableList(ecNumbers);
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
        return this.shortNames != null;
    }

    @Override
    public boolean hasEcNumbers() {
        return this.ecNumbers != null;
    }

    @Override
    public boolean isValid() {
        return (getFullName() != null)
                && ((getFullName().getValue() != null) && !getFullName().getValue().isEmpty());
    }

    public void setFullName(Name fullName) {
        this.fullName = fullName;
    }

    public void setShortNames(List<Name> shortNames) {
        this.shortNames = shortNames;
    }

    public void setEcNumbers(List<EC> ecNumbers) {
        this.ecNumbers = ecNumbers;
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
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ProteinAltNameImpl other = (ProteinAltNameImpl) obj;
        if (ecNumbers == null) {
            if (other.ecNumbers != null) return false;
        } else if (!ecNumbers.equals(other.ecNumbers)) return false;
        if (fullName == null) {
            if (other.fullName != null) return false;
        } else if (!fullName.equals(other.fullName)) return false;
        if (shortNames == null) {
            if (other.shortNames != null) return false;
        } else if (!shortNames.equals(other.shortNames)) return false;
        return true;
    }
}
