package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecName;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinRecNameImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.common.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.common.Utils.nonNullList;


/**
 *
 * @author lgonzales
 */
public class ProteinRecNameBuilder implements Builder<ProteinRecNameBuilder, ProteinRecName> {

    private Name fullName;
    private List<Name> shortNames = new ArrayList<>();
    private List<EC> ecNumbers = new ArrayList<>();

    public ProteinRecNameBuilder fullName(Name fullName) {
        this.fullName = fullName;
        return this;
    }

    public ProteinRecNameBuilder shortNames(List<Name> shortNames) {
        this.shortNames = nonNullList(shortNames);
        return this;
    }

    public ProteinRecNameBuilder addShortName(Name shortNames) {
        nonNullAdd(shortNames, this.shortNames);
        return this;
    }
    public ProteinRecNameBuilder ecNumbers(List<EC> ecNumbers) {
        this.ecNumbers = nonNullList(ecNumbers);
        return this;
    }

    public ProteinRecNameBuilder addEcNumber(EC ecNumbers) {
        nonNullAdd(ecNumbers, this.ecNumbers);
        return this;
    }

    @Override
    public ProteinRecName build() {
        return new ProteinRecNameImpl(fullName, shortNames, ecNumbers);
    }

    @Override
    public ProteinRecNameBuilder from(ProteinRecName instance) {
        this.fullName(instance.getFullName());
        this.shortNames(instance.getShortNames());
        this.ecNumbers(instance.getEcNumbers());
        return this;
    }
}