package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubName;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinSubNameImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.common.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.common.Utils.nonNullList;

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