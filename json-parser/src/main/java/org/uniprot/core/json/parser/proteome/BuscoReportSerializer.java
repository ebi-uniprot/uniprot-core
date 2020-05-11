package org.uniprot.core.json.parser.proteome;

import java.io.IOException;

import org.uniprot.core.proteome.impl.BuscoReportImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author lgonzales
 * @since 17/04/2020
 */
public class BuscoReportSerializer extends StdSerializer<BuscoReportImpl> {

    private static final long serialVersionUID = 204237402637911055L;

    public BuscoReportSerializer() {
        super(BuscoReportImpl.class);
    }

    @Override
    public void serialize(BuscoReportImpl value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        gen.writeStartObject();
        JavaType javaType = provider.constructType(BuscoReportImpl.class);
        BeanDescription beanDesc = provider.getConfig().introspect(javaType);
        JsonSerializer<Object> serializer =
                BeanSerializerFactory.instance.findBeanSerializer(provider, javaType, beanDesc);
        serializer.unwrappingSerializer(null).serialize(value, gen, provider);

        gen.writeObjectField("score", value.getScore());
        gen.writeEndObject();
    }
}
