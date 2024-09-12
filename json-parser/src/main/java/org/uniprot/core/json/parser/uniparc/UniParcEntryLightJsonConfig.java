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
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.impl.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceImpl;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismImpl;

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
        mod.addAbstractTypeMapping(SequenceFeatureLocation.class, SequenceFeatureLocationImpl.class);
        mod.addAbstractTypeMapping(CrossReference.class, CrossReferenceImpl.class);
        mod.addAbstractTypeMapping(Sequence.class, SequenceImpl.class);
        mod.addAbstractTypeMapping(CommonOrganism.class, CommonOrganismImpl.class);
        mod.addAbstractTypeMapping(Organism.class, OrganismImpl.class);
        mod.addAbstractTypeMapping(Evidence.class, EvidenceImpl.class);
        mod.addAbstractTypeMapping(Proteome.class, ProteomeImpl.class);
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
