package uk.ac.ebi.uniprot.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidencedValueImpl;

import java.io.IOException;
/**
 *
 * @author lgonzales
 */
public class ECNumberSerializer extends StdSerializer<ECNumberImpl> {

    public ECNumberSerializer() {
        super(ECNumberImpl.class);
    }

    @Override
    public void serialize(ECNumberImpl value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(value instanceof EC){
            EvidencedValueImpl evidencedValue = new EvidencedValueImpl(value.getValue(), ((EC) value).getEvidences());
            serializerProvider.findValueSerializer(EvidencedValueImpl.class).serialize(evidencedValue,jsonGenerator,serializerProvider);
        }else {
            jsonGenerator.writeString(value.getValue());
        }
    }

}

