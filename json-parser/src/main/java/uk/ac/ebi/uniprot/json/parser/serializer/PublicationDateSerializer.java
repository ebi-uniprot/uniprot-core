package uk.ac.ebi.uniprot.json.parser.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import org.uniprot.core.citation.impl.PublicationDateImpl;
/**
 *
 * @author lgonzales
 */
public class PublicationDateSerializer extends StdSerializer<PublicationDateImpl> {

    public PublicationDateSerializer() {
        super(PublicationDateImpl.class);
    }

    @Override
    public void serialize(PublicationDateImpl value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}
