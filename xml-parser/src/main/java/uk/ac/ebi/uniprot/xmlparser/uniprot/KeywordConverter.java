package uk.ac.ebi.uniprot.xmlparser.uniprot;

import java.util.List;

import uk.ac.ebi.uniprot.cv.keyword.KeywordService;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.KeywordType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class KeywordConverter implements Converter<KeywordType, Keyword> {
	private final EvidenceReferenceMapper evRefMapper;
	private final KeywordService keywordService;
	private final ObjectFactory xmlUniprotFactory;

	public KeywordConverter(EvidenceReferenceMapper evRefMapper, KeywordService keywordService) {
		this(evRefMapper, keywordService, new ObjectFactory());
	}

	public KeywordConverter(EvidenceReferenceMapper evRefMapper, KeywordService keywordService,
			ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.keywordService = keywordService;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Keyword fromXml(KeywordType xmlObj) {
		 String keywordValue = xmlObj.getValue();
		 List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
		 return UniProtFactory.INSTANCE.createKeyword(keywordValue, evidences);
	}

	@Override
	public KeywordType toXml(Keyword uniObj) {
		KeywordType xmlKeyword = xmlUniprotFactory.createKeywordType();
		String value = uniObj.getValue();
		xmlKeyword.setValue(value);
		uk.ac.ebi.uniprot.cv.keyword.KeywordDetail cvKeyword = keywordService.getById(uniObj.getValue());
		if (cvKeyword == null) {
			throw new RuntimeException("Keyword: " + uniObj.getValue() + " is not in keyword list");
		}

		xmlKeyword.setId(cvKeyword.getAccession());

		if (!uniObj.getEvidences().isEmpty()) {
			List<Integer> ev = evRefMapper.writeEvidences(uniObj.getEvidences());
			if (!ev.isEmpty())
				xmlKeyword.getEvidence().addAll(ev);
		}
		return xmlKeyword;
	}

}
