package org.uniprot.core.json.parser.uniprot.serializer;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.Property;
import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
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

        gen.writeStringField("drLine", buildDrLine(crossReference));
        gen.writeObjectField("evidences", crossReference.getEvidences());

        gen.writeEndObject();
    }

    private String buildDrLine(UniProtKBCrossReferenceImpl crossReference) {
        StringBuilder drLine = new StringBuilder();

        UniProtKBDatabase database = crossReference.getDatabase();
        drLine.append(database.getName()).append(";");
        drLine.append(crossReference.getId()).append(";");

        if (crossReference.hasIsoformId()) {
            drLine.append(crossReference.getIsoformId()).append(";");
        } else {
            drLine.append("-;");
        }

        if (crossReference.hasProperties()) {
            drLine.append(buildProperty(database, crossReference.getProperties()));
        } else {
            drLine.append("-");
        }

        return drLine.toString();
    }

    private String buildProperty(UniProtKBDatabase database, List<Property> properties) {
        StringBuilder builder = new StringBuilder();
        Map<String, String> props =
                properties.stream().collect(Collectors.toMap(Property::getKey, Property::getValue));
        int propSize = database.getUniProtDatabaseDetail().getAttributes().size();
        for (int i = 0; i < propSize; i++) {
            UniProtDatabaseAttribute attribute = database.getUniProtDatabaseAttribute(i);
            if (attribute != null) {
                builder.append(props.getOrDefault(attribute.getName(), "-"));
                if (i < propSize - 1) {
                    builder.append(";");
                }
            }
        }
        return builder.toString();
    }
}
