package org.uniprot.core.json.parser.uniparc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.uniprot.core.CrossReference;
import org.uniprot.core.Sequence;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.impl.SequenceImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.deserializer.LocalDateDeserializer;
import org.uniprot.core.json.parser.serializer.LocalDateSerializer;
import org.uniprot.core.uniparc.InterProGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.uniparc.impl.InterProGroupImpl;
import org.uniprot.core.uniparc.impl.SequenceFeatureImpl;
import org.uniprot.core.uniparc.impl.UniParcEntryLightImpl;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.PairImpl;

import java.time.LocalDate;

public class UniParcEntryLightJsonConfig extends JsonConfig {
    private static UniParcEntryLightJsonConfig instance;

    private final ObjectMapper objectMapper;
    private final ObjectMapper prettyMapper;

    private UniParcEntryLightJsonConfig() {
        this.objectMapper = initObjectMapper();
        this.prettyMapper = initPrettyObjectMapper();
    }

    public static synchronized UniParcEntryLightJsonConfig getInstance() {
        if (instance == null) {
            instance = new UniParcEntryLightJsonConfig();
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
        mod.addAbstractTypeMapping(UniParcEntryLight.class, UniParcEntryLightImpl.class);
        mod.addAbstractTypeMapping(InterProGroup.class, InterProGroupImpl.class);
        mod.addAbstractTypeMapping(SequenceFeature.class, SequenceFeatureImpl.class);
        mod.addAbstractTypeMapping(CrossReference.class, CrossReferenceImpl.class);
        mod.addAbstractTypeMapping(Sequence.class, SequenceImpl.class);
        mod.addAbstractTypeMapping(Pair.class, PairImpl.class);
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initPrettyObjectMapper() {
        ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();
        SimpleModule simpleMod = new SimpleModule();
        simpleMod.addSerializer(LocalDate.class, new LocalDateSerializer());
        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }
}
