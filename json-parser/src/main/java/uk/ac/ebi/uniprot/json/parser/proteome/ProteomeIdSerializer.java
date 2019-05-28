package uk.ac.ebi.uniprot.json.parser.proteome;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import uk.ac.ebi.uniprot.domain.proteome.impl.ProteomeIdImpl;

/**
 *
 * @author jluo
 * @date: 24 May 2019
 *
*/

public class ProteomeIdSerializer extends StdSerializer<ProteomeIdImpl> {

    public ProteomeIdSerializer() {
        super(ProteomeIdImpl.class);
    }

    @Override
    public void serialize(ProteomeIdImpl value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}


