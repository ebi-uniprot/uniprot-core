package uk.ac.ebi.uniprot.json.parser.keyword;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;
import uk.ac.ebi.uniprot.cv.keyword.KeywordEntry;
import uk.ac.ebi.uniprot.cv.keyword.KeywordStatistics;
import uk.ac.ebi.uniprot.cv.keyword.impl.GeneOntologyImpl;
import uk.ac.ebi.uniprot.cv.keyword.impl.KeywordEntryImpl;
import uk.ac.ebi.uniprot.cv.keyword.impl.KeywordImpl;
import uk.ac.ebi.uniprot.cv.keyword.impl.KeywordStatisticsImpl;
import uk.ac.ebi.uniprot.json.parser.JsonConfig;

/**
 * @author lgonzales
 */
public class KeywordJsonConfig extends JsonConfig {
    private static KeywordJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper simpleMapper; // mapper without unwanted fields like type, lighter version

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
