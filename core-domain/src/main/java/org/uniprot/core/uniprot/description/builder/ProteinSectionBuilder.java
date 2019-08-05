package org.uniprot.core.uniprot.description.builder;

import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

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
    public ProteinSectionBuilder recommendedName(ProteinRecName recommendedName) {
        this.recommendedName = recommendedName;
        return this;
    }

    public ProteinSectionBuilder alternativeNames(List<ProteinAltName> alternativeNames) {
		this.alternativeNames = nonNullList(alternativeNames);
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