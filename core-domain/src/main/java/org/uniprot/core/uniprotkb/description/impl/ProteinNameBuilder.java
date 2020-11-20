package org.uniprot.core.uniprotkb.description.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinName;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

/**
 * Created 19/11/2020
 *
 * @author Edd
 */
public class ProteinNameBuilder implements Builder<ProteinName> {
    protected Name fullName;

    protected List<Name> shortNames = new ArrayList<>();

    protected List<EC> ecNumbers = new ArrayList<>();

    public @Nonnull ProteinNameBuilder fullName(Name fullName) {
        this.fullName = fullName;
        return this;
    }

    public @Nonnull ProteinNameBuilder shortNamesSet(List<Name> shortNames) {
        this.shortNames = modifiableList(shortNames);
        return this;
    }

    public @Nonnull ProteinNameBuilder shortNamesAdd(Name shortName) {
        addOrIgnoreNull(shortName, this.shortNames);
        return this;
    }

    public @Nonnull ProteinNameBuilder ecNumbersSet(List<EC> ecNumbers) {
        this.ecNumbers = modifiableList(ecNumbers);
        return this;
    }

    public @Nonnull ProteinNameBuilder ecNumbersAdd(EC ecNumber) {
        addOrIgnoreNull(ecNumber, this.ecNumbers);
        return this;
    }

    @Nonnull
    @Override
    public ProteinName build() {
        return new ProteinNameImpl(fullName, shortNames, ecNumbers);
    }

    public static ProteinNameBuilder from(@Nonnull ProteinName instance) {
        return new ProteinNameBuilder()
                .fullName(instance.getFullName())
                .shortNamesSet(instance.getShortNames())
                .ecNumbersSet(instance.getEcNumbers());
    }
}
