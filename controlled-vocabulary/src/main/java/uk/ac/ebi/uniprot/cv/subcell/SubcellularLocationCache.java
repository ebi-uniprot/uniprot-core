package uk.ac.ebi.uniprot.cv.subcell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ebi.uniprot.cv.impl.SubcellularLocationFileReader;

public enum SubcellularLocationCache {
	INSTANCE;
	public static final String FTP_LOCATION ="ftp://ftp.uniprot.org/pub/databases/uniprot/knowledgebase/docs/subcell.txt";
	Map<String, List<SubcellularLocation> > locationSubcellularLocationMap =new HashMap<>();
	SubcellularLocationCache(){
		
	}
	
	public List<SubcellularLocation> get(String file) {
		String filename = file;
		if((filename ==null) || filename.isEmpty()){
			filename = FTP_LOCATION;
		}
		
		List<SubcellularLocation> result = locationSubcellularLocationMap.get(filename);
		if(result !=null)
			return result;
		
		result = buildCache(filename);
		if(result.isEmpty() && !FTP_LOCATION.equals(filename)) {
			result = locationSubcellularLocationMap.get(FTP_LOCATION);
			if(result ==null) {
				result = buildCache(FTP_LOCATION);
				locationSubcellularLocationMap.put(FTP_LOCATION, result);
				return result;
			}else {
				return result;
			}
		}else {
			locationSubcellularLocationMap.put(filename, result);
			return result;
		}
		
	}
	
	private List<SubcellularLocation> buildCache(String filename){
		SubcellularLocationFileReader parser = new SubcellularLocationFileReader();
		return parser.parse(filename);

	}
}
