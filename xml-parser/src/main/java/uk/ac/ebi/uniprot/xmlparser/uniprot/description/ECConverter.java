package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

public class ECConverter implements Converter<EvidencedStringType, EC> {
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;
	
	public ECConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public ECConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}
	@Override
	public EC fromXml(EvidencedStringType xmlObj) {
		 List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
		 return ProteinDescriptionFactory.INSTANCE.createECNumber(xmlObj.getValue(), evidences);
	}

	@Override
	public EvidencedStringType toXml(EC uniObj) {
		EvidencedStringType evStrType = xmlUniprotFactory.createEvidencedStringType();
		evStrType.setValue(uniObj.getValue());
		if (!uniObj.getEvidences().isEmpty()) {
			List<Integer> ev = evRefMapper.writeEvidences(uniObj.getEvidences());
			if (!ev.isEmpty())
				evStrType.getEvidence().addAll(ev);
		}
		return evStrType;
	}
	public DbReferenceType toXmlDbReference(EC uniObj) {
		DbReferenceType xmlDbRef = xmlUniprotFactory.createDbReferenceType();

        xmlDbRef.setId(uniObj.getValue());
        xmlDbRef.setType("EC");

        if (!uniObj.getEvidences().isEmpty()) {
			List<Integer> ev = evRefMapper.writeEvidences(uniObj.getEvidences());
			if (!ev.isEmpty())
				xmlDbRef.getEvidence().addAll(ev);
		}
        return xmlDbRef;
	}

}
