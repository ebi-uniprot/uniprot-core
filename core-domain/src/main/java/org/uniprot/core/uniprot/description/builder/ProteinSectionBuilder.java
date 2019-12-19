package org.uniprot.core.uniprot.description.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinAltName;
import org.uniprot.core.uniprot.description.ProteinRecName;
import org.uniprot.core.uniprot.description.ProteinSection;
import org.uniprot.core.uniprot.description.impl.ProteinSectionImpl;

public class ProteinSectionBuilder implements Builder<ProteinSectionBuilder, ProteinSection> {

    private ProteinRecName recommendedName;
    private List<ProteinAltName> alternativeNames = new ArrayList<>();
    private Name allergenName;
    private Name biotechName;
    private List<Name> cdAntigenNames = new ArrayList<>();
    private List<Name> innNames = new ArrayList<>();

    public @Nonnull ProteinSectionBuilder recommendedName(ProteinRecName recommendedName) {
        this.recommendedName = recommendedName;
        return this;
    }

    public @Nonnull ProteinSectionBuilder alternativeNames(List<ProteinAltName> alternativeNames) {
        this.alternativeNames = modifiableList(alternativeNames);
        return this;
    }

    public @Nonnull ProteinSectionBuilder addAlternativeNames(ProteinAltName alternativeNames) {
        addOrIgnoreNull(alternativeNames, this.alternativeNames);
        return this;
    }

    public @Nonnull ProteinSectionBuilder allergenName(Name allergenName) {
        this.allergenName = allergenName;
        return this;
    }

    public @Nonnull ProteinSectionBuilder biotechName(Name biotechName) {
        this.biotechName = biotechName;
        return this;
    }

    public @Nonnull ProteinSectionBuilder cdAntigenNames(List<Name> cdAntigenNames) {
        this.cdAntigenNames = modifiableList(cdAntigenNames);
        return this;
    }

    public @Nonnull ProteinSectionBuilder addCdAntigenNames(Name cdAntigen) {
        addOrIgnoreNull(cdAntigen, this.cdAntigenNames);
        return this;
    }

    public @Nonnull ProteinSectionBuilder innNames(List<Name> innNames) {
        this.innNames = modifiableList(innNames);
        return this;
    }

    public @Nonnull ProteinSectionBuilder addInnNames(Name innNames) {
        addOrIgnoreNull(innNames, this.innNames);
        return this;
    }

    @Override
    public @Nonnull ProteinSection build() {
        return new ProteinSectionImpl(
                recommendedName,
                alternativeNames,
                allergenName,
                biotechName,
                cdAntigenNames,
                innNames);
    }

    @Override
    public @Nonnull ProteinSectionBuilder from(@Nonnull ProteinSection instance) {
        this.allergenName(instance.getAllergenName());
        this.recommendedName(instance.getRecommendedName());
        this.alternativeNames(instance.getAlternativeNames());
        this.biotechName(instance.getBiotechName());
        this.cdAntigenNames(instance.getCdAntigenNames());
        this.innNames(instance.getInnNames());
        return this;
    }
}
