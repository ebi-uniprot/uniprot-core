package org.uniprot.core.uniprot.description.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinAltName;
import org.uniprot.core.uniprot.description.impl.ProteinAltNameImpl;

public class ProteinAltNameBuilder implements Builder<ProteinAltName> {

    private Name fullName;
    private List<Name> shortNames = new ArrayList<>();
    private List<EC> ecNumbers = new ArrayList<>();

    public @Nonnull ProteinAltNameBuilder fullName(Name fullName) {
        this.fullName = fullName;
        return this;
    }

    public @Nonnull ProteinAltNameBuilder shortNames(List<Name> shortNames) {
        this.shortNames = modifiableList(shortNames);
        return this;
    }

    public @Nonnull ProteinAltNameBuilder addShortName(Name shortNames) {
        addOrIgnoreNull(shortNames, this.shortNames);
        return this;
    }

    public @Nonnull ProteinAltNameBuilder ecNumbers(List<EC> ecNumbers) {
        this.ecNumbers = modifiableList(ecNumbers);
        return this;
    }

    public @Nonnull ProteinAltNameBuilder addEcNumber(EC ecNumbers) {
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
                .shortNames(instance.getShortNames())
                .ecNumbers(instance.getEcNumbers());
    }
}
