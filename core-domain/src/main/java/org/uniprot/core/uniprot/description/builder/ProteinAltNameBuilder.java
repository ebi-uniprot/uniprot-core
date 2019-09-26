package org.uniprot.core.uniprot.description.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinAltName;
import org.uniprot.core.uniprot.description.impl.ProteinAltNameImpl;

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
        addOrIgnoreNull(shortNames, this.shortNames);
        return this;
    }
    public ProteinAltNameBuilder ecNumbers(List<EC> ecNumbers) {
        this.ecNumbers = nonNullList(ecNumbers);
        return this;
    }

    public ProteinAltNameBuilder addEcNumber(EC ecNumbers) {
        addOrIgnoreNull(ecNumbers, this.ecNumbers);
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