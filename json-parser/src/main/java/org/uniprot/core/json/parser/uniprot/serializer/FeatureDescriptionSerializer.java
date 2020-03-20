package org.uniprot.core.json.parser.uniprot.serializer;

import java.io.IOException;

import org.uniprot.core.uniprotkb.feature.impl.FeatureDescriptionImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/** @author lgonzales */
public class FeatureDescriptionSerializer extends StdSerializer<FeatureDescriptionImpl> {

    public FeatureDescriptionSerializer() {
        super(FeatureDescriptionImpl.class);
    }

    @Override
    public void serialize(
            FeatureDescriptionImpl featureDescription,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(featureDescription.getValue());
    }
}
