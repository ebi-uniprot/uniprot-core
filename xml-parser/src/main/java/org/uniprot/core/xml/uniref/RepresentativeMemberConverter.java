package org.uniprot.core.xml.uniref;

import org.uniprot.core.Sequence;
import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.builder.RepresentativeMemberBuilder;
import org.uniprot.core.xml.jaxb.uniref.MemberType;
import org.uniprot.core.xml.jaxb.uniref.ObjectFactory;

/**
 *
 * @author jluo
 * @date: 13 Aug 2019
 *
*/

public class RepresentativeMemberConverter extends AbstractMemberConverter<RepresentativeMember> {

	public RepresentativeMemberConverter() {
		this(new ObjectFactory());
	}
	public RepresentativeMemberConverter(ObjectFactory jaxbFactory) {
		super(jaxbFactory);
	}
	@Override
	public RepresentativeMember fromXml(MemberType xmlObj) {
		RepresentativeMemberBuilder builder = new RepresentativeMemberBuilder();
		builder.sequence(convertFromXml(xmlObj.getSequence()));
		super.updateMemberFromXml(builder, xmlObj);
		return builder.build();
		
	}

	private Sequence convertFromXml(MemberType.Sequence sequence) {
		String seqString = sequence.getValue();
		StringBuilder realSequence = new StringBuilder();
		for (int iii = 0; iii < seqString.length(); iii++) {
			char c = seqString.charAt(iii);
			if (c == '\t' || c == '\n' || c == ' ') {
				continue;
			}
			realSequence.append(c);
		}
		return new SequenceBuilder(realSequence.toString()).build();
	}
	
	@Override
	public MemberType toXml(RepresentativeMember uniObj) {
		MemberType xmlObj = this.jaxbFactory.createMemberType();
		xmlObj.setSequence(convertToXmlBind(uniObj.getSequence()));
		super.updateMemberToXml(xmlObj, uniObj);
		return xmlObj;
	}

	private MemberType.Sequence convertToXmlBind(Sequence sequence) {
		MemberType.Sequence result = jaxbFactory.createMemberTypeSequence();
		String rawSequence = sequence.getValue();
		StringBuilder sequenceBuffer = new StringBuilder();
		for (int iii = 0; iii < rawSequence.length(); iii++) {
			if (iii % 60 == 0) {
				sequenceBuffer.append("\n                ");
			}
			sequenceBuffer.append(rawSequence.charAt(iii));
		}
		sequenceBuffer.append("\n            ");
		result.setValue(sequenceBuffer.toString());
		result.setChecksum(sequence.getCrc64());
		result.setLength(sequence.getLength());
		return result;
	}
}

