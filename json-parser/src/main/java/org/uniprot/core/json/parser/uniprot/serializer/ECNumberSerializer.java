package org.uniprot.core.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import org.uniprot.core.impl.ECNumberImpl;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;
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

