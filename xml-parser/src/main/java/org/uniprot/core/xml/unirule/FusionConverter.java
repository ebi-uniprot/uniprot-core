package org.uniprot.core.xml.unirule;

import java.util.Objects;

import org.uniprot.core.unirule.Fusion;
import org.uniprot.core.unirule.impl.FusionBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.FusionType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class FusionConverter implements Converter<FusionType, Fusion> {

    private final ObjectFactory objectFactory;

    public FusionConverter() {
        this(new ObjectFactory());
    }

    public FusionConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public Fusion fromXml(FusionType xmlObj) {
        Fusion fusion = null;
        if (Objects.nonNull(xmlObj)) {
            FusionBuilder builder = new FusionBuilder();
            builder.ctersSet(xmlObj.getCter()).ntersSet(xmlObj.getNter());
            fusion = builder.build();
        }

        return fusion;
    }

    @Override
    public FusionType toXml(Fusion uniObj) {
        FusionType fusionType = null;
        if (Objects.nonNull(uniObj)) {
            fusionType = this.objectFactory.createFusionType();
            fusionType.getCter().addAll(uniObj.getCters());
            fusionType.getNter().addAll(uniObj.getNters());
        }
        return fusionType;
    }
}
