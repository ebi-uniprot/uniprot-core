package org.uniprot.core.xml.unirule;

import java.util.List;
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
        FusionBuilder builder = new FusionBuilder();
        builder.ctersSet(xmlObj.getCter()).ntersSet(xmlObj.getNter());
        return builder.build();
    }

    @Override
    public FusionType toXml(Fusion uniObj) {

        FusionType fusionType = this.objectFactory.createFusionType();
        List<String> cters = fusionType.getCter();
        List<String> nters = fusionType.getNter();

        if (Objects.nonNull(uniObj.getCters())) {
            cters.addAll(uniObj.getCters());
        }
        if (Objects.nonNull(uniObj.getNters())) {
            nters.addAll(uniObj.getNters());
        }
        return fusionType;
    }
}
