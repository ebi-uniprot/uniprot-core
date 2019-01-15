package uk.ac.ebi.uniprot.json.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author lgonzales
 */
public interface JsonConfig {

    ObjectMapper getPrettyObjectMapper();

    ObjectMapper getObjectMapper();
}
