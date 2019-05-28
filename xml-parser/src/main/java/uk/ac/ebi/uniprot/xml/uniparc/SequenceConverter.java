package uk.ac.ebi.uniprot.xml.uniparc;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.builder.SequenceBuilder;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniparc.ObjectFactory;

/**
 *
 * @author jluo
 * @date: 23 May 2019
 *
*/

public class SequenceConverter implements Converter<uk.ac.ebi.uniprot.xml.jaxb.uniparc.Sequence,
uk.ac.ebi.uniprot.domain.Sequence> {
	private final ObjectFactory xmlFactory;
	public SequenceConverter() {
		this(new ObjectFactory());
	}

	public SequenceConverter(ObjectFactory xmlFactory) {
		this.xmlFactory = xmlFactory;
	
	}

	@Override
	public Sequence fromXml(uk.ac.ebi.uniprot.xml.jaxb.uniparc.Sequence xmlObj) {
		String sequence = xmlObj.getContent();
		sequence = sequence.replaceAll(" ", "");
		return new SequenceBuilder(sequence).build();
	}

	@Override
	public uk.ac.ebi.uniprot.xml.jaxb.uniparc.Sequence toXml(Sequence uniObj) {
		 uk.ac.ebi.uniprot.xml.jaxb.uniparc.Sequence xmlObj = xmlFactory.createSequence();
		 xmlObj.setContent(convertSeq(uniObj.getValue()));
		 xmlObj.setChecksum(uniObj.getCrc64());
		 xmlObj.setLength(uniObj.getLength());
		return xmlObj;
	}
	private String convertSeq(String sequence) {
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
		return sb.toString();
	}
}

