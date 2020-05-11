package org.uniprot.core.uniprotkb.description.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinAltName;

public class ProteinAltNameBuilder implements Builder<ProteinAltName> {

    private Name fullName;
    private List<Name> shortNames = new ArrayList<>();
    private List<EC> ecNumbers = new ArrayList<>();

    public @Nonnull ProteinAltNameBuilder fullName(Name fullName) {
        this.fullName = fullName;
        return this;
    }

    public @Nonnull ProteinAltNameBuilder shortNamesSet(List<Name> shortNames) {
        this.shortNames = modifiableList(shortNames);
        return this;
    }

    public @Nonnull ProteinAltNameBuilder shortNamesAdd(Name shortNames) {
        addOrIgnoreNull(shortNames, this.shortNames);
        return this;
    }

    public @Nonnull ProteinAltNameBuilder ecNumbersSet(List<EC> ecNumbers) {
        this.ecNumbers = modifiableList(ecNumbers);
        return this;
    }

    public @Nonnull ProteinAltNameBuilder ecNumbersAdd(EC ecNumbers) {
        addOrIgnoreNull(ecNumbers, this.ecNumbers);
        return this;
    }

    @Override
    public @Nonnull ProteinAltName build() {
        return new ProteinAltNameImpl(fullName, shortNames, ecNumbers);
    }

    public static @Nonnull ProteinAltNameBuilder from(@Nonnull ProteinAltName instance) {
        return new ProteinAltNameBuilder()
                .fullName(instance.getFullName())
                .shortNamesSet(instance.getShortNames())
                .ecNumbersSet(instance.getEcNumbers());
    }
}
