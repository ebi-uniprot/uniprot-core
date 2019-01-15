package uk.ac.ebi.uniprot.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtIdImpl;

import java.io.IOException;
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

