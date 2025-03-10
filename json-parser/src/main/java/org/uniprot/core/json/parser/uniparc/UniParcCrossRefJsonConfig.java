package org.uniprot.core.json.parser.uniparc;

import java.time.LocalDate;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.uniprot.core.CrossReference;
import org.uniprot.core.Database;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.impl.DefaultDatabase;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.deserializer.LocalDateDeserializer;
import org.uniprot.core.json.parser.serializer.LocalDateSerializer;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceImpl;
import org.uniprot.core.uniparc.impl.UniParcCrossReferencePair;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceImpl;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.uniprot.core.util.Pair;

/**
 * @author jluo
 * @date: 24 May 2019
 */
public class UniParcCrossRefJsonConfig extends JsonConfig {
    private static UniParcCrossRefJsonConfig instance;

    private final ObjectMapper objectMapper;
    private final ObjectMapper prettyMapper;

    private UniParcCrossRefJsonConfig() {
        this.objectMapper = initObjectMapper();
        this.prettyMapper = initPrettyObjectMapper();
    }

    public static synchronized UniParcCrossRefJsonConfig getInstance() {
        if (instance == null) {
            instance = new UniParcCrossRefJsonConfig();
        }
        return instance;
    }

    @Override
    public ObjectMapper getFullObjectMapper() {
        return this.objectMapper;
    }

    @Override
    public ObjectMapper getSimpleObjectMapper() {
        return this.prettyMapper;
    }

    private ObjectMapper initObjectMapper() {
        ObjectMapper objMapper = getDefaultFullObjectMapper();

        SimpleModule mod = new SimpleModule();
        mod.addSerializer(LocalDate.class, new LocalDateSerializer());
        mod.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        mod.addAbstractTypeMapping(Organism.class, OrganismImpl.class);
        mod.addAbstractTypeMapping(UniParcCrossReference.class, UniParcCrossReferenceImpl.class);
        mod.addAbstractTypeMapping(Evidence.class, EvidenceImpl.class);
        mod.addAbstractTypeMapping(CrossReference.class, CrossReferenceImpl.class);
        mod.addAbstractTypeMapping(Database.class, DefaultDatabase.class);
        mod.addAbstractTypeMapping(Pair.class, UniParcCrossReferencePair.class);
        objMapper.registerModule(mod);
        return objMapper;
    }

    private ObjectMapper initPrettyObjectMapper() {
        ObjectMapper simpleObjectMapper = getDefaultSimpleObjectMapper();
        simpleObjectMapper.registerModule(new JavaTimeModule());
        SimpleModule simpleMod = new SimpleModule();
        simpleMod.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleObjectMapper.registerModule(simpleMod);
        return simpleObjectMapper;
    }
}
