package uk.ac.ebi.uniprot.xmlparser.uniprot;

import java.util.List;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.GeneLocationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.StatusType;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class OrganelleConverter implements Converter<GeneLocationType, Organelle> {
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;

	public OrganelleConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public OrganelleConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Organelle fromXml(GeneLocationType xmlObj) {
		GeneEncodingType geneEncodingType = GeneEncodingType.typeOf(xmlObj.getType());
		String value = "";
		List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());

		if (xmlObj.getName() != null && !xmlObj.getName().isEmpty()) {
			int count = 0;
			for (StatusType name : xmlObj.getName()) {
				if (count == 1) {
					value += " (";
				}
				value += name.getValue();
				count++;
			}
			if (count > 1) {
				value += ")";
			}

		}
		return UniProtFactory.INSTANCE.createOrganelle(geneEncodingType, value, evidences);
	}

	@Override
	public GeneLocationType toXml(Organelle organelle) {
		GeneLocationType geneLocationXML = xmlUniprotFactory.createGeneLocationType();
		geneLocationXML.setType(organelle.getGeneEncodingType().getName().toLowerCase());
		if (organelle.getGeneEncodingType() == GeneEncodingType.PLASMID) {
			if (!Strings.isNullOrEmpty(organelle.getValue())) {
				String value = organelle.getValue();
				if ((value.endsWith(")") && value.indexOf(" (") != -1)) {
					int index = value.lastIndexOf(" (");
					String first = value.substring(0, index);
					String second = value.substring(index + 2, value.length() - 1);
					StatusType statusType = xmlUniprotFactory.createStatusType();
					statusType.setValue(first);
					geneLocationXML.getName().add(statusType);
					StatusType statusType2 = xmlUniprotFactory.createStatusType();
					statusType2.setValue(second);
					geneLocationXML.getName().add(statusType2);
				} else {
					StatusType statusType = xmlUniprotFactory.createStatusType();
					statusType.setValue(organelle.getValue());
					geneLocationXML.getName().add(statusType);
				}
			}
		}

		if (!organelle.getEvidences().isEmpty()) {
			List<Integer> evs = evRefMapper.writeEvidences(organelle.getEvidences());
			if (!evs.isEmpty())
				geneLocationXML.getEvidence().addAll(evs);
		}
		return geneLocationXML;

	}

}
