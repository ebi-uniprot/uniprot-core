package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinAltNameImpl;

import static org.uniprot.core.common.Utils.nonNullAdd;
import static org.uniprot.core.common.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

public class ProteinAltNameBuilder implements Builder<ProteinAltNameBuilder, ProteinAltName> {

    private Name fullName;
    private List<Name> shortNames = new ArrayList<>();
    private List<EC> ecNumbers = new ArrayList<>();

    public ProteinAltNameBuilder fullName(Name fullName) {
        this.fullName = fullName;
        return this;
    }

    public ProteinAltNameBuilder shortNames(List<Name> shortNames) {
        this.shortNames = nonNullList(shortNames);
        return this;
    }

    public ProteinAltNameBuilder addShortName(Name shortNames) {
        nonNullAdd(shortNames, this.shortNames);
        return this;
    }
    public ProteinAltNameBuilder ecNumbers(List<EC> ecNumbers) {
        this.ecNumbers = nonNullList(ecNumbers);
        return this;
    }

    public ProteinAltNameBuilder addEcNumber(EC ecNumbers) {
        nonNullAdd(ecNumbers, this.ecNumbers);
        return this;
    }

   
    @Override
    public ProteinAltName build() {
        return new ProteinAltNameImpl( fullName, shortNames, ecNumbers);
    }

    @Override
    public ProteinAltNameBuilder from(ProteinAltName instance) {
        this.fullName(instance.getFullName());
        this.shortNames(instance.getShortNames());
        this.ecNumbers(instance.getEcNumbers());

        return this;
    }
}