package org.uniprot.core.json.parser;

/**
 * @author lgonzales
 * @since 08/09/2020
 */
public class JsonParserException extends RuntimeException {

    private static final long serialVersionUID = -1513667223420371158L;

    public JsonParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
