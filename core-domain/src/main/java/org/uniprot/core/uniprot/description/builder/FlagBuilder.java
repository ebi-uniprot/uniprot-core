package org.uniprot.core.uniprot.description.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.description.Flag;
import org.uniprot.core.uniprot.description.FlagType;
import org.uniprot.core.uniprot.description.impl.FlagImpl;

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
