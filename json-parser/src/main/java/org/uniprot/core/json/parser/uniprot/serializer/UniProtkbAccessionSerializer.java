package org.uniprot.core.json.parser.uniprot.serializer;

import java.io.IOException;

import org.uniprot.core.uniprotkb.impl.UniProtkbAccessionImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/** @author lgonzales */
public class UniProtkbAccessionSerializer extends StdSerializer<UniProtkbAccessionImpl> {

    public UniProtkbAccessionSerializer() {
        super(UniProtkbAccessionImpl.class);
    }

    @Override
    public void serialize(
            UniProtkbAccessionImpl value,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}
