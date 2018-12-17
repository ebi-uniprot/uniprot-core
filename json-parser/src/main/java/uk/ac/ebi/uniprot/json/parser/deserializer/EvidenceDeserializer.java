package uk.ac.ebi.uniprot.json.parser.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

import java.io.IOException;

public class EvidenceDeserializer extends StdDeserializer<Evidence> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public EvidenceDeserializer() {
        super(Evidence.class);
    }


	@Override
	public Evidence deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return EvidenceImpl.parseEvidenceLine(jp.readValueAs(String.class));
	}

}
