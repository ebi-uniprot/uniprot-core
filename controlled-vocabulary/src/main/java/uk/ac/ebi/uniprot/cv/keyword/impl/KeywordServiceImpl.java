package uk.ac.ebi.uniprot.cv.keyword.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.cv.keyword.KeywordCache;
import uk.ac.ebi.uniprot.cv.keyword.KeywordDetail;
import uk.ac.ebi.uniprot.cv.keyword.KeywordService;

public class KeywordServiceImpl implements KeywordService {
	private Map<String, KeywordDetail> keywordAccessionMap;
	public KeywordServiceImpl(String filename) {
		List<KeywordDetail> keywords = KeywordCache.INSTANCE.get(filename);
		keywordAccessionMap =keywords.stream().collect(Collectors.toMap(KeywordDetail::getAccession, Function.identity()));
	}
	public KeywordServiceImpl() {
		this(KeywordCache.FTP_LOCATION);
	}
	
	
	@Override
	public KeywordDetail getByAccession(String id) {
		return keywordAccessionMap.get(id);
	}

	@Override
	public Collection<KeywordDetail> getAll() {
		return keywordAccessionMap.values();
	}

}
