package uk.ac.ebi.uniprot.domain.uniprot.feature;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureImpl.class, name = "FeatureImpl")
})
public interface Feature extends HasEvidences {
	 FeatureType getType();
	 FeatureLocation getLocation();
	 FeatureDescription getDescription();
	 FeatureId getFeatureId();
	 boolean hasFeatureId();
	 AlternativeSequence getAlternativeSequence();
	 boolean hasAlternativeSequence();
	 DBCrossReference<FeatureXDbType> getDbXref();
}
