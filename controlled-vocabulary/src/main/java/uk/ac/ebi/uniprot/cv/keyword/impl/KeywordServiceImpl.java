package uk.ac.ebi.uniprot.cv.keyword.impl;

import uk.ac.ebi.uniprot.cv.keyword.KeywordCache;
import uk.ac.ebi.uniprot.cv.keyword.KeywordEntry;
import uk.ac.ebi.uniprot.cv.keyword.KeywordService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KeywordServiceImpl implements KeywordService {
    private Map<String, KeywordEntry> keywordAccessionMap;
    private List<KeywordEntry> categories;
	public KeywordServiceImpl(String filename) {
        List<KeywordEntry> keywords = KeywordCache.INSTANCE.get(filename);
        keywordAccessionMap = keywords.stream().collect(Collectors.toMap(KeywordEntry::getAccession, Function.identity()));
		categories= keywords.stream().filter(val-> (val.getParents() ==null) || val.getParents().isEmpty())
		.collect(Collectors.toList());
	}
	public KeywordServiceImpl() {
		this(KeywordCache.FTP_LOCATION);
	}
	
	
	@Override
    public KeywordEntry getByAccession(String id) {
		return keywordAccessionMap.get(id);
	}

	@Override
    public Collection<KeywordEntry> getAll() {
		return keywordAccessionMap.values();
	}
	@Override
    public List<KeywordEntry> getAllCategories() {
		return categories;
	}

}
