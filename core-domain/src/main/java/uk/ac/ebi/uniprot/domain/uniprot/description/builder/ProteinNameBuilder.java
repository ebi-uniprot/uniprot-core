package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinNameImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.common.Utils.nonNullAdd;


/**
 *
 * @author lgonzales
 */
public class ProteinNameBuilder implements Builder<ProteinNameBuilder, ProteinName> {

    private Name fullName;
    private List<Name> shortNames = new ArrayList<>();
    private List<EC> ecNumbers = new ArrayList<>();

    public ProteinNameBuilder fullName(Name fullName) {
        this.fullName = fullName;
        return this;
    }

    public ProteinNameBuilder shortNames(List<Name> shortNames) {
        this.shortNames = shortNames;
        return this;
    }

    public ProteinNameBuilder addShortName(Name shortNames) {
        nonNullAdd(shortNames, this.shortNames);
        return this;
    }
    public ProteinNameBuilder ecNumbers(List<EC> ecNumbers) {
        this.ecNumbers = ecNumbers;
        return this;
    }

    public ProteinNameBuilder addEcNumber(EC ecNumbers) {
        nonNullAdd(ecNumbers, this.ecNumbers);
        return this;
    }

    @Override
    public ProteinName build() {
        return new ProteinNameImpl(fullName, shortNames, ecNumbers);
    }

    @Override
    public ProteinNameBuilder from(ProteinName instance) {
        this.fullName(instance.getFullName());
        this.shortNames(instance.getShortNames());
        this.ecNumbers(instance.getEcNumbers());
        return this;
    }
}