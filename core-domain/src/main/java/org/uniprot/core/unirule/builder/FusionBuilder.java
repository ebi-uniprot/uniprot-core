package org.uniprot.core.unirule.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.unirule.Fusion;
import org.uniprot.core.unirule.impl.FusionImpl;

public class FusionBuilder implements Builder<Fusion> {

    private List<String> cters;
    private List<String> nters;

    public FusionBuilder ctersAdd(String cter) {
        addOrIgnoreNull(cter, this.cters);
        return this;
    }

    public FusionBuilder ctersSet(List<String> cters) {
        this.cters = modifiableList(cters);
        return this;
    }

    public FusionBuilder ntersAdd(String nter) {
        addOrIgnoreNull(nter, this.nters);
        return this;
    }

    public FusionBuilder ntersSet(List<String> nters) {
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
