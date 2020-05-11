package org.uniprot.core.json.parser.uniprot.serializer;

import java.io.IOException;

import org.uniprot.core.impl.ECNumberImpl;
import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueBuilder;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/** @author lgonzales */
public class ECNumberSerializer extends StdSerializer<ECNumberImpl> {

    public ECNumberSerializer() {
        super(ECNumberImpl.class);
    }

    @Override
    public void serialize(
            ECNumberImpl value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (value instanceof EC) {
            EvidencedValue evidencedValue =
                    new EvidencedValueBuilder()
                            .value(value.getValue())
                            .evidencesSet(((EC) value).getEvidences())
                            .build();
            serializerProvider
                    .findValueSerializer(EvidencedValueImpl.class)
                    .serialize(evidencedValue, jsonGenerator, serializerProvider);
        } else {
            jsonGenerator.writeString(value.getValue());
        }
    }
}
