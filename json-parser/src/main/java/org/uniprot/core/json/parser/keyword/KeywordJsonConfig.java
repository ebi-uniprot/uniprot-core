package org.uniprot.core.json.parser.keyword;

import org.uniprot.core.cv.keyword.Keyword;
import org.uniprot.core.cv.keyword.impl.KeywordImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.cv.keyword.GeneOntology;
import org.uniprot.cv.keyword.KeywordEntry;
import org.uniprot.cv.keyword.KeywordStatistics;
import org.uniprot.cv.keyword.impl.GeneOntologyImpl;
import org.uniprot.cv.keyword.impl.KeywordEntryImpl;
import org.uniprot.cv.keyword.impl.KeywordStatisticsImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/** @author lgonzales */
public class KeywordJsonConfig extends JsonConfig {
    private static KeywordJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper
            simpleMapper; // mapper without unwanted fields like type, lighter version

    private KeywordJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized KeywordJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KeywordJsonConfig();
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
        mod.addAbstractTypeMapping(KeywordEntry.class, KeywordEntryImpl.class);
        mod.addAbstractTypeMapping(Keyword.class, KeywordImpl.class);
        mod.addAbstractTypeMapping(GeneOntology.class, GeneOntologyImpl.class);
        mod.addAbstractTypeMapping(KeywordStatistics.class, KeywordStatisticsImpl.class);

        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        ObjectMapper simpleObjMapper = getDefaultSimpleObjectMapper();
        return simpleObjMapper;
    }
}
