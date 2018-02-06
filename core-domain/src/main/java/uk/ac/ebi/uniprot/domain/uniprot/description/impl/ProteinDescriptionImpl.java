package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAlternativeName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinNameSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecommendedName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubmissionName;

public class ProteinDescriptionImpl implements ProteinDescription {
	private final ProteinRecommendedName recommendedName;
	private final List<ProteinSubmissionName> submissionNames;
	private final ProteinAlternativeName alternativeName;
	private final Flag flag;
	private final List<ProteinNameSection> includes;
	private final List<ProteinNameSection> contains;
	
	public ProteinDescriptionImpl(ProteinRecommendedName recommendedName,
			List<ProteinSubmissionName> submissionNames, 
			ProteinAlternativeName alternativeName) {
		this(recommendedName, submissionNames, alternativeName, null, null, null);
	}
	
	public ProteinDescriptionImpl(ProteinRecommendedName recommendedName,
			List<ProteinSubmissionName> submissionNames,
			ProteinAlternativeName alternativeName,
			Flag flag) {
		this(recommendedName, submissionNames, alternativeName, flag, null, null);
	}
	public ProteinDescriptionImpl(ProteinRecommendedName recommendedName,
			List<ProteinSubmissionName> submissionNames,
			ProteinAlternativeName alternativeName,
			Flag flag,
			 List<ProteinNameSection> includes,
			 List<ProteinNameSection> contains) {
		this.recommendedName = recommendedName;
		if((submissionNames ==null) || (submissionNames.isEmpty())) {
			this.submissionNames = Collections.emptyList();
		}else {
			this.submissionNames = Collections.unmodifiableList(submissionNames);
		}
		this.alternativeName = alternativeName;
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
	public ProteinAlternativeName getAlternativeName() {
		return alternativeName;
	}

	
	@Override
	public List<ProteinSubmissionName> getSubmissionNames() {
		return submissionNames;
	}
	@Override
	public Flag getFlag() {
		return flag;
	}
	
	@Override
	public List<ProteinNameSection> getIncludes() {
		return includes;
	}

	@Override
	public List<ProteinNameSection> getContains() {
		return contains;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alternativeName == null) ? 0 : alternativeName.hashCode());
		result = prime * result + ((contains == null) ? 0 : contains.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((includes == null) ? 0 : includes.hashCode());
		result = prime * result + ((recommendedName == null) ? 0 : recommendedName.hashCode());
		result = prime * result + ((submissionNames == null) ? 0 : submissionNames.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProteinDescriptionImpl other = (ProteinDescriptionImpl) obj;
		if (alternativeName == null) {
			if (other.alternativeName != null)
				return false;
		} else if (!alternativeName.equals(other.alternativeName))
			return false;
		if (contains == null) {
			if (other.contains != null)
				return false;
		} else if (!contains.equals(other.contains))
			return false;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (includes == null) {
			if (other.includes != null)
				return false;
		} else if (!includes.equals(other.includes))
			return false;
		if (recommendedName == null) {
			if (other.recommendedName != null)
				return false;
		} else if (!recommendedName.equals(other.recommendedName))
			return false;
		if (submissionNames == null) {
			if (other.submissionNames != null)
				return false;
		} else if (!submissionNames.equals(other.submissionNames))
			return false;
		return true;
	}

}
