package org.uniprot.core.json.parser.uniprot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.uniprot.core.Position;
import org.uniprot.core.PositionModifier;

import java.io.IOException;

/**
 * Created 04/11/2020
 *
 * @author Edd
 */
public class PositionSerializer extends StdSerializer<Position> {

    public PositionSerializer() {
        super(Position.class);
    }

    @Override
    public void serialize(
            Position position, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        String valueFieldName = "value";
        PositionModifier modifier = position.getModifier();
        if (modifier == PositionModifier.UNKNOWN) {
            jsonGenerator.writeNullField(valueFieldName);
        } else {
            jsonGenerator.writeObjectField(valueFieldName, position.getValue());
        }
        jsonGenerator.writeObjectField("modifier", modifier);
        jsonGenerator.writeEndObject();
    }
}
