package org.uniprot.core.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.uniprot.core.uniprotkb.feature.impl.FeatureIdImpl;

import java.io.IOException;

/** @author lgonzales */
public class FeatureIdSerializer extends StdSerializer<FeatureIdImpl> {

    public FeatureIdSerializer() {
        super(FeatureIdImpl.class);
    }

    @Override
    public void serialize(
            FeatureIdImpl featureId,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(featureId.getValue());
    }
}
