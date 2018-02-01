package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecommendedName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubmissionName;

public class ProteinDescriptionImpl implements ProteinDescription {
	private final ProteinRecommendedName recommendedName;
	private final ProteinSubmissionName submmissionName;
	private final Flag flag;
	private final List<ProteinRecommendedName> includes;
	private final List<ProteinRecommendedName> contains;
	
	public ProteinDescriptionImpl(ProteinRecommendedName recommendedName,
			ProteinSubmissionName submmissionName) {
		this(recommendedName, submmissionName, null, null, null);
	}
	
	public ProteinDescriptionImpl(ProteinRecommendedName recommendedName,
			ProteinSubmissionName submmissionName,
			Flag flag) {
		this(recommendedName, submmissionName, flag, null, null);
	}
	public ProteinDescriptionImpl(ProteinRecommendedName recommendedName,
			ProteinSubmissionName submmissionName,
			Flag flag,
			 List<ProteinRecommendedName> includes,
			 List<ProteinRecommendedName> contains) {
		this.recommendedName = recommendedName;
		this.submmissionName = submmissionName;
		this.flag = flag;
		if((includes ==null) || (includes.isEmpty())) {
			this.includes = Collections.emptyList();
		}else {
			this.includes = Collections.unmodifiableList(includes);
		}
		if((contains ==null) || (contains.isEmpty())) {
			this.contains = Collections.emptyList();
		}else {
			this.contains = Collections.unmodifiableList(contains);
		}
		
	}
	@Override
	public ProteinRecommendedName getRecommendedName() {
		return recommendedName;
	}

	@Override
	public ProteinSubmissionName getSubmmissonName() {
		return submmissionName;
	}
	@Override
	public Flag getFlag() {
		return flag;
	}
	
	@Override
	public List<ProteinRecommendedName> getIncludes() {
		return includes;
	}

	@Override
	public List<ProteinRecommendedName> getContains() {
		return contains;
	}



}
