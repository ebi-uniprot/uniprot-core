package org.uniprot.core.cv.pathway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.impl.UniPathwayFileReader;

public enum UniPathwayCache {
	INSTANCE;
	public static final String FTP_LOCATION ="./src/main/resources/unipathway.txt";
	Map<String, List<UniPathway> > locationPathwayMap =new HashMap<>();

	public List<UniPathway> get(String file){
		String filename = file;
		if((filename ==null) || filename.isEmpty()){
			filename = FTP_LOCATION;
		}
		
		List<UniPathway> result = locationPathwayMap.get(filename);
		if(result !=null)
			return result;
		
		result = buildCache(filename);
		if(result.isEmpty() && !FTP_LOCATION.equals(filename)) {
			result = locationPathwayMap.get(FTP_LOCATION);
			if(result ==null) {
				result = buildCache(FTP_LOCATION);
				locationPathwayMap.put(FTP_LOCATION, result);
				return result;
			}else {
				return result;
			}
		}else {
			locationPathwayMap.put(filename, result);
			return result;
		}
		
	}
	
	private List<UniPathway> buildCache(String filename){
		UniPathwayFileReader reader = new UniPathwayFileReader();
		return reader.parse(filename);

	}
}
