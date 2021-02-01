package org.uniprot.core.uniprotkb.description.impl;

import static org.uniprot.core.util.Utils.unmodifiableList;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinName;

public class ProteinNameImpl implements ProteinName {
    private static final long serialVersionUID = 2539291178732249294L;
    private Name fullName;
    private final List<Name> shortNames;
    private final List<EC> ecNumbers;

    // no arg constructor for JSON deserialization
    ProteinNameImpl() {
        shortNames = Collections.emptyList();
        ecNumbers = Collections.emptyList();
    }

    ProteinNameImpl(Name fullName, List<Name> shortNames, List<EC> ecNumbers) {
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
        ProteinNameImpl other = (ProteinNameImpl) obj;
        if (!ecNumbers.equals(other.ecNumbers)) return false;
        if (fullName == null) {
            if (other.fullName != null) return false;
        } else if (!fullName.equals(other.fullName)) return false;
        return shortNames.equals(other.shortNames);
    }
}
