package uk.ac.ebi.uniprot.domain.util;

import java.util.Collections;
import java.util.List;

public class Utils {
	public static String resetNull(String value) {
		if(value ==null)
			return "";
		else
			return value;
	}
	
	public static <T> List<T> unmodifierList(List<T> value){
	    	if ((value == null) || value.isEmpty()) {
	           return  Collections.emptyList();
	        } else {
	            return  Collections.unmodifiableList(value);
	        }
	    }
}
