package org.uniprot.core.uniprot.description.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinSubName;
import org.uniprot.core.uniprot.description.impl.ProteinSubNameImpl;

public class ProteinSubNameBuilder implements Builder<ProteinSubNameBuilder, ProteinSubName> {

    private Name fullName;
    private List<EC> ecNumbers = new ArrayList<>();

    public @Nonnull ProteinSubNameBuilder fullName(Name fullName) {
        this.fullName = fullName;
        return this;
    }

    public @Nonnull ProteinSubNameBuilder ecNumbers(List<EC> ecNumbers) {
        this.ecNumbers = modifiableList(ecNumbers);
        return this;
    }

    public @Nonnull ProteinSubNameBuilder addEcNumber(EC ecNumbers) {
        addOrIgnoreNull(ecNumbers, this.ecNumbers);
        return this;
    }

    @Override
    public @Nonnull ProteinSubName build() {
        return new ProteinSubNameImpl(fullName, ecNumbers);
    }

    @Override
    public @Nonnull ProteinSubNameBuilder from(@Nonnull ProteinSubName instance) {
        this.fullName(instance.getFullName());
        this.ecNumbers(instance.getEcNumbers());
        return this;
    }
}
