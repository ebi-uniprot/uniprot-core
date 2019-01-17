package uk.ac.ebi.uniprot.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtAccessionImpl;

import java.io.IOException;
/**
 *
 * @author lgonzales
 */
public class UniProtAccessionSerializer extends StdSerializer<UniProtAccessionImpl> {

    public UniProtAccessionSerializer() {
        super(UniProtAccessionImpl.class);
    }

    @Override
    public void serialize(UniProtAccessionImpl value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}
