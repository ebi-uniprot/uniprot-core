package org.uniprot.core.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;

import java.io.IOException;

/** @author lgonzales */
public class UniProtDatabaseSerializer extends StdSerializer<UniProtKBDatabase> {

    public UniProtDatabaseSerializer() {
        super(UniProtKBDatabase.class);
    }

    @Override
    public void serialize(
            UniProtKBDatabase value,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(value.getName());
    }
}
