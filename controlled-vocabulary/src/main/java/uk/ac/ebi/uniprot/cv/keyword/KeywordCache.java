package uk.ac.ebi.uniprot.cv.keyword;

import uk.ac.ebi.uniprot.cv.impl.KeywordFileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




public enum KeywordCache {

	INSTANCE;
	public static final String FTP_LOCATION ="ftp://ftp.uniprot.org/pub/databases/uniprot/knowledgebase/docs/keywlist.txt";
	Map<String, List<KeywordEntry>> locationKeywordMap = new HashMap<>();
	
	KeywordCache(){
		
	}

	public List<KeywordEntry> get(String file) {
		String filename = file;
		if((filename ==null) || filename.isEmpty()){
			filename = FTP_LOCATION;
		}

		List<KeywordEntry> result = locationKeywordMap.get(filename);
		if(result !=null)
			return result;
		
		result = buildCache(filename);
		if(result.isEmpty() && !FTP_LOCATION.equals(filename)) {
			result = locationKeywordMap.get(FTP_LOCATION);
			if(result ==null) {
				result = buildCache(FTP_LOCATION);
				locationKeywordMap.put(FTP_LOCATION, result);
				return result;
			}else {
				return result;
			}
		}else {
			locationKeywordMap.put(filename, result);
			return result;
		}
		
	}

	private List<KeywordEntry> buildCache(String filename) {
		
		KeywordFileReader parser = new KeywordFileReader();
		return parser.parse(filename);

	}
}
