package org.uniprot.core.uniprot.description.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.description.*;
import org.uniprot.core.uniprot.description.impl.FlagImpl;
import org.uniprot.core.uniprot.description.impl.ProteinDescriptionImpl;

/** @author lgonzales */
public class ProteinDescriptionBuilder
        implements Builder<ProteinDescriptionBuilder, ProteinDescription> {

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

    public ProteinDescriptionBuilder recommendedName(ProteinRecName recommendedName) {
        this.recommendedName = recommendedName;
        return this;
    }

    public ProteinDescriptionBuilder alternativeNames(List<ProteinAltName> alternativeNames) {
        this.alternativeNames = modifiableList(alternativeNames);
        return this;
    }

    public ProteinDescriptionBuilder addAlternativeNames(ProteinAltName alternativeNames) {
        addOrIgnoreNull(alternativeNames, this.alternativeNames);
        return this;
    }

    public ProteinDescriptionBuilder allergenName(Name allergenName) {
        this.allergenName = allergenName;
        return this;
    }

    public ProteinDescriptionBuilder biotechName(Name biotechName) {
        this.biotechName = biotechName;
        return this;
    }

    public ProteinDescriptionBuilder cdAntigenNames(List<Name> cdAntigenNames) {
        this.cdAntigenNames = modifiableList(cdAntigenNames);
        return this;
    }

    public ProteinDescriptionBuilder addCdAntigenNames(Name cdAntigen) {
        addOrIgnoreNull(cdAntigen, this.cdAntigenNames);
        return this;
    }

    public ProteinDescriptionBuilder innNames(List<Name> innNames) {
        this.innNames = modifiableList(innNames);
        return this;
    }

    public ProteinDescriptionBuilder addInnNames(Name innNames) {
        addOrIgnoreNull(innNames, this.innNames);
        return this;
    }

    public ProteinDescriptionBuilder flag(FlagType flag) {
        if (flag != null) {
            this.flag = new FlagImpl(flag);
        }
        return this;
    }

    public ProteinDescriptionBuilder submissionNames(List<ProteinSubName> submissionNames) {
        this.submissionNames = modifiableList(submissionNames);
        return this;
    }

    public ProteinDescriptionBuilder addSubmissionNames(ProteinSubName submissionNames) {
        addOrIgnoreNull(submissionNames, this.submissionNames);
        return this;
    }

    public ProteinDescriptionBuilder includes(List<ProteinSection> includes) {
        this.includes = modifiableList(includes);
        return this;
    }

    public ProteinDescriptionBuilder addIncludes(ProteinSection includes) {
        addOrIgnoreNull(includes, this.includes);
        return this;
    }

    public ProteinDescriptionBuilder contains(List<ProteinSection> contains) {
        this.contains = modifiableList(contains);
        return this;
    }

    public ProteinDescriptionBuilder addContains(ProteinSection contains) {
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

    @Override
    public @Nonnull ProteinDescriptionBuilder from(@Nonnull ProteinDescription instance) {
        this.recommendedName(instance.getRecommendedName());
        this.alternativeNames(instance.getAlternativeNames());
        this.allergenName(instance.getAllergenName());
        this.biotechName(instance.getBiotechName());
        this.cdAntigenNames(instance.getCdAntigenNames());
        this.innNames(instance.getInnNames());
        this.submissionNames(instance.getSubmissionNames());
        this.flag = instance.getFlag();
        this.contains(instance.getContains());
        this.includes(instance.getIncludes());

        return this;
    }
}
