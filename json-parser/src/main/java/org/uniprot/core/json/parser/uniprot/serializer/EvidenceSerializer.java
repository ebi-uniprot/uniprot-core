package org.uniprot.core.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.uniprot.core.uniprotkb.evidence.impl.EvidenceImpl;

import java.io.IOException;

public class EvidenceSerializer extends StdSerializer<EvidenceImpl> {

    /** */
    private static final long serialVersionUID = 1L;

    public EvidenceSerializer() {
        super(EvidenceImpl.class);
    }

    @Override
    public void serialize(EvidenceImpl evidence, JsonGenerator gen, SerializerProvider sp)
            throws IOException {
        gen.writeStartObject();
        gen.writeStringField("evidenceCode", evidence.getEvidenceCode().getCode());
        if (evidence.getEvidenceCrossReference() != null) {
            if (evidence.getEvidenceCrossReference().getDatabase() != null) {
                gen.writeStringField(
                        "source", evidence.getEvidenceCrossReference().getDatabase().getName());
            }
            gen.writeStringField("id", evidence.getEvidenceCrossReference().getId());
        }
        gen.writeEndObject();
    }
}
