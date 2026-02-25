package org.uniprot.core.proteome.impl;

import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.RelatedProteome;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;

import java.util.Objects;

public class RelatedProteomeImpl implements RelatedProteome {

    private static final long serialVersionUID = 5186813241049073388L;
    private ProteomeId proteomeId;
    private Float similarity;
    private Taxonomy taxonomy;

    RelatedProteomeImpl(){}

    public RelatedProteomeImpl(ProteomeId proteomeId, Float similarity, Taxonomy taxonomy) {
        this.proteomeId = proteomeId;
        this.similarity = similarity;
        this.taxonomy = taxonomy;
    }

    @Override
    public ProteomeId getId() {
        return this.proteomeId;
    }

    @Override
    public Float getSimilarity() {
        return this.similarity;
    }

    @Override
    public Taxonomy getTaxId() {
        return this.taxonomy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RelatedProteomeImpl that = (RelatedProteomeImpl) o;
        return Objects.equals(this.proteomeId, that.proteomeId) && Objects.equals(this.taxonomy, that.taxonomy)
                && Objects.equals(this.similarity, that.getSimilarity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(proteomeId, taxonomy, similarity);
    }
}
