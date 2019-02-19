package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinSectionImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.common.Utils.nonNullAdd;

public class ProteinSectionBuilder implements Builder<ProteinSectionBuilder, ProteinSection> {

    private ProteinRecName recommendedName;
    private List<ProteinAltName> alternativeNames = new ArrayList<>();

    public ProteinSectionBuilder recommendedName(ProteinRecName recommendedName) {
        this.recommendedName = recommendedName;
        return this;
    }

    public ProteinSectionBuilder alternativeNames(List<ProteinAltName> alternativeNames) {
        this.alternativeNames = alternativeNames;
        return this;
    }

    public ProteinSectionBuilder addAlternativeNames(ProteinAltName alternativeNames) {
        nonNullAdd(alternativeNames, this.alternativeNames);
        return this;
    }

    @Override
    public ProteinSection build() {
        return new ProteinSectionImpl(recommendedName, alternativeNames);
    }

    @Override
    public ProteinSectionBuilder from(ProteinSection instance) {
        this.recommendedName(instance.getRecommendedName());
        this.alternativeNames(instance.getAlternativeNames());
        return this;
    }
}