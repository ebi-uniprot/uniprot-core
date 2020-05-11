package org.uniprot.core.json.parser.unirule;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.uniprot.core.unirule.impl.UniRuleIdImpl;

import java.io.IOException;

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
