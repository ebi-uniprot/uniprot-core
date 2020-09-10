package org.uniprot.core.json.parser.uniprot.serializer;

import java.io.IOException;
import java.util.List;

import org.uniprot.core.Property;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtKBCrossReferenceImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author lgonzales
 * @since 08/09/2020
 */
public class UniProtKBCrossReferenceSerializer extends StdSerializer<UniProtKBCrossReferenceImpl> {

    private static final long serialVersionUID = -2508208281068195717L;

    public UniProtKBCrossReferenceSerializer() {
        super(UniProtKBCrossReferenceImpl.class);
    }

    @Override
    public void serialize(
            UniProtKBCrossReferenceImpl crossReference, JsonGenerator gen, SerializerProvider sp)
            throws IOException {
        gen.writeStartObject();

        gen.writeStringField("database", crossReference.getDatabase().getName());
        gen.writeStringField("id", crossReference.getId());

        if (crossReference.hasIsoformId()) {
            gen.writeStringField("isoformId", crossReference.getIsoformId());
        }

        if (crossReference.hasEvidences()) {
            gen.writeObjectField("evidences", crossReference.getEvidences());
        }

        if (crossReference.hasProperties()) {
            buildProperties(gen, crossReference.getProperties());
        }
        gen.writeEndObject();
    }

    private void buildProperties(JsonGenerator gen, List<Property> properties) throws IOException {
        for (Property property : properties) {
            gen.writeStringField(property.getKey(), property.getValue());
        }
    }
}
