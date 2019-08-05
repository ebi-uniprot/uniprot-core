package org.uniprot.core.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import org.uniprot.core.uniprot.impl.UniProtIdImpl;
/**
 *
 * @author lgonzales
 */
public class UniProtIdSerializer extends StdSerializer<UniProtIdImpl> {

    public UniProtIdSerializer() {
        super(UniProtIdImpl.class);
    }

    @Override
    public void serialize(UniProtIdImpl value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}

