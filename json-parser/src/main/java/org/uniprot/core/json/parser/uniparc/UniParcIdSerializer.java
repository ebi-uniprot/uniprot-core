package org.uniprot.core.json.parser.uniparc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.uniprot.core.uniparc.impl.UniParcIdImpl;

import java.io.IOException;

/**
 * @author jluo
 * @date: 24 May 2019
 */
public class UniParcIdSerializer extends StdSerializer<UniParcIdImpl> {

    public UniParcIdSerializer() {
        super(UniParcIdImpl.class);
    }

    @Override
    public void serialize(
            UniParcIdImpl value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}
