package org.uniprot.core.json.parser.crossref;

import org.uniprot.core.Statistics;
import org.uniprot.core.cv.xdb.CrossRefEntry;
import org.uniprot.core.cv.xdb.impl.CrossRefEntryImpl;
import org.uniprot.core.impl.StatisticsImpl;
import org.uniprot.core.json.parser.JsonConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CrossRefJsonConfig extends JsonConfig {
    private static CrossRefJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper
            simpleMapper; // mapper without unwanted fields like type, lighter version

    private CrossRefJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized CrossRefJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrossRefJsonConfig();
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
        mod.addAbstractTypeMapping(CrossRefEntry.class, CrossRefEntryImpl.class);
        mod.addAbstractTypeMapping(Statistics.class, StatisticsImpl.class);

        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        return getDefaultSimpleObjectMapper();
    }
}
