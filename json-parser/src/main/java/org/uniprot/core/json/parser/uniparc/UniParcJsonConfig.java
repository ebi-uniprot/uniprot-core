package org.uniprot.core.json.parser.uniparc;

import java.time.LocalDate;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.DatabaseType;
import org.uniprot.core.Sequence;
import org.uniprot.core.Value;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.impl.DefaultDatabaseType;
import org.uniprot.core.impl.SequenceImpl;
import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.deserializer.LocalDateDeserializer;
import org.uniprot.core.json.parser.serializer.LocalDateSerializer;
import org.uniprot.core.uniparc.InterproGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.UniParcDBCrossReference;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniparc.impl.InterProGroupImpl;
import org.uniprot.core.uniparc.impl.SequenceFeatureImpl;
import org.uniprot.core.uniparc.impl.UniParcDBCrossReferenceImpl;
import org.uniprot.core.uniparc.impl.UniParcEntryImpl;
import org.uniprot.core.uniparc.impl.UniParcIdImpl;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.impl.TaxonomyImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 *
 * @author jluo
 * @date: 24 May 2019
 *
*/

public class UniParcJsonConfig extends JsonConfig {
	private static UniParcJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper prettyMapper;

    private UniParcJsonConfig() {
        this.objectMapper = initObjectMapper();
        this.prettyMapper = initPrettyObjectMapper();
    }

    public static synchronized UniParcJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniParcJsonConfig();
        }
        return INSTANCE;
    }

    @Override
    public ObjectMapper getSimpleObjectMapper() {
        return this.prettyMapper;
    }

    @Override
    public ObjectMapper getFullObjectMapper() {
        return this.objectMapper;
    }


    private ObjectMapper initObjectMapper() {
        ObjectMapper objMapper =  getDefaultFullObjectMapper();

        SimpleModule mod = new SimpleModule();
        mod.addSerializer(LocalDate.class, new LocalDateSerializer());
        mod.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        mod.addAbstractTypeMapping(UniParcEntry.class, UniParcEntryImpl.class);
        mod.addAbstractTypeMapping(InterproGroup.class, InterProGroupImpl.class);
        mod.addAbstractTypeMapping(SequenceFeature.class, SequenceFeatureImpl.class);
        mod.addAbstractTypeMapping(Taxonomy.class, TaxonomyImpl.class);
        mod.addAbstractTypeMapping(UniParcDBCrossReference.class, UniParcDBCrossReferenceImpl.class);
        
        mod.addAbstractTypeMapping(Value.class, ValueImpl.class);
        mod.addAbstractTypeMapping(UniParcId.class, UniParcIdImpl.class);
        mod.addAbstractTypeMapping(DBCrossReference.class, DBCrossReferenceImpl.class);
        mod.addAbstractTypeMapping(DatabaseType.class, DefaultDatabaseType.class);
        mod.addAbstractTypeMapping(Sequence.class, SequenceImpl.class);
 
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initPrettyObjectMapper() {
        ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();
        SimpleModule simpleMod = new SimpleModule();
        simpleMod.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleMod.addSerializer(UniParcIdImpl.class, new UniParcIdSerializer());
        
        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }
}

