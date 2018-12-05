package uk.ac.ebi.uniprot.domain.taxonomy;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonNodeImpl.class, name = "taxonNodeImpl")
})
public interface TaxonNode  {
	TaxonNode getParent();
    List<Taxon> getTaxonLineage();
    Taxon getTaxon();
    TaxonomyRank getRank();
}
