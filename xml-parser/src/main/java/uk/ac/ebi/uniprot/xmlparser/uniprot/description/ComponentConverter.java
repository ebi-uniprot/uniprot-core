package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType.Component;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class ComponentConverter implements Converter<Component, ProteinSection> {
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
		return ProteinDescriptionFactory.INSTANCE.createProteinNameSection(
				recNameConverter.fromXml(xmlObj.getRecommendedName()),
				xmlObj.getAlternativeName().stream()
				.map(altNameConverter::fromXml).collect(Collectors.toList())
				);

	}

	@Override
	public Component toXml(ProteinSection uniObj) {
		Component component = xmlUniprotFactory.createProteinTypeComponent();
		component.setRecommendedName(recNameConverter.toXml(uniObj.getRecommendedName()));
		uniObj.getAlternativeNames().forEach(val -> component.getAlternativeName().add(altNameConverter.toXml(val)));		
		return component;

	}

}
