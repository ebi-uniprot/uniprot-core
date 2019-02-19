package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.description.*;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.FlagImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinDescriptionImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.common.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.common.Utils.nonNullList;

/**
 * @author lgonzales
 */
public class ProteinDescriptionBuilder implements Builder<ProteinDescriptionBuilder, ProteinDescription> {

    private ProteinRecName recommendedName;
    private List<ProteinAltName> alternativeNames = new ArrayList<>();
    private Flag flag;
    private List<ProteinSubName> submissionNames = new ArrayList<>();
    private List<ProteinSection> includes = new ArrayList<>();
    private List<ProteinSection> contains = new ArrayList<>();

    public ProteinDescriptionBuilder recommendedName(ProteinRecName recommendedName) {
        this.recommendedName = recommendedName;
        return this;
    }

    public ProteinDescriptionBuilder alternativeNames(List<ProteinAltName> alternativeNames) {
        this.alternativeNames = nonNullList(alternativeNames);
        return this;
    }

    public ProteinDescriptionBuilder addAlternativeNames(ProteinAltName alternativeNames) {
        nonNullAdd(alternativeNames, this.alternativeNames);
        return this;
    }

    public ProteinDescriptionBuilder flag(FlagType flag) {
        if (flag != null) {
            this.flag = new FlagImpl(flag);
        }
        return this;
    }

    public ProteinDescriptionBuilder submissionNames(List<ProteinSubName> submissionNames) {
        this.submissionNames = nonNullList(submissionNames);
        return this;
    }

    public ProteinDescriptionBuilder addSubmissionNames(ProteinSubName submissionNames) {
        nonNullAdd(submissionNames, this.submissionNames);
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
                                          submissionNames, flag, includes, contains);
    }

    @Override
    public ProteinDescriptionBuilder from(ProteinDescription instance) {
        this.alternativeNames(instance.getAlternativeNames());
        this.contains(instance.getContains());
        this.recommendedName(instance.getRecommendedName());
        this.submissionNames(instance.getSubmissionNames());
        this.flag = instance.getFlag();
        this.includes(instance.getIncludes());
        return this;
    }
}