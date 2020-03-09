package org.uniprot.core.uniprot.description.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinRecName;

/** @author lgonzales */
public class ProteinRecNameBuilder implements Builder<ProteinRecName> {

    private Name fullName;
    private List<Name> shortNames = new ArrayList<>();
    private List<EC> ecNumbers = new ArrayList<>();

    public @Nonnull ProteinRecNameBuilder fullName(Name fullName) {
        this.fullName = fullName;
        return this;
    }

    public @Nonnull ProteinRecNameBuilder shortNamesSet(List<Name> shortNames) {
        this.shortNames = modifiableList(shortNames);
        return this;
    }

    public @Nonnull ProteinRecNameBuilder shortNamesAdd(Name shortNames) {
        addOrIgnoreNull(shortNames, this.shortNames);
        return this;
    }

    public @Nonnull ProteinRecNameBuilder ecNumbersSet(List<EC> ecNumbers) {
        this.ecNumbers = modifiableList(ecNumbers);
        return this;
    }

    public @Nonnull ProteinRecNameBuilder ecNumbersAdd(EC ecNumbers) {
        addOrIgnoreNull(ecNumbers, this.ecNumbers);
        return this;
    }

    @Override
    public @Nonnull ProteinRecName build() {
        return new ProteinRecNameImpl(fullName, shortNames, ecNumbers);
    }

    public static @Nonnull ProteinRecNameBuilder from(@Nonnull ProteinRecName instance) {
        return new ProteinRecNameBuilder()
                .fullName(instance.getFullName())
                .shortNamesSet(instance.getShortNames())
                .ecNumbersSet(instance.getEcNumbers());
    }
}
