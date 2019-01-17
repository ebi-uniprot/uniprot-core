package uk.ac.ebi.uniprot.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.APIsoformImpl;

import java.io.IOException;
/**
 *
 * @author lgonzales
 */
public class IsoformIdImplSerializer extends StdSerializer<APIsoformImpl.IsoformIdImpl> {


    public IsoformIdImplSerializer() {
        super(APIsoformImpl.IsoformIdImpl.class);
    }

    @Override
    public void serialize(APIsoformImpl.IsoformIdImpl isoformId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(isoformId.getValue());
    }
}
