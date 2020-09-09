package org.uniprot.core.json.parser.uniprot.deserializer;

import java.io.IOException;

import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.json.parser.JsonParserException;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceImpl;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtKBCrossReferenceImpl;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author lgonzales
 * @since 08/09/2020
 */
public class UniProtKBCrossReferenceDeserializer
        extends StdDeserializer<UniProtKBCrossReferenceImpl> {

    private static final long serialVersionUID = -1022039763400678402L;

    public UniProtKBCrossReferenceDeserializer() {
        super(UniProtKBCrossReferenceImpl.class);
    }

    @Override
    public UniProtKBCrossReferenceImpl deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        UniProtCrossReferenceBuilder builder = new UniProtCrossReferenceBuilder();
        ObjectNode node = jp.readValueAsTree();
        if (node != null) {
            if (node.has("drLine")) {
                JsonNode drLine = node.get("drLine");
                parseDrLine(drLine.asText(), builder);
            }
            if (node.has("evidences")) {
                node.get("evidences")
                        .elements()
                        .forEachRemaining(
                                evidence -> {
                                    try {
                                        EvidenceImpl converted =
                                                jp.getCodec()
                                                        .treeToValue(evidence, EvidenceImpl.class);
                                        builder.evidencesAdd(converted);
                                    } catch (JsonProcessingException e) {
                                        throw new JsonParserException(
                                                "Error to deserialize UniProtKBCrossReference", e);
                                    }
                                });
            }
        }
        return (UniProtKBCrossReferenceImpl) builder.build();
    }

    private void parseDrLine(String drLine, UniProtCrossReferenceBuilder builder) {
        String[] drProps = drLine.split(";");
        UniProtKBDatabase database = new UniProtKBDatabaseImpl(drProps[0].trim());
        builder.database(database);
        builder.id(drProps[1].trim());

        String isoFormId = drProps[2].trim();
        if (!"-".equals(isoFormId)) {
            builder.isoformId(isoFormId);
        }

        for (int i = 3; i < drProps.length; i++) {
            String propValue = drProps[i].trim();
            if (!"-".equals(propValue)) {
                UniProtDatabaseAttribute attribute = database.getUniProtDatabaseAttribute(i - 3);
                if (attribute != null) {
                    builder.propertiesAdd(attribute, propValue);
                }
            }
        }
    }
}
