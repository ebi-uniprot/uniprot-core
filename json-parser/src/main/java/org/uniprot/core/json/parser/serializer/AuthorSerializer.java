package org.uniprot.core.json.parser.serializer;

import java.io.IOException;

import org.uniprot.core.citation.impl.AuthorImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/** @author lgonzales */
public class AuthorSerializer extends StdSerializer<AuthorImpl> {

    public AuthorSerializer() {
        super(AuthorImpl.class);
    }

    @Override
    public void serialize(
            AuthorImpl value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(value.getValue());
    }
}
