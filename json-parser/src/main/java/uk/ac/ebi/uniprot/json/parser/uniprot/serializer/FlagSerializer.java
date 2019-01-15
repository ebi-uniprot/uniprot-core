package uk.ac.ebi.uniprot.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.FlagImpl;

import java.io.IOException;
/**
 *
 * @author lgonzales
 */
public class FlagSerializer extends StdSerializer<FlagImpl> {


    public FlagSerializer() {
        super(FlagImpl.class);
    }

    @Override
    public void serialize(FlagImpl flag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(flag.getType().toDisplayName());
    }
}