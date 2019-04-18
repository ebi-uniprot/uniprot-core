package uk.ac.ebi.uniprot.xml.proteome;

import uk.ac.ebi.uniprot.domain.proteome.RedundantProteome;
import uk.ac.ebi.uniprot.domain.proteome.builder.ProteomeIdBuilder;
import uk.ac.ebi.uniprot.domain.proteome.builder.RedundantProteomeBuilder;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.RedundantProteomeType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;

public class RedundantProteomeConverter implements Converter<RedundantProteomeType, RedundantProteome> {
	private final ObjectFactory xmlFactory;

	public RedundantProteomeConverter() {
		this(new ObjectFactory());
	}

	public RedundantProteomeConverter(ObjectFactory xmlFactory) {
		this.xmlFactory = xmlFactory;
	}

	@Override
	public RedundantProteome fromXml(RedundantProteomeType xmlObj) {
		RedundantProteomeBuilder builder = RedundantProteomeBuilder.newInstance();
		builder.proteomeId(new ProteomeIdBuilder(xmlObj.getUpid()).build());
		builder.similarity(xmlObj.getSimilarity());
		return builder.build();
	}

	@Override
	public RedundantProteomeType toXml(RedundantProteome uniObj) {
		RedundantProteomeType rpType = xmlFactory.createRedundantProteomeType();
		rpType.setUpid(uniObj.getId().getValue());
		rpType.setSimilarity(uniObj.getSimilarity());
		return rpType;
	}

}
