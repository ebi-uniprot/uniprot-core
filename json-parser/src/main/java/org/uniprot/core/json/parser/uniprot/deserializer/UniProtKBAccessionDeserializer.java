package org.uniprot.core.json.parser.uniprot.deserializer;

import java.io.IOException;

import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionImpl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * @author lgonzales
 * @since 10/09/2020
 */
public class UniProtKBAccessionDeserializer extends StdDeserializer<UniProtKBAccessionImpl> {

    private static final long serialVersionUID = 6569988850273471111L;

    public UniProtKBAccessionDeserializer() {
        super(UniProtKBAccessionImpl.class);
    }

    @Override
    public UniProtKBAccessionImpl deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        return (UniProtKBAccessionImpl)
                new UniProtKBAccessionBuilder(jp.getValueAsString()).build();
    }
}
