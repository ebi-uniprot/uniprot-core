package uk.ac.ebi.uniprot.json.parser.subcell;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;
import uk.ac.ebi.uniprot.cv.keyword.impl.GeneOntologyImpl;
import uk.ac.ebi.uniprot.cv.keyword.impl.KeywordImpl;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocationEntry;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocationStatistics;
import uk.ac.ebi.uniprot.cv.subcell.impl.SubcellularLocationEntryImpl;
import uk.ac.ebi.uniprot.cv.subcell.impl.SubcellularLocationStatisticsImpl;
import uk.ac.ebi.uniprot.json.parser.JsonConfig;

/**
 * @author lgonzales
 * @since 2019-07-11
 */
public class SubcellularLocationJsonConfig extends JsonConfig {
    private static SubcellularLocationJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper simpleMapper; // mapper without unwanted fields like type, lighter version

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
        mod.addAbstractTypeMapping(SubcellularLocationEntry.class, SubcellularLocationEntryImpl.class);
        mod.addAbstractTypeMapping(SubcellularLocationStatistics.class, SubcellularLocationStatisticsImpl.class);
        mod.addAbstractTypeMapping(Keyword.class, KeywordImpl.class);
        mod.addAbstractTypeMapping(GeneOntology.class, GeneOntologyImpl.class);
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

