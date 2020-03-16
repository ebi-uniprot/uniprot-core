package org.uniprot.core.json.parser.uniprot.serializer;

import java.io.IOException;

import org.uniprot.core.uniprotkb.xdb.UniProtkbDatabase;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/** @author lgonzales */
public class UniProtDatabaseSerializer extends StdSerializer<UniProtkbDatabase> {

    public UniProtDatabaseSerializer() {
        super(UniProtkbDatabase.class);
    }

    @Override
    public void serialize(
            UniProtkbDatabase value,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(value.getName());
    }
}
