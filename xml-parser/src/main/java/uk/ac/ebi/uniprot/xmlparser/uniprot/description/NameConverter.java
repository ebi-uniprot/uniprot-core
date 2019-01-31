package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.NameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

import java.util.List;

public class NameConverter implements Converter<EvidencedStringType, Name> {
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;
	
	public NameConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public NameConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Name fromXml(EvidencedStringType xmlObj) {
		 List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
		 return new NameBuilder().value(xmlObj.getValue()).evidences(evidences).build();
	}

	@Override
	public EvidencedStringType toXml(Name uniObj) {
		EvidencedStringType evStrType = xmlUniprotFactory.createEvidencedStringType();
		evStrType.setValue(uniObj.getValue());
		if (!uniObj.getEvidences().isEmpty()) {
			List<Integer> ev = evRefMapper.writeEvidences(uniObj.getEvidences());
			if (!ev.isEmpty())
				evStrType.getEvidence().addAll(ev);
		}
		return evStrType;
	}

}
