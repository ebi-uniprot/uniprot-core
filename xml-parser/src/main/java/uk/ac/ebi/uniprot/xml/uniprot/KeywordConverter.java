package uk.ac.ebi.uniprot.xml.uniprot;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.KeywordType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;

import java.util.List;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.builder.KeywordBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

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
