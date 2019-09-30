package org.uniprot.core.json.parser.uniprot.serializer;

import java.io.IOException;

import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/** @author lgonzales */
public class UniProtAccessionSerializer extends StdSerializer<UniProtAccessionImpl> {

    public UniProtAccessionSerializer() {
        super(UniProtAccessionImpl.class);
    }

    @Override
    public void serialize(
            UniProtAccessionImpl value,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}
