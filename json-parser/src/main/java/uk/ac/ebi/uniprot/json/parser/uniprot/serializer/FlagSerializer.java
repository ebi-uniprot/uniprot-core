package uk.ac.ebi.uniprot.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import org.uniprot.core.uniprot.description.impl.FlagImpl;
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