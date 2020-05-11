package org.uniprot.core.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.uniprot.core.uniprotkb.impl.UniProtKBIdImpl;

import java.io.IOException;

/** @author lgonzales */
public class UniProtIdSerializer extends StdSerializer<UniProtKBIdImpl> {

    public UniProtIdSerializer() {
        super(UniProtKBIdImpl.class);
    }

    @Override
    public void serialize(
            UniProtKBIdImpl value,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}
