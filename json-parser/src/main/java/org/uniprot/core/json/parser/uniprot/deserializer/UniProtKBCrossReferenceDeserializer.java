package org.uniprot.core.json.parser.uniprot.deserializer;

import java.io.IOException;

import org.uniprot.core.json.parser.JsonParserException;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceImpl;
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
            node.fields()
                    .forEachRemaining(
                            entry -> {
                                switch (entry.getKey()) {
                                    case "database":
                                        String dbValue = entry.getValue().asText();
                                        builder.database(new UniProtKBDatabaseImpl(dbValue));
                                        break;

                                    case "id":
                                        builder.id(entry.getValue().asText());
                                        break;

                                    case "isoformId":
                                        builder.isoformId(entry.getValue().asText());
                                        break;

                                    case "evidences":
                                        entry.getValue()
                                                .elements()
                                                .forEachRemaining(
                                                        evidence ->
                                                                parseEvidence(
                                                                        jp, builder, evidence));
                                        break;

                                    default:
                                        builder.propertiesAdd(
                                                entry.getKey(), entry.getValue().asText());
                                }
                            });
        }
        return (UniProtKBCrossReferenceImpl) builder.build();
    }

    private void parseEvidence(
            JsonParser jp, UniProtCrossReferenceBuilder builder, JsonNode evidence) {
        try {
            EvidenceImpl converted = jp.getCodec().treeToValue(evidence, EvidenceImpl.class);
            builder.evidencesAdd(converted);
        } catch (JsonProcessingException e) {
            throw new JsonParserException("Error to deserialize UniProtKBCrossReference", e);
        }
    }
}
