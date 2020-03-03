package org.uniprot.core.json.parser.uniprot.serializer;

import java.io.IOException;

import org.uniprot.core.uniprot.xdb.UniProtDatabase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/** @author lgonzales */
public class UniProtDatabaseSerializer extends StdSerializer<UniProtDatabase> {

    public UniProtDatabaseSerializer() {
        super(UniProtDatabase.class);
    }

    @Override
    public void serialize(
            UniProtDatabase value,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(value.getName());
    }
}
