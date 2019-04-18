package uk.ac.ebi.uniprot.domain.proteome.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeId;
import uk.ac.ebi.uniprot.domain.proteome.RedundantProteome;
import uk.ac.ebi.uniprot.domain.proteome.impl.RedundantProteomeImpl;

public class RedundantProteomeBuilder implements Builder<RedundantProteomeBuilder, RedundantProteome> {
	private ProteomeId id;
	private Float similarity;
	
	public static RedundantProteomeBuilder newInstance() {
		return new RedundantProteomeBuilder();
	}
	
	public RedundantProteomeBuilder proteomeId(ProteomeId id) {
		this.id = id;
		return this;
	}
	public RedundantProteomeBuilder proteomeId(String id) {
		this.id = new ProteomeIdBuilder(id).build();
		return this;
	}
	
	public RedundantProteomeBuilder similarity(Float similarity) {
		this.similarity = similarity;
		return this;
	}
	
	@Override
	public RedundantProteome build() {
		return new RedundantProteomeImpl(id, similarity);
	}

	@Override
	public RedundantProteomeBuilder from(RedundantProteome instance) {
		this.id = instance.getId();
		this.similarity = instance.getSimilarity();
		return this;
	}

}
