package uk.ac.ebi.uniprot.json.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;

import java.io.IOException;

public class JsonUtils {
	public static String getJsonString(Object obj, boolean isPretty) {
		final ObjectMapper objectMapper = JsonParserConfig.getJsonObjectMapper();
    	try {
    		if(isPretty)
    			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    		else
    			return objectMapper.writeValueAsString(obj);
    		
    	}catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    }
	public static <T> T convertJsonToObject(String json, Class<T> clazz){
		ObjectMapper mapper = JsonParserConfig.getJsonObjectMapper();
		try {
			return mapper.readValue(json, clazz);
		}catch(Exception e) {
    		throw new RuntimeException(e);
    	}
	}
	public static <T> String getJsonSchema(Class<T> clazz) {
	    ObjectMapper mapper = JsonParserConfig.getJsonObjectMapper();
	    JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);
	    JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(clazz);
	    try {
	   		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
	    }catch(IOException e) {
	    	throw new RuntimeException (e);
	    }
	}
}
