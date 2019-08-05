package org.uniprot.core.proteome.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.RedundantProteome;
import org.uniprot.core.proteome.impl.RedundantProteomeImpl;

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
