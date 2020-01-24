package org.uniprot.core.uniprot.description.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinRecName;
import org.uniprot.core.uniprot.description.impl.ProteinRecNameImpl;

/** @author lgonzales */
public class ProteinRecNameBuilder implements Builder<ProteinRecName> {

    private Name fullName;
    private List<Name> shortNames = new ArrayList<>();
    private List<EC> ecNumbers = new ArrayList<>();

    public @Nonnull ProteinRecNameBuilder fullName(Name fullName) {
        this.fullName = fullName;
        return this;
    }

    public @Nonnull ProteinRecNameBuilder shortNames(List<Name> shortNames) {
        this.shortNames = modifiableList(shortNames);
        return this;
    }

    public @Nonnull ProteinRecNameBuilder addShortName(Name shortNames) {
        addOrIgnoreNull(shortNames, this.shortNames);
        return this;
    }

    public @Nonnull ProteinRecNameBuilder ecNumbers(List<EC> ecNumbers) {
        this.ecNumbers = modifiableList(ecNumbers);
        return this;
    }

    public @Nonnull ProteinRecNameBuilder addEcNumber(EC ecNumbers) {
        addOrIgnoreNull(ecNumbers, this.ecNumbers);
        return this;
    }

    @Override
    public @Nonnull ProteinRecName build() {
        return new ProteinRecNameImpl(fullName, shortNames, ecNumbers);
    }

    public static @Nonnull ProteinRecNameBuilder from(@Nonnull ProteinRecName instance) {
        return new ProteinRecNameBuilder().fullName(instance.getFullName())
        .shortNames(instance.getShortNames())
        .ecNumbers(instance.getEcNumbers());
    }
}
