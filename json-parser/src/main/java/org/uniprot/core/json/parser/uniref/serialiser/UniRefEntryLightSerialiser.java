package org.uniprot.core.json.parser.uniref.serialiser;

import java.io.IOException;

import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.impl.UniRefEntryLightBuilder;
import org.uniprot.core.uniref.impl.UniRefEntryLightImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author lgonzales
 * @since 06/11/2020
 */
public class UniRefEntryLightSerialiser extends StdSerializer<UniRefEntryLightImpl> {

    private static final long serialVersionUID = 3723472187349283896L;

    public UniRefEntryLightSerialiser() {
        super(UniRefEntryLightImpl.class);
    }

    @Override
    public void serialize(
            UniRefEntryLightImpl value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        value =
                (UniRefEntryLightImpl)
                        UniRefEntryLightBuilder.from(value).seedId(getSeedId(value)).build();

        jgen.writeStartObject();
        JavaType javaType = provider.constructType(UniRefEntryLightImpl.class);
        BeanDescription beanDesc = provider.getConfig().introspect(javaType);
        JsonSerializer<Object> serializer =
                BeanSerializerFactory.instance.findBeanSerializer(provider, javaType, beanDesc);
        serializer.unwrappingSerializer(null).serialize(value, jgen, provider);
        jgen.writeEndObject();
    }

    private String getSeedId(UniRefEntryLight entry) {
        String[] splitSeedId = entry.getSeedId().split(",");
        return splitSeedId[splitSeedId.length - 1];
    }
}
