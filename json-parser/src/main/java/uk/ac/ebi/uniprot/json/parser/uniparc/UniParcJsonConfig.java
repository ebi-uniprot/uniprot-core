package uk.ac.ebi.uniprot.json.parser.uniparc;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.Value;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.impl.DefaultDatabaseType;
import uk.ac.ebi.uniprot.domain.impl.SequenceImpl;
import uk.ac.ebi.uniprot.domain.impl.ValueImpl;
import uk.ac.ebi.uniprot.domain.uniparc.InterproGroup;
import uk.ac.ebi.uniprot.domain.uniparc.SequenceFeature;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcEntry;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcId;
import uk.ac.ebi.uniprot.domain.uniparc.impl.InterProGroupImpl;
import uk.ac.ebi.uniprot.domain.uniparc.impl.SequenceFeatureImpl;
import uk.ac.ebi.uniprot.domain.uniparc.impl.UniParcDBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniparc.impl.UniParcEntryImpl;
import uk.ac.ebi.uniprot.domain.uniparc.impl.UniParcIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.impl.TaxonomyImpl;
import uk.ac.ebi.uniprot.json.parser.JsonConfig;
import uk.ac.ebi.uniprot.json.parser.deserializer.LocalDateDeserializer;
import uk.ac.ebi.uniprot.json.parser.serializer.LocalDateSerializer;

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

