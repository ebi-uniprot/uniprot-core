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
		this.alternativeNames = nonNullList(alternativeNames);
		return this;
	}

	public ProteinDescriptionBuilder addAlternativeNames(ProteinAltName alternativeNames) {
		nonNullAdd(alternativeNames, this.alternativeNames);
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
		return new ProteinDescriptionImpl(recommendedName, alternativeNames, allergenName, biotechName, cdAntigenNames,
				innNames, submissionNames, flag, includes, contains);
	}

	@Override
	public ProteinDescriptionBuilder from(ProteinDescription instance) {
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