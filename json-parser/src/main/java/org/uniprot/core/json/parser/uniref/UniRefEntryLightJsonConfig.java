package org.uniprot.core.json.parser.uniref;

import java.time.LocalDate;

import org.uniprot.core.Value;
import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.deserializer.LocalDateDeserializer;
import org.uniprot.core.json.parser.serializer.LocalDateSerializer;
import org.uniprot.core.uniref.*;
import org.uniprot.core.uniref.impl.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author lgonzales
 * @since 07/07/2020
 */
public class UniRefEntryLightJsonConfig extends JsonConfig {

    private static UniRefEntryLightJsonConfig instance;

    private final ObjectMapper objectMapper;
    private final ObjectMapper prettyMapper;

    private UniRefEntryLightJsonConfig() {
        this.objectMapper = initObjectMapper();
        this.prettyMapper = initPrettyObjectMapper();
    }

    public static synchronized UniRefEntryLightJsonConfig getInstance() {
        if (instance == null) {
            instance = new UniRefEntryLightJsonConfig();
        }
        return instance;
    }

    @Override
    public ObjectMapper getSimpleObjectMapper() {
        return this.prettyMapper;
    }

    @Override
    public ObjectMapper getFullObjectMapper() {
        return this.objectMapper;
    }

    private ObjectMapper initObjectMapper() {
        ObjectMapper objMapper = getDefaultFullObjectMapper();

        SimpleModule mod = new SimpleModule();
        mod.addSerializer(LocalDate.class, new LocalDateSerializer());
        mod.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        mod.addAbstractTypeMapping(UniRefEntryLight.class, UniRefEntryLightImpl.class);
        mod.addAbstractTypeMapping(Value.class, ValueImpl.class);
        mod.addAbstractTypeMapping(UniRefEntryId.class, UniRefEntryIdImpl.class);
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initPrettyObjectMapper() {
        ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();
        SimpleModule simpleMod = new SimpleModule();
        simpleMod.addSerializer(LocalDate.class, new LocalDateSerializer());

        simpleMod.addSerializer(UniRefEntryIdImpl.class, new UniRefEntryIdSerializer());
        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }
}
