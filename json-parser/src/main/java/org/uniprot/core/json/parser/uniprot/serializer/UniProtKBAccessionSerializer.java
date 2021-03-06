package org.uniprot.core.json.parser.uniprot.serializer;

import java.io.IOException;

import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/** @author lgonzales */
public class UniProtKBAccessionSerializer extends StdSerializer<UniProtKBAccessionImpl> {

    public UniProtKBAccessionSerializer() {
        super(UniProtKBAccessionImpl.class);
    }

    @Override
    public void serialize(
            UniProtKBAccessionImpl value,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}
