package uk.ac.ebi.uniprot.domain.util.json;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;

public class JsonUtils {
	public static String getJsonString(Object obj, boolean isPretty) {
		final ObjectMapper objectMapper = new ObjectMapper();
    	try {
    		if(isPretty)
    		  return objectMapper.writerWithDefaultPrettyPrinter()   			 				  
    				  .writeValueAsString(obj);
    		else
    			return objectMapper.writeValueAsString(obj);
    		
    	}catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    }
	public static <T> T convertJsonToObject(String json, Class<T> clazz){
		ObjectMapper mapper = new ObjectMapper();
		try {
		return mapper.readValue(json, clazz);
		}catch(Exception e) {
    		throw new RuntimeException(e);
    	}
	}
	public static <T> String getJsonSchema(Class<T> clazz) {
	    ObjectMapper mapper = new ObjectMapper();
	    JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);
	    JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(clazz);
	    try {
	   return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
	    }catch(IOException e) {
	    	throw new RuntimeException (e);
	    }
	}
}
