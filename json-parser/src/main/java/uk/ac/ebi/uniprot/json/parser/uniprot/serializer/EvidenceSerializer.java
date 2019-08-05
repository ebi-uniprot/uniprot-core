package uk.ac.ebi.uniprot.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import org.uniprot.core.uniprot.evidence.impl.EvidenceImpl;

public class EvidenceSerializer extends StdSerializer<EvidenceImpl> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EvidenceSerializer(){
        super(EvidenceImpl.class);
    }


	@Override
	public void serialize(EvidenceImpl evidence, JsonGenerator gen, SerializerProvider sp) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("evidenceCode",evidence.getEvidenceCode().getCode());
        if(evidence.getSource() != null) {
        	if(evidence.getSource().getDatabaseType() != null) {
				gen.writeStringField("source", evidence.getSource().getDatabaseType().getName());
			}
			gen.writeStringField("id",evidence.getSource().getId());
		}
		gen.writeEndObject();
	}
}