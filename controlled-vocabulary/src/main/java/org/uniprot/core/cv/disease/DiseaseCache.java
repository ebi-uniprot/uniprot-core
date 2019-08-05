package org.uniprot.core.cv.disease;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.impl.DiseaseFileReader;


public enum DiseaseCache {
	INSTANCE;
	public static final String FTP_LOCATION ="ftp://ftp.uniprot.org/pub/databases/uniprot/knowledgebase/docs/humdisease.txt";
	Map<String, List<Disease> > locationDiseaseMap =new HashMap<>();

	public List<Disease> get(String file){
		String filename = file;
		if((filename ==null) || filename.isEmpty()){
			filename = FTP_LOCATION;
		}
		
		List<Disease> result = locationDiseaseMap.get(filename);
		if(result !=null)
			return result;
		
		result = buildCache(filename);
		if(result.isEmpty() && !FTP_LOCATION.equals(filename)) {
			result = locationDiseaseMap.get(FTP_LOCATION);
			if(result ==null) {
				result = buildCache(FTP_LOCATION);
				locationDiseaseMap.put(FTP_LOCATION, result);
				return result;
			}else {
				return result;
			}
		}else {
			locationDiseaseMap.put(filename, result);
			return result;
		}
		
	}
	
	private List<Disease> buildCache(String filename){
		DiseaseFileReader reader = new DiseaseFileReader();
		return reader.parse(filename);

	}
}
