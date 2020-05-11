package org.uniprot.core.json.parser.uniprot.serializer;

import java.io.IOException;

import org.uniprot.core.uniprotkb.feature.impl.UniProtKBFeatureIdImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/** @author lgonzales */
public class FeatureIdSerializer extends StdSerializer<UniProtKBFeatureIdImpl> {

    public FeatureIdSerializer() {
        super(UniProtKBFeatureIdImpl.class);
    }

    @Override
    public void serialize(
            UniProtKBFeatureIdImpl featureId,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(featureId.getValue());
    }
}
