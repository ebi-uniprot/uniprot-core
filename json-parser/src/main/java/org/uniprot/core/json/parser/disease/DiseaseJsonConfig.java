package org.uniprot.core.json.parser.disease;

import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.disease.builder.DiseaseCrossReferenceImpl;
import org.uniprot.core.cv.disease.builder.DiseaseEntryImpl;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.builder.KeywordIdImpl;
import org.uniprot.core.json.parser.JsonConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class DiseaseJsonConfig extends JsonConfig {
    private static DiseaseJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper
            simpleMapper; // mapper without unwanted fields like type, lighter version

    private DiseaseJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized DiseaseJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DiseaseJsonConfig();
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
        mod.addAbstractTypeMapping(DiseaseEntry.class, DiseaseEntryImpl.class);
        mod.addAbstractTypeMapping(KeywordId.class, KeywordIdImpl.class);
        mod.addAbstractTypeMapping(DiseaseCrossReference.class, DiseaseCrossReferenceImpl.class);

        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        ObjectMapper simpleObjMapper = getDefaultSimpleObjectMapper();
        return simpleObjMapper;
    }
}
