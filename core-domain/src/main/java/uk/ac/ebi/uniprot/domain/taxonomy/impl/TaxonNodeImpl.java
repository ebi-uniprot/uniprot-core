package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonNode;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TaxonNodeImpl implements TaxonNode {
    private final TaxonNode parent;
    private final Taxon taxon;
    private final TaxonomyRank rank;
    @JsonCreator
    public TaxonNodeImpl(@JsonProperty("parent") TaxonNode parent,
    		@JsonProperty("taxon")Taxon taxon, 
    		@JsonProperty("rank") TaxonomyRank rank) {
        this.parent =parent;
        this.taxon = taxon;
        this.rank = rank;

    }
    @JsonIgnore
    @Override
    public List<Taxon> getTaxonLineage() {
        List<Taxon> names = new ArrayList<>();
        TaxonNode parentNode = this.getParent();
        while(parentNode !=null){
            names.add(parentNode.getTaxon());
            parentNode = parentNode.getParent();
        }
        Collections.reverse(names);
        return names;  
    }

    @Override
    public TaxonNode getParent() {
       return parent;
    }



    @Override
    public Taxon getTaxon() {
        return taxon;
    }
	@Override
	public TaxonomyRank getRank() {
		return rank;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((taxon == null) ? 0 : taxon.hashCode());
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
		TaxonNodeImpl other = (TaxonNodeImpl) obj;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (rank != other.rank)
			return false;
		if (taxon == null) {
			if (other.taxon != null)
				return false;
		} else if (!taxon.equals(other.taxon))
			return false;
		return true;
	}


}
