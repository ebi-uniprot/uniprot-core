package org.uniprot.core.json.parser.publication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.publication.ComputationallyMappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.publication.impl.ComputationallyMappedReferenceImpl;
import org.uniprot.core.publication.impl.MappedSourceImpl;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionImpl;

public class ComputationallyMappedReferenceJsonConfig extends JsonConfig {
    private static ComputationallyMappedReferenceJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper
            simpleMapper; // mapper without unwanted fields like type, lighter version

    private ComputationallyMappedReferenceJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized ComputationallyMappedReferenceJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ComputationallyMappedReferenceJsonConfig();
        }
        return INSTANCE;
    }

    @Override
    public ObjectMapper getSimpleObjectMapper() {
        return this.simpleMapper;
    }

    @Override
    public ObjectMapper getFullObjectMapper() {
        return this.objectMapper;
    }

    private ObjectMapper initFullObjectMapper() {
        ObjectMapper objMapper = getDefaultFullObjectMapper();

        // customise the default mapper
        SimpleModule mod = new SimpleModule();
        mod.addAbstractTypeMapping(ComputationallyMappedReference.class, ComputationallyMappedReferenceImpl.class);
        mod.addAbstractTypeMapping(UniProtKBAccession.class, UniProtKBAccessionImpl.class);
        mod.addAbstractTypeMapping(MappedSource.class, MappedSourceImpl.class);
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        ObjectMapper simpleObjMapper = getDefaultSimpleObjectMapper();
        return simpleObjMapper;
    }
}
