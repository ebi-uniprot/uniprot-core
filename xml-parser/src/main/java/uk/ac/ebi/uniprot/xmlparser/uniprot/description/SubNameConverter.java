package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import java.util.Collections;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType.SubmittedName;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class SubNameConverter implements Converter<SubmittedName, ProteinName> {
	private final NameConverter nameConverter;
	private final ECConverter ecConverter;
	private final ObjectFactory xmlUniprotFactory;
	public SubNameConverter(NameConverter nameConverter, ECConverter ecConverter) {
		this(nameConverter, ecConverter,  new ObjectFactory());
	}
	public SubNameConverter(NameConverter nameConverter, ECConverter ecConverter,
			ObjectFactory xmlUniprotFactory) {
		this.nameConverter = nameConverter;
		this.ecConverter = ecConverter;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}
	
	@Override
	public ProteinName fromXml(SubmittedName xmlObj) {
		return ProteinDescriptionFactory.INSTANCE.createProteinName(
				nameConverter.fromXml(xmlObj.getFullName()),
				Collections.emptyList(),
				xmlObj.getEcNumber().stream().map(ecConverter::fromXml)
				.collect(Collectors.toList())
				);
	}

	@Override
	public SubmittedName toXml(ProteinName uniObj) {
		SubmittedName subName = xmlUniprotFactory.createProteinTypeSubmittedName();
		subName.setFullName(nameConverter.toXml(uniObj.getFullName()));
		uniObj.getEcNumbers().forEach(val -> subName.getEcNumber().add(ecConverter.toXml(val)));
		return subName;
	}

}
