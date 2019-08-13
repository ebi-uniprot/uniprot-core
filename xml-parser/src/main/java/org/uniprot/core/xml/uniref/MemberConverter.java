package org.uniprot.core.xml.uniref;

import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.builder.UniRefMemberBuilder;
import org.uniprot.core.xml.jaxb.uniref.MemberType;
import org.uniprot.core.xml.jaxb.uniref.ObjectFactory;

/**
 *
 * @author jluo
 * @date: 13 Aug 2019
 *
*/

public class MemberConverter extends AbstractMemberConverter<UniRefMember> {

	public MemberConverter() {
		this(new ObjectFactory());
	}
	public MemberConverter(ObjectFactory jaxbFactory) {
		super(jaxbFactory);
	}
	@Override
	public UniRefMember fromXml(MemberType xmlObj) {
		UniRefMemberBuilder builder = new UniRefMemberBuilder();
		super.updateMemberFromXml(builder, xmlObj);
		return builder.build();
	}

	@Override
	public MemberType toXml(UniRefMember uniObj) {
		MemberType xmlObj = this.jaxbFactory.createMemberType();
		super.updateMemberToXml(xmlObj, uniObj);
		return xmlObj;
	}

}

