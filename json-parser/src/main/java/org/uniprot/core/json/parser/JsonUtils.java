package org.uniprot.core.json.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;

import java.io.IOException;

import org.uniprot.core.json.parser.uniprot.UniprotJsonConfig;
/**
 *
 * @author lgonzales
 */
public class JsonUtils {

	public static String getJsonString(Object obj, boolean isPretty) {
		final ObjectMapper objectMapper = UniprotJsonConfig.getInstance().getFullObjectMapper();
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
		ObjectMapper mapper = UniprotJsonConfig.getInstance().getFullObjectMapper();
		try {
			return mapper.readValue(json, clazz);
		}catch(Exception e) {
    		throw new RuntimeException(e);
    	}
	}

	public static <T> String getJsonSchema(Class<T> clazz) {
	    ObjectMapper mapper = UniprotJsonConfig.getInstance().getFullObjectMapper();
	    JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);
	    JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(clazz);
	    try {
	   		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
	    }catch(IOException e) {
	    	throw new RuntimeException (e);
	    }
	}
}
