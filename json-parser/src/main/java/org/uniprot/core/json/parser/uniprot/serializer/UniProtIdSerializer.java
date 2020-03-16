package org.uniprot.core.json.parser.uniprot.serializer;

import java.io.IOException;

import org.uniprot.core.uniprotkb.impl.UniProtkbIdImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/** @author lgonzales */
public class UniProtIdSerializer extends StdSerializer<UniProtkbIdImpl> {

    public UniProtIdSerializer() {
        super(UniProtkbIdImpl.class);
    }

    @Override
    public void serialize(
            UniProtkbIdImpl value,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}
