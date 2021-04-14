package org.uniprot.core.json.parser.keyword.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.uniprot.core.cv.keyword.KeywordCategory;

import java.io.IOException;

/**
 * @author lgonzales
 * @since 14/04/2021
 */
public class KeywordCategorySerializer extends StdSerializer<KeywordCategory> {

    private static final long serialVersionUID = -6059366854247432259L;

    public KeywordCategorySerializer() {
        super(KeywordCategory.class);
    }

    @Override
    public void serialize(
            KeywordCategory category, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", category.getName());
        jsonGenerator.writeStringField("id", category.getId());
        jsonGenerator.writeEndObject();
    }
}