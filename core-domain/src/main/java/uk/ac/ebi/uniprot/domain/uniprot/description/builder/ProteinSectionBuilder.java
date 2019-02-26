package uk.ac.ebi.uniprot.domain.uniprot.description.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinSectionImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.common.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.common.Utils.nonNullList;

public class ProteinSectionBuilder implements Builder<ProteinSectionBuilder, ProteinSection> {

    private ProteinRecName recommendedName;
    private List<ProteinAltName> alternativeNames = new ArrayList<>();
    private Name allergenName;
	private Name biotechName;
	private List<Name> cdAntigenNames = new ArrayList<>();
	private List<Name> innNames = new ArrayList<>();
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
    public ProteinSectionBuilder allergenName(Name allergenName) {
		this.allergenName = allergenName;
		return this;
	}

	public ProteinSectionBuilder biotechName(Name biotechName) {
		this.biotechName = biotechName;
		return this;
	}

	public ProteinSectionBuilder cdAntigenNames(List<Name> cdAntigenNames) {
		this.cdAntigenNames = nonNullList(cdAntigenNames);
		return this;
	}

	public ProteinSectionBuilder addCdAntigenNames(Name cdAntigen) {
		nonNullAdd(cdAntigen, this.cdAntigenNames);
		return this;
	}

	public ProteinSectionBuilder innNames(List<Name> innNames) {
		this.innNames = nonNullList(innNames);
		return this;
	}

	public ProteinSectionBuilder addInnNames(Name innNames) {
		nonNullAdd(innNames, this.innNames);
		return this;
	}
    @Override
    public ProteinSection build() {
        return new ProteinSectionImpl(recommendedName, alternativeNames,
        		 allergenName,  biotechName, cdAntigenNames, innNames
        		);
    }

    @Override
    public ProteinSectionBuilder from(ProteinSection instance) {
        this.recommendedName(instance.getRecommendedName());
        this.alternativeNames(instance.getAlternativeNames());
        return this;
    }
}