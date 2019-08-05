package org.uniprot.core.xml.uniprot;

import com.google.common.base.Strings;

import java.util.List;

import org.uniprot.core.uniprot.GeneEncodingType;
import org.uniprot.core.uniprot.GeneLocation;
import org.uniprot.core.uniprot.builder.GeneLocationBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.GeneLocationType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.StatusType;

public class OrganelleConverter implements Converter<GeneLocationType, GeneLocation> {
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
	public GeneLocation fromXml(GeneLocationType xmlObj) {
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
		return new GeneLocationBuilder(geneEncodingType, value, evidences).build();
	}

	@Override
	public GeneLocationType toXml(GeneLocation organelle) {
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
