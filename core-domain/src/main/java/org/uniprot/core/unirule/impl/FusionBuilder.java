package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.*;

import org.uniprot.core.Builder;
import org.uniprot.core.unirule.Fusion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class FusionBuilder implements Builder<Fusion> {

    private List<String> cters = new ArrayList<>();
    private List<String> nters = new ArrayList<>();

    public @Nonnull FusionBuilder ctersAdd(String cter) {
        addOrIgnoreNull(cter, this.cters);
        return this;
    }

    public @Nonnull FusionBuilder ctersSet(List<String> cters) {
        this.cters = modifiableList(cters);
        return this;
    }

    public @Nonnull FusionBuilder ntersAdd(String nter) {
        addOrIgnoreNull(nter, this.nters);
        return this;
    }

    public @Nonnull FusionBuilder ntersSet(List<String> nters) {
        this.nters = modifiableList(nters);
        return this;
    }

    @Nonnull
    @Override
    public Fusion build() {
        return new FusionImpl(cters, nters);
    }

    public static @Nonnull FusionBuilder from(@Nonnull Fusion instance) {
        nullThrowIllegalArgument(instance);
        FusionBuilder builder = new FusionBuilder();
        builder.ctersSet(instance.getCters()).ntersSet(instance.getNters());
        return builder;
    }
}
