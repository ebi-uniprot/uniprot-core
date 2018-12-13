package uk.ac.ebi.uniprot.cv.keyword.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.cv.keyword.KeywordDetail;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;
import uk.ac.ebi.uniprot.cv.keyword.KeywordCache;
import uk.ac.ebi.uniprot.cv.keyword.KeywordService;

public class KeywordServiceImpl implements KeywordService {
	private List<KeywordDetail> keywords ;
	private Map<String, Keyword> keywordIdMap;
	public KeywordServiceImpl(String filename) {
		keywords = KeywordCache.INSTANCE.get(filename);
		keywordIdMap =keywords.stream().map(KeywordDetail::getKeyword).collect(Collectors.toMap(Keyword::getId, Function.identity()));
	}
	public KeywordServiceImpl() {
		this(KeywordCache.FTP_LOCATION);
	}
	
	@Override
	public Keyword getById(String id) {
		return keywordIdMap.get(id);
	}

	@Override
	public List<KeywordDetail> getAll() {
		return keywords;
	}

}
