package org.uniprot.core.uniprot.description.builder;

import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinSubName;
import org.uniprot.core.uniprot.description.impl.ProteinSubNameImpl;

public class ProteinSubNameBuilder implements Builder<ProteinSubNameBuilder, ProteinSubName> {

    private Name fullName;
    private List<EC> ecNumbers = new ArrayList<>();

    public ProteinSubNameBuilder fullName(Name fullName) {
        this.fullName = fullName;
        return this;
    }

    public ProteinSubNameBuilder ecNumbers(List<EC> ecNumbers) {
        this.ecNumbers = nonNullList(ecNumbers);
        return this;
    }

    public ProteinSubNameBuilder addEcNumber(EC ecNumbers) {
        nonNullAdd(ecNumbers, this.ecNumbers);
        return this;
    }

    @Override
    public ProteinSubName build() {
        return new ProteinSubNameImpl(fullName, ecNumbers);
    }

    @Override
    public ProteinSubNameBuilder from(ProteinSubName instance) {
        this.fullName(instance.getFullName());
        this.ecNumbers(instance.getEcNumbers());
        return this;
    }
}
