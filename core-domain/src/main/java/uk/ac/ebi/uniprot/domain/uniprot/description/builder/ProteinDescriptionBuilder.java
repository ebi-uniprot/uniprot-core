package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.description.*;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.FlagImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinDescriptionImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

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
        this.alternativeNames = nonNullList(alternativeNames);
        return this;
    }

    public ProteinDescriptionBuilder addAlternativeNames(ProteinName alternativeNames) {
        nonNullAdd(alternativeNames, this.alternativeNames);
        return this;
    }

    public ProteinDescriptionBuilder flag(FlagType flag) {
        if (flag != null) {
            this.flag = new FlagImpl(flag);
        }
        return this;
    }

    public ProteinDescriptionBuilder submissionNames(List<ProteinName> submissionNames) {
        this.submissionNames = nonNullList(submissionNames);
        return this;
    }

    public ProteinDescriptionBuilder addSubmissionNames(ProteinName submissionNames) {
        nonNullAdd(submissionNames, this.submissionNames);
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
        this.cdAntigenNames = nonNullList(cdAntigenNames);
        return this;
    }

    public ProteinDescriptionBuilder addCdAntigenNames(Name cdAntigen) {
        nonNullAdd(cdAntigen, this.cdAntigenNames);
        return this;
    }

    public ProteinDescriptionBuilder innNames(List<Name> innNames) {
        this.innNames = nonNullList(innNames);
        return this;
    }

    public ProteinDescriptionBuilder addInnNames(Name innNames) {
        nonNullAdd(innNames, this.innNames);
        return this;
    }

    public ProteinDescriptionBuilder includes(List<ProteinSection> includes) {
        this.includes = nonNullList(includes);
        return this;
    }

    public ProteinDescriptionBuilder addIncludes(ProteinSection includes) {
        nonNullAdd(includes, this.includes);
        return this;
    }

    public ProteinDescriptionBuilder contains(List<ProteinSection> contains) {
        this.contains = nonNullList(contains);
        return this;
    }

    public ProteinDescriptionBuilder addContains(ProteinSection contains) {
        nonNullAdd(contains, this.contains);
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