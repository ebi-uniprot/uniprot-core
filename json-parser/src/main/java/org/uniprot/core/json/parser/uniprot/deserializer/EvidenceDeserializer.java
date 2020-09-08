package org.uniprot.core.json.parser.uniprot.deserializer;

import java.io.IOException;

import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceImpl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author lgonzales
 * @since 07/09/2020
 */
public class EvidenceDeserializer extends StdDeserializer<EvidenceImpl> {

    private static final long serialVersionUID = 6569988850273471111L;

    public EvidenceDeserializer() {
        super(EvidenceImpl.class);
    }

    @Override
    public EvidenceImpl deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        EvidenceBuilder builder = new EvidenceBuilder();
        ObjectNode node = jp.readValueAsTree();
        if (node != null) {
            if (node.has("evidenceCode")) {
                JsonNode evidenceCode = node.get("evidenceCode");
                builder.evidenceCode(EvidenceCode.typeOf(evidenceCode.textValue()));
            }
            if (node.has("source")) {
                JsonNode source = node.get("source");
                builder.databaseName(source.textValue());
            }
            if (node.has("id")) {
                JsonNode id = node.get("id");
                builder.databaseId(id.textValue());
            }
        }
        return (EvidenceImpl) builder.build();
    }
}
