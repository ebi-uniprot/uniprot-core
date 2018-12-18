package uk.ac.ebi.uniprot.parser.impl.kw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.uniprot.cv.keyword.KeywordService;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.exception.ParseKeywordException;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;

public class KwLineConverter extends EvidenceCollector implements Converter<KwLineObject, List<Keyword> > {
	private final KeywordService keywordService;
	private final boolean ignoreWrongId ;
	public KwLineConverter(KeywordService keywordService) {
		this(keywordService, true);
	}
	
	public KwLineConverter(KeywordService keywordService, boolean ignoreWrongId) {
		this.keywordService =keywordService;
		this.ignoreWrongId = ignoreWrongId;
	}
	
	
	@Override
	public List<Keyword> convert(KwLineObject f) {
		Map<Object, List<Evidence> > evidences = EvidenceHelper.convert(f.getEvidenceInfo());
		this.addAll( evidences.values());
		List<Keyword> keywords =new ArrayList<>();
		for(String kw: f.keywords){
			uk.ac.ebi.uniprot.cv.keyword.Keyword keyword = keywordService.getById(kw);
			String kwid ="";
			if(keyword !=null) {
				kwid = keyword.getAccession();
			}else if(!ignoreWrongId) {
				throw new ParseKeywordException (kw + " does not match keyword entry.");
			}
			keywords.add(UniProtFactory.INSTANCE.createKeyword(kwid, kw, evidences.get(kw)));
		}	
		return keywords;
	}

}
