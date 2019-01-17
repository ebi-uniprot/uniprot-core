package uk.ac.ebi.uniprot.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureDescriptionImpl;

import java.io.IOException;
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