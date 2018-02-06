package uk.ac.ebi.uniprot.domain.uniprot.impl;

import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

public class UniProtFeatureImpl<T extends Feature> implements UniProtFeature<T> {
	private final T feature;
	private final List<Evidence> evidences;

	public UniProtFeatureImpl(T feature, List<Evidence> evidences) {
		this.feature = feature;

		if ((evidences == null) || (evidences.isEmpty())) {
			this.evidences = Collections.emptyList();
		} else {
			this.evidences = Collections.unmodifiableList(evidences);
		}
	}

	@Override
	public List<Evidence> getEvidences() {
		return evidences;
	}

	@Override
	public T getFeature() {
		return feature;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
		result = prime * result + ((feature == null) ? 0 : feature.hashCode());
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
		UniProtFeatureImpl other = (UniProtFeatureImpl) obj;
		if (evidences == null) {
			if (other.evidences != null)
				return false;
		} else if (!evidences.equals(other.evidences))
			return false;
		if (feature == null) {
			if (other.feature != null)
				return false;
		} else if (!feature.equals(other.feature))
			return false;
		return true;
	}

}
