package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import static uk.ac.ebi.uniprot.common.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.common.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinAltNameImpl;

public class ProteinAltNameBuilder implements Builder<ProteinAltNameBuilder, ProteinAltName> {

    private Name fullName;
    private List<Name> shortNames = new ArrayList<>();
    private List<EC> ecNumbers = new ArrayList<>();
    private Name allergenName;
	private Name biotechName; 
	private List<Name> cdAntigenNames = new ArrayList<>();
	private List<Name> innNames = new ArrayList<>();

    public ProteinAltNameBuilder fullName(Name fullName) {
        this.fullName = fullName;
        return this;
    }

    public ProteinAltNameBuilder shortNames(List<Name> shortNames) {
        this.shortNames = shortNames;
        return this;
    }

    public ProteinAltNameBuilder addShortName(Name shortNames) {
        nonNullAdd(shortNames, this.shortNames);
        return this;
    }
    public ProteinAltNameBuilder ecNumbers(List<EC> ecNumbers) {
        this.ecNumbers = ecNumbers;
        return this;
    }

    public ProteinAltNameBuilder addEcNumber(EC ecNumbers) {
        nonNullAdd(ecNumbers, this.ecNumbers);
        return this;
    }
    
    public ProteinAltNameBuilder allergenName(Name allergenName) {
        this.allergenName = allergenName;
        return this;
    }

    public ProteinAltNameBuilder biotechName(Name biotechName) {
        this.biotechName = biotechName;
        return this;
    }

    public ProteinAltNameBuilder cdAntigenNames(List<Name> cdAntigenNames) {
        this.cdAntigenNames = nonNullList(cdAntigenNames);
        return this;
    }

    public ProteinAltNameBuilder addCdAntigenNames(Name cdAntigen) {
        nonNullAdd(cdAntigen, this.cdAntigenNames);
        return this;
    }

    public ProteinAltNameBuilder innNames(List<Name> innNames) {
        this.innNames = nonNullList(innNames);
        return this;
    }

    public ProteinAltNameBuilder addInnNames(Name innNames) {
        nonNullAdd(innNames, this.innNames);
        return this;
    }

    @Override
    public ProteinAltName build() {
        return new ProteinAltNameImpl( fullName, shortNames, ecNumbers, allergenName,
    			 biotechName,  cdAntigenNames,  innNames);
    }

    @Override
    public ProteinAltNameBuilder from(ProteinAltName instance) {
        this.fullName(instance.getFullName());
        this.shortNames(instance.getShortNames());
        this.ecNumbers(instance.getEcNumbers());
        this.allergenName(instance.getAllergenName());
        this.biotechName(instance.getBiotechName());
        this.cdAntigenNames(instance.getCdAntigenNames());
        this.innNames(instance.getInnNames());
        return this;
    }
}