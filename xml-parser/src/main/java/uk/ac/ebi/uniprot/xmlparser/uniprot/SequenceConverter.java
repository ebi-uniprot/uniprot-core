package uk.ac.ebi.uniprot.xmlparser.uniprot;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.builder.SequenceBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SequenceType;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class SequenceConverter implements Converter<SequenceType, Sequence> {
	private final ObjectFactory xmlUniprotFactory;

	public SequenceConverter() {
		this(new ObjectFactory());
	}

	public SequenceConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Sequence fromXml(SequenceType xmlObj) {
		String sequence = xmlObj.getValue();
		sequence = sequence.replaceAll(" ", "");
		return new SequenceBuilder(sequence).build();
	}

	@Override
	public SequenceType toXml(Sequence uniObj) {
		SequenceType xmlObj = xmlUniprotFactory.createSequenceType();
		xmlObj.setChecksum(uniObj.getCrc64());
		xmlObj.setLength(uniObj.getValue().length());
		xmlObj.setMass(uniObj.getMolWeight());
		String sequence = uniObj.getValue();
		StringBuilder sb = new StringBuilder();
		int count = 0;
		int size = 60;
		for (int i = 0; i < sequence.length(); i = i + size) {
			if (count > 0) {
				sb.append(" ");
			}
			if (sequence.length() > i + size) {

				sb.append(sequence.substring(i, i + size));
				count++;
			} else {
				sb.append(sequence.substring(i));
			}
		}

		xmlObj.setValue(sb.toString());

		return xmlObj;
	}

}
