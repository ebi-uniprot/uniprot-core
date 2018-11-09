package uk.ac.ebi.uniprot.domain.common;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestHelper {
	public static void writeJson(Object obj) {
		final ObjectMapper objectMapper = new ObjectMapper();
    	try {
    		  String json = objectMapper.writerWithDefaultPrettyPrinter()
    			 				  
    				  .writeValueAsString(obj);
    		
    	        System.out.println(json);
    	        
    	   
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
