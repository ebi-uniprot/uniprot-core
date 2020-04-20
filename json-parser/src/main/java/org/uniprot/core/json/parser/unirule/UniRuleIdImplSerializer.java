package org.uniprot.core.json.parser.unirule;

import java.io.IOException;

import org.uniprot.core.unirule.impl.UniRuleIdImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/** @author sahmad */
public class UniRuleIdImplSerializer extends StdSerializer<UniRuleIdImpl> {

    public UniRuleIdImplSerializer() {
        super(UniRuleIdImpl.class);
    }

    @Override
    public void serialize(
            UniRuleIdImpl value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}
