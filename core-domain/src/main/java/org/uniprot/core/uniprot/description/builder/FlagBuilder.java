package org.uniprot.core.uniprot.description.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.description.Flag;
import org.uniprot.core.uniprot.description.FlagType;
import org.uniprot.core.uniprot.description.impl.FlagImpl;

/**
 * Created 29/01/19
 *
 * @author Edd
 */
public class FlagBuilder implements Builder<FlagBuilder, Flag> {
    private FlagType flagType;

    @Override
    public Flag build() {
        return new FlagImpl(flagType);
    }

    @Override
    public FlagBuilder from(Flag instance) {
        this.flagType = instance.getType();
        return this;
    }

    public FlagBuilder(FlagType flagType) {
        this.flagType = flagType;
    }
}