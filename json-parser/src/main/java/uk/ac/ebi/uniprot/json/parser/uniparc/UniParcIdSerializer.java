package uk.ac.ebi.uniprot.json.parser.uniparc;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import uk.ac.ebi.uniprot.domain.uniparc.impl.UniParcIdImpl;

/**
 *
 * @author jluo
 * @date: 24 May 2019
 *
*/

public class UniParcIdSerializer extends StdSerializer<UniParcIdImpl> {

    public UniParcIdSerializer() {
        super(UniParcIdImpl.class);
    }

    @Override
    public void serialize(UniParcIdImpl value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}
