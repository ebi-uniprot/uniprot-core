package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonNode;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaxonNodeImpl implements TaxonNode {
    private TaxonNode parent;
    private Taxon taxon;
    private TaxonomyRank rank;

    private TaxonNodeImpl() {

    }

    public TaxonNodeImpl(TaxonNode parent,
                         Taxon taxon,
                         TaxonomyRank rank) {
        this.parent = parent;
        this.taxon = taxon;
        this.rank = rank;
    }

    @Override
    public List<Taxon> getTaxonLineage() {
        List<Taxon> names = new ArrayList<>();
        TaxonNode parentNode = this.getParent();
        while (parentNode != null) {
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
