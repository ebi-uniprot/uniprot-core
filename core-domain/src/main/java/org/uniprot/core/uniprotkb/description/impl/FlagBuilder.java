package org.uniprot.core.uniprotkb.description.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.description.Flag;
import org.uniprot.core.uniprotkb.description.FlagType;

import javax.annotation.Nonnull;

/**
 * Created 29/01/19
 *
 * @author Edd
 */
public class FlagBuilder implements Builder<Flag> {
    private FlagType flagType;

    @Override
    public @Nonnull Flag build() {
        return new FlagImpl(flagType);
    }

    public static @Nonnull FlagBuilder from(@Nonnull Flag instance) {
        return new FlagBuilder(instance.getType());
    }

    public FlagBuilder(FlagType flagType) {
        this.flagType = flagType;
    }
}
