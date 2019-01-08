package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType.Domain;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class DomainConverter implements Converter<Domain, ProteinSection> {
	private final RecNameConverter recNameConverter;
	private final AltNameConverter altNameConverter;
	private final ObjectFactory xmlUniprotFactory;
	public DomainConverter(RecNameConverter recNameConverter, AltNameConverter altNameConverter) {
		this(recNameConverter, altNameConverter, new ObjectFactory());
	}
	public DomainConverter(RecNameConverter recNameConverter, AltNameConverter altNameConverter,
			ObjectFactory xmlUniprotFactory) {
		this.recNameConverter = recNameConverter;
		this.altNameConverter = altNameConverter;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}
	
	@Override
	public ProteinSection fromXml(Domain xmlObj) {
		return ProteinDescriptionFactory.INSTANCE.createProteinNameSection(
				recNameConverter.fromXml(xmlObj.getRecommendedName()),
				xmlObj.getAlternativeName().stream()
				.map(altNameConverter::fromXml).collect(Collectors.toList())
				);

	}

	@Override
	public Domain toXml(ProteinSection uniObj) {
		Domain domain = xmlUniprotFactory.createProteinTypeDomain();
		domain.setRecommendedName(recNameConverter.toXml(uniObj.getRecommendedName()));
		uniObj.getAlternativeNames().forEach(val -> domain.getAlternativeName().add(altNameConverter.toXml(val)));		
		return domain;

	}

}
