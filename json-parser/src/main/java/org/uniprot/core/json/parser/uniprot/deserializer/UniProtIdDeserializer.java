package org.uniprot.core.json.parser.uniprot.deserializer;

import java.io.IOException;

import org.uniprot.core.uniprotkb.impl.UniProtKBIdBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdImpl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * @author lgonzales
 * @since 10/09/2020
 */
public class UniProtIdDeserializer extends StdDeserializer<UniProtKBIdImpl> {

    private static final long serialVersionUID = -6072736161497887686L;

    public UniProtIdDeserializer() {
        super(UniProtKBIdImpl.class);
    }

    @Override
    public UniProtKBIdImpl deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        return (UniProtKBIdImpl) new UniProtKBIdBuilder(jp.getValueAsString()).build();
    }
}
