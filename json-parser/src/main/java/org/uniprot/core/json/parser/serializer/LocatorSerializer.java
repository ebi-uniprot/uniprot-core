package org.uniprot.core.json.parser.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.uniprot.core.citation.impl.ElectronicArticleImpl;

import java.io.IOException;

/** @author lgonzales */
public class LocatorSerializer extends StdSerializer<ElectronicArticleImpl.LocatorImpl> {

    public LocatorSerializer() {
        super(ElectronicArticleImpl.LocatorImpl.class);
    }

    @Override
    public void serialize(
            ElectronicArticleImpl.LocatorImpl locator,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(locator.getValue());
    }
}
