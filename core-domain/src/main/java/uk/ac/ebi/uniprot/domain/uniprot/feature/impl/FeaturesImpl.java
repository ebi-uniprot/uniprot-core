package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Features;

public class FeaturesImpl implements Features {
	private final List<Feature> features;
	public FeaturesImpl(List<Feature> features){
		if((features ==null) || features.isEmpty()) {
			this.features = Collections.emptyList();
		}else {
			this.features = Collections.unmodifiableList(features);
		}
	}
	@Override
	public List<Feature> getFeatures() {
		return features;
	}

	@Override
	public List<Feature> getFeaturesByType(FeatureType type) {
		return features.stream().filter(feature -> feature.getType() ==type)
		.collect(Collectors.toList());
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
		FeaturesImpl other = (FeaturesImpl) obj;
		if (features == null) {
			if (other.features != null)
				return false;
		} else if (!features.equals(other.features))
			return false;
		return true;
	}

}
