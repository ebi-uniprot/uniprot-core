package org.uniprot.core.json.parser.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import org.uniprot.core.citation.impl.JournalImpl;
/**
 *
 * @author lgonzales
 */
public class JournalSerializer extends StdSerializer<JournalImpl> {


    public JournalSerializer() {
        super(JournalImpl.class);
    }

    @Override
    public void serialize(JournalImpl journal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(journal.getName());
    }
}