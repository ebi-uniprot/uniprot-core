package uk.ac.ebi.uniprot.xml.uniprot.citation;

import uk.ac.ebi.uniprot.xml.Converter;

public class PageConverter implements Converter<String, String>{

	@Override
	public String fromXml(String xmlObj) {
		 if(xmlObj != null && xmlObj.length() > 0 && !xmlObj.contains("-")){
	            return xmlObj;
	        }
			return null;
	}

	@Override
	public String toXml(String uniObj) {
		 if(uniObj != null && uniObj.length() > 0 && !uniObj.contains("-")) {
	            return uniObj;
	        }
		 else
			 return null;
	}

}
