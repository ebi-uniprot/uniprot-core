package uk.ac.ebi.uniprot.domain.uniprot.feature;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureLocationImpl.class, name = "FeatureLocationImpl")
})
public interface FeatureLocation {
	public FeatureLocationModifier getStartModifier();
	public Integer getStart();
	public boolean isStartAvailable();
	
	public Integer getEnd();
	public FeatureLocationModifier getEndModifier();
	public boolean isEndAvailable();

}

