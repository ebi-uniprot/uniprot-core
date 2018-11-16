package uk.ac.ebi.uniprot.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestHelper {
	public static  <T> void verifyJson(T obj  ) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json =objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			System.out.println(json);
			T converted =  objectMapper.readValue(json,(Class<T>) obj.getClass());
			System.out.println(converted.toString());
			assertEquals(obj, converted);
			}catch(Exception e) {
	    		fail(e.getMessage());
	    	}
	}
}
