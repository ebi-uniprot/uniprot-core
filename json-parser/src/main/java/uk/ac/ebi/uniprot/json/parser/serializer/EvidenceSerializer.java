package uk.ac.ebi.uniprot.json.parser.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.io.IOException;

public class EvidenceSerializer extends StdSerializer<Evidence> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EvidenceSerializer(){
        super(Evidence.class);
    }


	@Override
	public void serialize(Evidence value, JsonGenerator gen, SerializerProvider sp) throws IOException, JsonProcessingException {
        gen.writeString(value.getValue());
		
	}
}