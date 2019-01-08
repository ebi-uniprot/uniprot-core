package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType.RecommendedName;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class RecNameConverter implements Converter<RecommendedName, ProteinName> {
	private final NameConverter nameConverter;
	private final ECConverter ecConverter;
	private final ObjectFactory xmlUniprotFactory;
	public RecNameConverter(NameConverter nameConverter, ECConverter ecConverter) {
		this(nameConverter, ecConverter,  new ObjectFactory());
	}
	public RecNameConverter(NameConverter nameConverter, ECConverter ecConverter, ObjectFactory xmlUniprotFactory) {
		this.nameConverter = nameConverter;
		this.ecConverter = ecConverter;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}
	
	@Override
	public ProteinName fromXml(RecommendedName xmlObj) {
		return ProteinDescriptionFactory.INSTANCE.createProteinName(
				nameConverter.fromXml(xmlObj.getFullName()),
				xmlObj.getShortName().stream().map(nameConverter::fromXml)
				.collect(Collectors.toList()),
				xmlObj.getEcNumber().stream().map(ecConverter::fromXml)
				.collect(Collectors.toList())
				);
	}

	@Override
	public RecommendedName toXml(ProteinName uniObj) {
		RecommendedName recName = xmlUniprotFactory.createProteinTypeRecommendedName();
		recName.setFullName(nameConverter.toXml(uniObj.getFullName()));
		uniObj.getShortNames().forEach(val -> recName.getShortName().add(nameConverter.toXml(val)));
		uniObj.getEcNumbers().forEach(val -> recName.getEcNumber().add(ecConverter.toXml(val)));
		return recName;
	}

}
