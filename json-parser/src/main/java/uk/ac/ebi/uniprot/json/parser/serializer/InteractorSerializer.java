package uk.ac.ebi.uniprot.json.parser.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.InteractionImpl;

import java.io.IOException;
/**
 *
 * @author lgonzales
 */
public class InteractorSerializer extends StdSerializer<InteractionImpl.InteractorImpl> {

    public InteractorSerializer() {
        super(InteractionImpl.InteractorImpl.class);
    }

    @Override
    public void serialize(InteractionImpl.InteractorImpl value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(value.getValue());
    }

}