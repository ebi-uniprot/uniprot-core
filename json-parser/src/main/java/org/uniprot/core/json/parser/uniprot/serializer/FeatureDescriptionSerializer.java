package org.uniprot.core.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import org.uniprot.core.uniprot.feature.impl.FeatureDescriptionImpl;
/**
 *
 * @author lgonzales
 */
public class FeatureDescriptionSerializer extends StdSerializer<FeatureDescriptionImpl> {


    public FeatureDescriptionSerializer() {
        super(FeatureDescriptionImpl.class);
    }

    @Override
    public void serialize(FeatureDescriptionImpl featureDescription, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(featureDescription.getValue());
    }
}