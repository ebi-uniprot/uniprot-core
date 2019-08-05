package org.uniprot.core.xml.uniprot.description;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.description.ProteinSection;
import org.uniprot.core.uniprot.description.builder.ProteinSectionBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.ProteinType.Component;

public class ComponentConverter implements Converter<Component, ProteinSection>, ToXmlDbReferences<ProteinSection> {
    private final NameConverter nameConverter;
    private final RecNameConverter recNameConverter;
    private final AltNameConverter altNameConverter;
    private final ObjectFactory xmlUniprotFactory;


    public ComponentConverter(NameConverter nameConverter,
    		RecNameConverter recNameConverter, AltNameConverter altNameConverter) {
        this(nameConverter, recNameConverter, altNameConverter, new ObjectFactory());
    }

    public ComponentConverter(NameConverter nameConverter,
    		RecNameConverter recNameConverter, AltNameConverter altNameConverter,
                              ObjectFactory xmlUniprotFactory) {
    	this.nameConverter = nameConverter;
        this.recNameConverter = recNameConverter;
        this.altNameConverter = altNameConverter;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public ProteinSection fromXml(Component xmlObj) {
    	ProteinSectionBuilder builder =
    	new ProteinSectionBuilder()
        .recommendedName(recNameConverter.fromXml(xmlObj.getRecommendedName()))
        .alternativeNames(xmlObj.getAlternativeName().stream()
                                  .map(altNameConverter::fromXml).collect(Collectors.toList()));
    	if(xmlObj.getAllergenName() !=null) {
			builder.allergenName(nameConverter.fromXml(xmlObj.getAllergenName()));
		}
		if(xmlObj.getBiotechName() !=null) {
			builder.biotechName(nameConverter.fromXml(xmlObj.getBiotechName()));
		}
		if(!xmlObj.getCdAntigenName().isEmpty()) {
			builder.cdAntigenNames(
			xmlObj.getCdAntigenName().stream()
			.map(nameConverter::fromXml)
			.collect(Collectors.toList()));
		}
		if(!xmlObj.getInnName().isEmpty()) {
			builder.innNames(
					xmlObj.getInnName().stream()
					.map(nameConverter::fromXml)
					.collect(Collectors.toList()));
		}
    	
    	return builder.build();

    }

    @Override
    public Component toXml(ProteinSection uniObj) {
        Component component = xmlUniprotFactory.createProteinTypeComponent();
        component.setRecommendedName(recNameConverter.toXml(uniObj.getRecommendedName()));
        uniObj.getAlternativeNames().forEach(val -> component.getAlternativeName().add(altNameConverter.toXml(val)));
      
        if (uniObj.getAllergenName() != null) {
        	component.setAllergenName(nameConverter.toXml(uniObj.getAllergenName()));
		}

		if (uniObj.getBiotechName() != null) {
			component.setBiotechName(nameConverter.toXml(uniObj.getBiotechName()));
		}

		uniObj.getCdAntigenNames().forEach(val -> component.getCdAntigenName().add(nameConverter.toXml(val)));

		uniObj.getInnNames().forEach(val -> component.getInnName().add(nameConverter.toXml(val)));


        
        
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
