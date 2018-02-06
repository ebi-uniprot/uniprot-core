package uk.ac.ebi.uniprot.domain.uniprot.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeatures;

public class UniProtFeaturesImpl implements UniProtFeatures {
	private final List<UniProtFeature<? extends Feature>> features;

	public UniProtFeaturesImpl(List<UniProtFeature<? extends Feature>> features) {
		if ((features == null) || features.isEmpty()) {
			this.features = Collections.emptyList();
		} else {
			this.features = Collections.unmodifiableList(features);
		}
	}

	@Override
	public List<UniProtFeature<? extends Feature>> getFeatues() {
		return features;
	}

	@Override
	public List<UniProtFeature<? extends Feature>> getFeaturesByType(FeatureType type) {
		List<UniProtFeature<? extends Feature>> typedFeatures = new ArrayList<>();
		for (UniProtFeature<? extends Feature> feature : features) {
			if (feature.getFeature().getType() == type) {
				typedFeatures.add(feature);
			}
		}
		return typedFeatures;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((features == null) ? 0 : features.hashCode());
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
		UniProtFeaturesImpl other = (UniProtFeaturesImpl) obj;
		if (features == null) {
			if (other.features != null)
				return false;
		} else if (!features.equals(other.features))
			return false;
		return true;
	}

}
