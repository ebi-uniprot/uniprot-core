package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinNameSection;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProteinDescriptionImpl implements ProteinDescription {
	private final ProteinName recommendedName;
	private final List<ProteinName> alternativeNames;
	private final List<ProteinName> submissionNames;
	
	private final Name allergenName;
	private final Name biotechName;
	private final List<Name> cdAntigenNames;
	private final List<Name> innNames;	
	private final List<ProteinNameSection> includes;  //dmain
	private final List<ProteinNameSection> contains;  //component
	
	public ProteinDescriptionImpl(ProteinName recommendedName,
			 List<ProteinName> alternativeNames
			) {
		this(recommendedName, alternativeNames, null);
	}
	
	
	public ProteinDescriptionImpl(ProteinName recommendedName,
			 List<ProteinName> alternativeNames,
			List<ProteinName> submissionNames
			) {
		this(recommendedName, alternativeNames, submissionNames,
				null,null, null, null, null, null);
	}
	
	public ProteinDescriptionImpl(ProteinName recommendedName,
			List<ProteinName> alternativeNames,
			List<ProteinName> submissionNames,
			 Name allergenName,
			Name biotechName, List<Name> cdAntigenNames, List<Name> innNames,
			List<ProteinNameSection> includes, List<ProteinNameSection> contains){
		this.recommendedName = recommendedName;
		this.alternativeNames  =copyList(alternativeNames);
		this.submissionNames  =copyList(submissionNames);

		this.allergenName = allergenName;
		this.biotechName =biotechName;
		this.cdAntigenNames  =copyList(cdAntigenNames);
		this.innNames  =copyList(innNames);
		this.includes  =copyList(includes);
		this.contains  =copyList(contains);		
	}

	 private <T> List<T> copyList(List<T> value) {
		 if((value ==null) || value.isEmpty()) {
			 return Collections.emptyList();
		 }else {
			 return new ArrayList<>(value);
		 }
	   }
	 
	 

	@Override
	public ProteinName getRecommendedName() {
		return recommendedName;
	}

	@Override
	public List<ProteinName> getSubmissionNames() {
		return submissionNames;
	}

	@Override
	public List<ProteinName> getAlternativeNames() {
		return alternativeNames;
	}

	@Override
	public Name getAllergenName() {
		return allergenName;
	}

	@Override
	public Name getBiotechName() {
		return biotechName;
	}

	@Override
	public List<Name> getCdAntigenNames() {
		return cdAntigenNames;
	}

	@Override
	public List<Name> getInnNames() {
		return innNames;
	}

	@Override
	public List<ProteinNameSection> getIncludes() {
		return includes;
	}

	@Override
	public List<ProteinNameSection> getContains() {
		return contains;
	}
	@JsonIgnore
	@Override
	public boolean isValid() {
		if (getRecommendedName() != null) {
			return getRecommendedName().isValid();
		} else {
			return getSubmissionNames().stream().anyMatch(val -> val.isValid());
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allergenName == null) ? 0 : allergenName.hashCode());
		result = prime * result + ((alternativeNames == null) ? 0 : alternativeNames.hashCode());
		result = prime * result + ((biotechName == null) ? 0 : biotechName.hashCode());
		result = prime * result + ((cdAntigenNames == null) ? 0 : cdAntigenNames.hashCode());
		result = prime * result + ((contains == null) ? 0 : contains.hashCode());
		result = prime * result + ((includes == null) ? 0 : includes.hashCode());
		result = prime * result + ((innNames == null) ? 0 : innNames.hashCode());
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
		if (allergenName == null) {
			if (other.allergenName != null)
				return false;
		} else if (!allergenName.equals(other.allergenName))
			return false;
		if (alternativeNames == null) {
			if (other.alternativeNames != null)
				return false;
		} else if (!alternativeNames.equals(other.alternativeNames))
			return false;
		if (biotechName == null) {
			if (other.biotechName != null)
				return false;
		} else if (!biotechName.equals(other.biotechName))
			return false;
		if (cdAntigenNames == null) {
			if (other.cdAntigenNames != null)
				return false;
		} else if (!cdAntigenNames.equals(other.cdAntigenNames))
			return false;
		if (contains == null) {
			if (other.contains != null)
				return false;
		} else if (!contains.equals(other.contains))
			return false;
		if (includes == null) {
			if (other.includes != null)
				return false;
		} else if (!includes.equals(other.includes))
			return false;
		if (innNames == null) {
			if (other.innNames != null)
				return false;
		} else if (!innNames.equals(other.innNames))
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
