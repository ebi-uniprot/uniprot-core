package org.uniprot.core.uniprotkb.description.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinSubName;

public class ProteinSubNameBuilder implements Builder<ProteinSubName> {

    private Name fullName;
    private List<EC> ecNumbers = new ArrayList<>();

    public @Nonnull ProteinSubNameBuilder fullName(Name fullName) {
        this.fullName = fullName;
        return this;
    }

    public @Nonnull ProteinSubNameBuilder ecNumbersSet(List<EC> ecNumbers) {
        this.ecNumbers = modifiableList(ecNumbers);
        return this;
    }

    public @Nonnull ProteinSubNameBuilder ecNumbersAdd(EC ecNumbers) {
        addOrIgnoreNull(ecNumbers, this.ecNumbers);
        return this;
    }

    @Override
    public @Nonnull ProteinSubName build() {
        return new ProteinSubNameImpl(fullName, ecNumbers);
    }

    public static @Nonnull ProteinSubNameBuilder from(@Nonnull ProteinSubName instance) {
        return new ProteinSubNameBuilder()
                .fullName(instance.getFullName())
                .ecNumbersSet(instance.getEcNumbers());
    }
}
