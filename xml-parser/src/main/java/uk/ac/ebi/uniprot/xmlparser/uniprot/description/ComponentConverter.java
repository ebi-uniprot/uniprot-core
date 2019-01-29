package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinSectionBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType.Component;
import uk.ac.ebi.uniprot.xmlparser.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentConverter implements Converter<Component, ProteinSection>, ToXmlDbReferences<ProteinSection> {
    private final RecNameConverter recNameConverter;
    private final AltNameConverter altNameConverter;
    private final ObjectFactory xmlUniprotFactory;

    public ComponentConverter(RecNameConverter recNameConverter, AltNameConverter altNameConverter) {
        this(recNameConverter, altNameConverter, new ObjectFactory());
    }

    public ComponentConverter(RecNameConverter recNameConverter, AltNameConverter altNameConverter,
                              ObjectFactory xmlUniprotFactory) {
        this.recNameConverter = recNameConverter;
        this.altNameConverter = altNameConverter;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public ProteinSection fromXml(Component xmlObj) {
        return new ProteinSectionBuilder()
                .recommendedName(recNameConverter.fromXml(xmlObj.getRecommendedName()))
                .alternativeNames(xmlObj.getAlternativeName().stream()
                                          .map(altNameConverter::fromXml).collect(Collectors.toList()))
                .build();
    }

    @Override
    public Component toXml(ProteinSection uniObj) {
        Component component = xmlUniprotFactory.createProteinTypeComponent();
        component.setRecommendedName(recNameConverter.toXml(uniObj.getRecommendedName()));
        uniObj.getAlternativeNames().forEach(val -> component.getAlternativeName().add(altNameConverter.toXml(val)));
        return component;

    }

    @Override
    public List<DbReferenceType> toXmlDbReferences(ProteinSection t) {
        List<DbReferenceType> result = new ArrayList<>();
        result.addAll(recNameConverter.toXmlDbReferences(t.getRecommendedName()));
        t.getAlternativeNames().forEach(val -> {
            altNameConverter.toXmlDbReferences(val)
                    .forEach(dbType -> {
                        if (!result.contains(dbType)) {
                            result.add(dbType);
                        }
                    });
        });
        return result;
    }

}
