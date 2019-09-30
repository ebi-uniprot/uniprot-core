package org.uniprot.core.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinSubName;

public class ProteinSubNameImpl implements ProteinSubName {

    private static final long serialVersionUID = 5591636341774951586L;
    private Name fullName;
    private List<EC> ecNumbers;

    private ProteinSubNameImpl() {
        ecNumbers = Collections.emptyList();
    }

    public ProteinSubNameImpl(Name fullName, List<EC> ecNumbers) {
        this.fullName = fullName;
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
    public List<EC> getEcNumbers() {
        return ecNumbers;
    }

    @Override
    public boolean hasFullName() {
        return this.fullName != null;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ecNumbers == null) ? 0 : ecNumbers.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ProteinSubNameImpl other = (ProteinSubNameImpl) obj;
        if (ecNumbers == null) {
            if (other.ecNumbers != null) return false;
        } else if (!ecNumbers.equals(other.ecNumbers)) return false;
        if (fullName == null) {
            if (other.fullName != null) return false;
        } else if (!fullName.equals(other.fullName)) return false;

        return true;
    }
}
