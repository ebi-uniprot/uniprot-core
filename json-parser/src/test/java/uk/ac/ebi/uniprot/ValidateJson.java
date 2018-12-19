package uk.ac.ebi.uniprot;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.uniprot.json.parser.JsonParserConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ValidateJson {

    private static Logger logger = LoggerFactory.getLogger(ValidateJson.class);

    public static <T> void verifyJsonParser(T obj) {
        try {
            ObjectMapper mapper = JsonParserConfig.getJsonObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            logger.info(json);
            T converted =  mapper.readValue(json,(Class<T>) obj.getClass());
            assertEquals(obj, converted);
        }catch(Exception e) {
            fail(e.getMessage());
        }
    }

    public static <T> void verifySimpleJsonParser(T obj,String expectedJsonString) {
        try {
            ObjectMapper mapper = JsonParserConfig.getJsonSimpleObjectMapper();
            String json = mapper.writer().writeValueAsString(obj);
            logger.info(json);
            assertEquals(json, expectedJsonString);
        }catch(Exception e) {
            fail(e.getMessage());
        }
    }

}