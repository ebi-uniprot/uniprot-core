package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinNameSection;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProteinNameSectionImpl implements ProteinNameSection {
	private final ProteinName recommendedName;
	private final List<ProteinName> alternativeNames;
	public ProteinNameSectionImpl(ProteinName recommendedName, List<ProteinName> alternativeNames) {
		
		this.recommendedName = recommendedName;
		if((alternativeNames ==null)|| alternativeNames.isEmpty()) {
			this.alternativeNames =Collections.emptyList();
		}else {
			this.alternativeNames = Collections.unmodifiableList(alternativeNames);
		}
	
	}
	@Override
	public ProteinName getRecommendedName() {
		return recommendedName;
	}
	@Override
	public List<ProteinName> getAlternativeNames() {
		return alternativeNames;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alternativeNames == null) ? 0 : alternativeNames.hashCode());
		result = prime * result + ((recommendedName == null) ? 0 : recommendedName.hashCode());
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
		ProteinNameSectionImpl other = (ProteinNameSectionImpl) obj;
		if (alternativeNames == null) {
			if (other.alternativeNames != null)
				return false;
		} else if (!alternativeNames.equals(other.alternativeNames))
			return false;
		if (recommendedName == null) {
			if (other.recommendedName != null)
				return false;
		} else if (!recommendedName.equals(other.recommendedName))
			return false;
		return true;
	}
	
}
