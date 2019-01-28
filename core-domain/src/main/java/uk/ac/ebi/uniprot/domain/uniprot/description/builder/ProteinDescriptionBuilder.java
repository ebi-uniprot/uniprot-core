package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.description.*;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.FlagImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinDescriptionImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

/**
 * @author lgonzales
 */
public class ProteinDescriptionBuilder implements Builder2<ProteinDescriptionBuilder, ProteinDescription> {

    private ProteinName recommendedName;
    private List<ProteinName> alternativeNames = new ArrayList<>();
    private Flag flag;
    private List<ProteinName> submissionNames = new ArrayList<>();
    private Name allergenName;
    private Name biotechName;
    private List<Name> cdAntigenNames = new ArrayList<>();
    private List<Name> innNames = new ArrayList<>();
    private List<ProteinSection> includes = new ArrayList<>();
    private List<ProteinSection> contains = new ArrayList<>();

    public ProteinDescriptionBuilder recommendedName(ProteinName recommendedName) {
        this.recommendedName = recommendedName;
        return this;
    }

    public ProteinDescriptionBuilder alternativeNames(List<ProteinName> alternativeNames) {
        nonNullAddAll(alternativeNames, this.alternativeNames);
        return this;
    }

    public ProteinDescriptionBuilder addAlternativeNames(ProteinName alternativeNames) {
        this.alternativeNames.add(alternativeNames);
        return this;
    }

    public ProteinDescriptionBuilder flag(FlagType flag) {
        if (flag != null) {
            this.flag = new FlagImpl(flag);
        }
        return this;
    }

    public ProteinDescriptionBuilder submissionNames(List<ProteinName> submissionNames) {
        nonNullAddAll(submissionNames, this.submissionNames);
        return this;
    }

    public ProteinDescriptionBuilder addSubmissionNames(ProteinName submissionNames) {
        this.submissionNames.add(submissionNames);
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
        nonNullAddAll(cdAntigenNames, this.cdAntigenNames);
        return this;
    }

    public ProteinDescriptionBuilder addCdAntigenNames(Name cdAntigen) {
        this.cdAntigenNames.add(cdAntigen);
        return this;
    }

    public ProteinDescriptionBuilder innNames(List<Name> innNames) {
        nonNullAddAll(innNames, this.innNames);
        return this;
    }

    public ProteinDescriptionBuilder addInnNames(Name innNames) {
        this.innNames.add(innNames);
        return this;
    }

    public ProteinDescriptionBuilder includes(List<ProteinSection> includes) {
        nonNullAddAll(includes, this.includes);
        return this;
    }

    public ProteinDescriptionBuilder addIncludes(ProteinSection includes) {
        this.includes.add(includes);
        return this;
    }

    public ProteinDescriptionBuilder contains(List<ProteinSection> contains) {
        nonNullAddAll(contains, this.contains);
        return this;
    }

    public ProteinDescriptionBuilder addContains(ProteinSection contains) {
        this.contains.add(contains);
        return this;
    }

    @Override
    public ProteinDescription build() {
        return new ProteinDescriptionImpl(recommendedName, alternativeNames,
                                          submissionNames, flag, allergenName, biotechName, cdAntigenNames,
                                          innNames, includes, contains);
    }

    @Override
    public ProteinDescriptionBuilder from(ProteinDescription instance) {
        this.allergenName(instance.getAllergenName());
        this.alternativeNames(instance.getAlternativeNames());
        this.biotechName(instance.getBiotechName());
        this.cdAntigenNames(instance.getCdAntigenNames());
        this.contains(instance.getContains());
        this.recommendedName(instance.getRecommendedName());
        this.submissionNames(instance.getSubmissionNames());
        this.flag = instance.getFlag();
        this.innNames(instance.getInnNames());
        this.includes(instance.getIncludes());
        return this;
    }
}