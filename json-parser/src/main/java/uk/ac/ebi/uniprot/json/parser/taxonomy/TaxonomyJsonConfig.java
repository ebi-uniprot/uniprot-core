package uk.ac.ebi.uniprot.json.parser.taxonomy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyEntry;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyLineage;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyStatistics;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyStrain;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonomyEntryImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonomyLineageImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonomyStatisticsImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonomyStrainImpl;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.impl.TaxonomyImpl;
import uk.ac.ebi.uniprot.json.parser.JsonConfig;

public class TaxonomyJsonConfig extends JsonConfig {
    private static TaxonomyJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper simpleMapper; // mapper without unwanted fields like type, lighter version

    private TaxonomyJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized TaxonomyJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaxonomyJsonConfig();
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
        mod.addAbstractTypeMapping(TaxonomyEntry.class, TaxonomyEntryImpl.class);
        mod.addAbstractTypeMapping(TaxonomyLineage.class, TaxonomyLineageImpl.class);
        mod.addAbstractTypeMapping(TaxonomyStatistics.class, TaxonomyStatisticsImpl.class);
        mod.addAbstractTypeMapping(TaxonomyStrain.class, TaxonomyStrainImpl.class);
        mod.addAbstractTypeMapping(Taxonomy.class, TaxonomyImpl.class);

        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        ObjectMapper simpleObjMapper = getDefaultSimpleObjectMapper();
        return simpleObjMapper;
    }
}

