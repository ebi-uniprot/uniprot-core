package org.uniprot.core.xml.proteome;

import org.uniprot.core.proteome.RedundantProteome;
import org.uniprot.core.proteome.impl.ProteomeIdBuilder;
import org.uniprot.core.proteome.impl.RedundantProteomeBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.RedundantProteomeType;

public class RedundantProteomeConverter
        implements Converter<RedundantProteomeType, RedundantProteome> {
    private final ObjectFactory xmlFactory;

    public RedundantProteomeConverter() {
        this(new ObjectFactory());
    }

    public RedundantProteomeConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    @Override
    public RedundantProteome fromXml(RedundantProteomeType xmlObj) {
        RedundantProteomeBuilder builder = new RedundantProteomeBuilder();
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
