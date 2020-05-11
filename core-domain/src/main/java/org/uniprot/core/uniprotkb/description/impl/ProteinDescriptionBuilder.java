package org.uniprot.core.uniprotkb.description.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.description.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

/** @author lgonzales */
public class ProteinDescriptionBuilder implements Builder<ProteinDescription> {

    private ProteinRecName recommendedName;
    private List<ProteinAltName> alternativeNames = new ArrayList<>();

    private Name allergenName;
    private Name biotechName;
    private List<Name> cdAntigenNames = new ArrayList<>();
    private List<Name> innNames = new ArrayList<>();

    private List<ProteinSubName> submissionNames = new ArrayList<>();
    private Flag flag;
    private List<ProteinSection> includes = new ArrayList<>();
    private List<ProteinSection> contains = new ArrayList<>();

    public @Nonnull ProteinDescriptionBuilder recommendedName(ProteinRecName recommendedName) {
        this.recommendedName = recommendedName;
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder alternativeNamesSet(
            List<ProteinAltName> alternativeNames) {
        this.alternativeNames = modifiableList(alternativeNames);
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder alternativeNamesAdd(ProteinAltName alternativeNames) {
        addOrIgnoreNull(alternativeNames, this.alternativeNames);
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder allergenName(Name allergenName) {
        this.allergenName = allergenName;
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder biotechName(Name biotechName) {
        this.biotechName = biotechName;
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder cdAntigenNamesSet(List<Name> cdAntigenNames) {
        this.cdAntigenNames = modifiableList(cdAntigenNames);
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder cdAntigenNamesAdd(Name cdAntigen) {
        addOrIgnoreNull(cdAntigen, this.cdAntigenNames);
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder innNamesSet(List<Name> innNames) {
        this.innNames = modifiableList(innNames);
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder innNamesAdd(Name innNames) {
        addOrIgnoreNull(innNames, this.innNames);
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder flag(FlagType flag) {
        if (flag != null) {
            this.flag = new FlagImpl(flag);
        }
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder flag(Flag flag) {
        this.flag = flag;
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder submissionNamesSet(
            List<ProteinSubName> submissionNames) {
        this.submissionNames = modifiableList(submissionNames);
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder submissionNamesAdd(ProteinSubName submissionNames) {
        addOrIgnoreNull(submissionNames, this.submissionNames);
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder includesSet(List<ProteinSection> includes) {
        this.includes = modifiableList(includes);
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder includesAdd(ProteinSection includes) {
        addOrIgnoreNull(includes, this.includes);
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder containsSet(List<ProteinSection> contains) {
        this.contains = modifiableList(contains);
        return this;
    }

    public @Nonnull ProteinDescriptionBuilder containsAdd(ProteinSection contains) {
        addOrIgnoreNull(contains, this.contains);
        return this;
    }

    @Override
    public @Nonnull ProteinDescription build() {
        return new ProteinDescriptionImpl(
                recommendedName,
                alternativeNames,
                allergenName,
                biotechName,
                cdAntigenNames,
                innNames,
                submissionNames,
                flag,
                includes,
                contains);
    }

    public static @Nonnull ProteinDescriptionBuilder from(@Nonnull ProteinDescription instance) {
        return new ProteinDescriptionBuilder()
                .recommendedName(instance.getRecommendedName())
                .alternativeNamesSet(instance.getAlternativeNames())
                .allergenName(instance.getAllergenName())
                .biotechName(instance.getBiotechName())
                .cdAntigenNamesSet(instance.getCdAntigenNames())
                .innNamesSet(instance.getInnNames())
                .submissionNamesSet(instance.getSubmissionNames())
                .flag(instance.getFlag())
                .containsSet(instance.getContains())
                .includesSet(instance.getIncludes());
    }
}
