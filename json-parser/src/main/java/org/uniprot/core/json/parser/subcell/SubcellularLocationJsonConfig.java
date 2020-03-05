package org.uniprot.core.json.parser.subcell;

import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.go.builder.GoTermImpl;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.builder.KeywordIdImpl;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.cv.subcell.builder.SubcellularLocationEntryImpl;
import org.uniprot.core.impl.StatisticsImpl;
import org.uniprot.core.json.parser.JsonConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author lgonzales
 * @since 2019-07-11
 */
public class SubcellularLocationJsonConfig extends JsonConfig {
    private static SubcellularLocationJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper
            simpleMapper; // mapper without unwanted fields like type, lighter version

    private SubcellularLocationJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized SubcellularLocationJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SubcellularLocationJsonConfig();
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
        mod.addAbstractTypeMapping(
                SubcellularLocationEntry.class, SubcellularLocationEntryImpl.class);
        mod.addAbstractTypeMapping(Statistics.class, StatisticsImpl.class);
        mod.addAbstractTypeMapping(KeywordId.class, KeywordIdImpl.class);
        mod.addAbstractTypeMapping(GoTerm.class, GoTermImpl.class);
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();

        SimpleModule simpleMod = new SimpleModule();
        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }
}
