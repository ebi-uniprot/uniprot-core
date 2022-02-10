package org.uniprot.core.xml.uniprot;

import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.impl.LigandPartBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.LigandPartType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

/**
 *
 * @author jluo
 * @date: 9 Feb 2022
 *
*/

public class FeatureLigandPartConverter implements Converter<LigandPartType, LigandPart> {
	private final ObjectFactory xmlUniprotFactory;

	public FeatureLigandPartConverter() {
		this(new ObjectFactory());
	}

	public FeatureLigandPartConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public LigandPart fromXml(LigandPartType xmlObj) {
		LigandPartBuilder builder = new LigandPartBuilder();
		builder.name(xmlObj.getName());
		if (Utils.notNull(xmlObj.getDbReference())) {
			builder.id(xmlObj.getDbReference().getType() + ":" + xmlObj.getDbReference().getId());
		}
		if (Utils.notNullNotEmpty(xmlObj.getLabel())) {
			builder.label(xmlObj.getLabel());
		}
		if (Utils.notNullNotEmpty(xmlObj.getNote())) {
			builder.note(xmlObj.getNote());
		}
		return builder.build();
	}

	@Override
	public LigandPartType toXml(LigandPart uniObj) {
		LigandPartType ligandPartType = xmlUniprotFactory.createLigandPartType();
		ligandPartType.setName(uniObj.getName());
		if (Utils.notNull(uniObj.getId())) {
			ligandPartType.setDbReference(convertDbReferenceType(uniObj.getId()));
		}

		if (Utils.notNullNotEmpty(uniObj.getLabel())) {
			ligandPartType.setLabel(uniObj.getLabel());
		}

		if (Utils.notNullNotEmpty(uniObj.getNote())) {
			ligandPartType.setNote(uniObj.getNote());
		}
		return ligandPartType;
	}

	private DbReferenceType convertDbReferenceType(String val) {
		int index = val.indexOf(':');
		String type = val.substring(0, index);
		String id = val.substring(index + 1);
		DbReferenceType dbRef = xmlUniprotFactory.createDbReferenceType();
		dbRef.setType(type);
		dbRef.setId(id);
		return dbRef;
	}

}

