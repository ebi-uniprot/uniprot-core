package org.uniprot.core.json.parser.proteome;

import java.io.IOException;

import org.uniprot.core.proteome.impl.ProteomeIdImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author jluo
 * @date: 24 May 2019
 */
public class ProteomeIdSerializer extends StdSerializer<ProteomeIdImpl> {

    public ProteomeIdSerializer() {
        super(ProteomeIdImpl.class);
    }

    @Override
    public void serialize(
            ProteomeIdImpl value,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}
