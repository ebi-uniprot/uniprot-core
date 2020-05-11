package org.uniprot.core.proteome.impl;

import java.util.Objects;

import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.RedundantProteome;

public class RedundantProteomeImpl implements RedundantProteome {

    private static final long serialVersionUID = -5984551068808415398L;
    private ProteomeId id;
    private Float similarity;

    // no arg constructor for JSON deserialization
    RedundantProteomeImpl() {}

    RedundantProteomeImpl(ProteomeId id, Float similarity) {
        this.id = id;
        this.similarity = similarity;
    }

    @Override
    public ProteomeId getId() {
        return id;
    }

    @Override
    public Float getSimilarity() {
        return similarity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, similarity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RedundantProteomeImpl other = (RedundantProteomeImpl) obj;
        return Objects.equals(id, other.id) && Objects.equals(similarity, other.similarity);
    }
}
