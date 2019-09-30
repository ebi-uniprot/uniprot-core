package org.uniprot.core.json.parser.taxonomy;

import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.taxonomy.*;
import org.uniprot.core.taxonomy.impl.*;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.impl.TaxonomyImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class TaxonomyJsonConfig extends JsonConfig {
    private static TaxonomyJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper
            simpleMapper; // mapper without unwanted fields like type, lighter version

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
        mod.addAbstractTypeMapping(TaxonomyInactiveReason.class, TaxonomyInactiveReasonImpl.class);
        mod.addAbstractTypeMapping(Taxonomy.class, TaxonomyImpl.class);

        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        ObjectMapper simpleObjMapper = getDefaultSimpleObjectMapper();
        return simpleObjMapper;
    }
}
