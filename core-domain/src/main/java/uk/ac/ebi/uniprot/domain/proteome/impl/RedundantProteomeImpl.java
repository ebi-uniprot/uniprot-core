package uk.ac.ebi.uniprot.domain.proteome.impl;

import java.util.Objects;

import uk.ac.ebi.uniprot.domain.proteome.ProteomeId;
import uk.ac.ebi.uniprot.domain.proteome.RedundantProteome;

public class RedundantProteomeImpl implements RedundantProteome {

	private static final long serialVersionUID = -5984551068808415398L;
	private ProteomeId id;
	private Double similarity;
	
	private RedundantProteomeImpl() {
		
	}
	
	public RedundantProteomeImpl(ProteomeId id, Double similarity) {
		this.id = id;
		this.similarity = similarity;
	}
	
	@Override
	public ProteomeId getId() {
		return id;
	}

	@Override
	public Double getSimilarity() {
		return similarity;
	}

	@Override
	public int hashCode() {	
		return Objects.hash(id, similarity);	
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RedundantProteomeImpl other = (RedundantProteomeImpl) obj;
		return Objects.equals(id, other.id) 
				&& Objects.equals(similarity, other.similarity);
	}

}
