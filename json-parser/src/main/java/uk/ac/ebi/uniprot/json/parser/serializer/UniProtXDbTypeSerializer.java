package uk.ac.ebi.uniprot.json.parser.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;

import java.io.IOException;
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