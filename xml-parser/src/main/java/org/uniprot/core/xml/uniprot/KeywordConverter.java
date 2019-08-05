package org.uniprot.core.xml.uniprot;

import java.util.List;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.builder.KeywordBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.KeywordType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

public class KeywordConverter implements Converter<KeywordType, Keyword> {
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;

	public KeywordConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public KeywordConverter(EvidenceIndexMapper evRefMapper,
			ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Keyword fromXml(KeywordType xmlObj) {
		 String keywordValue = xmlObj.getValue();
		 List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
		 return new KeywordBuilder(xmlObj.getId(), keywordValue, KeywordCategory.UNKNOWN, evidences).build();
	}

	@Override
	public KeywordType toXml(Keyword uniObj) {
		KeywordType xmlKeyword = xmlUniprotFactory.createKeywordType();
		String value = uniObj.getValue();
		xmlKeyword.setValue(value);
		xmlKeyword.setId(uniObj.getId());

		if (!uniObj.getEvidences().isEmpty()) {
			List<Integer> ev = evRefMapper.writeEvidences(uniObj.getEvidences());
			if (!ev.isEmpty())
				xmlKeyword.getEvidence().addAll(ev);
		}
		return xmlKeyword;
	}

}
