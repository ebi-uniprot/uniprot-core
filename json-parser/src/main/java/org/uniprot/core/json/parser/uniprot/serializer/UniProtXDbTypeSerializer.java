package org.uniprot.core.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import org.uniprot.core.uniprot.xdb.UniProtXDbType;
/**
 *
 * @author lgonzales
 */
public class UniProtXDbTypeSerializer extends StdSerializer<UniProtXDbType> {

    public UniProtXDbTypeSerializer() {
        super(UniProtXDbType.class);
    }

    @Override
    public void serialize(UniProtXDbType value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(value.getName());
    }
}