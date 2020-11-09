package org.uniprot.core.json.parser.genecentric;

import org.uniprot.core.CrossReference;
import org.uniprot.core.Sequence;
import org.uniprot.core.genecentric.GeneCentricEntry;
import org.uniprot.core.genecentric.Protein;
import org.uniprot.core.genecentric.impl.GeneCentricEntryImpl;
import org.uniprot.core.genecentric.impl.ProteinImpl;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.impl.SequenceImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.uniprot.serializer.EvidenceSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.UniProtDatabaseSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.UniProtIdSerializer;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceImpl;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdImpl;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismImpl;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author lgonzales
 * @since 23/10/2020
 */
public class GeneCentricJsonConfig extends JsonConfig {
    private static GeneCentricJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper
            simpleMapper; // mapper without unwanted fields like type, lighter version

    private GeneCentricJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized GeneCentricJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GeneCentricJsonConfig();
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
        mod.addAbstractTypeMapping(GeneCentricEntry.class, GeneCentricEntryImpl.class);
        mod.addAbstractTypeMapping(Protein.class, ProteinImpl.class);
        mod.addAbstractTypeMapping(UniProtKBId.class, UniProtKBIdImpl.class);
        mod.addAbstractTypeMapping(Organism.class, OrganismImpl.class);
        mod.addAbstractTypeMapping(Evidence.class, EvidenceImpl.class);
        mod.addAbstractTypeMapping(CrossReference.class, CrossReferenceImpl.class);
        mod.addAbstractTypeMapping(Sequence.class, SequenceImpl.class);
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        ObjectMapper simpleObjMapper = getDefaultSimpleObjectMapper();

        SimpleModule prettyWriterModule = new SimpleModule();
        prettyWriterModule.addSerializer(UniProtKBIdImpl.class, new UniProtIdSerializer());
        prettyWriterModule.addSerializer(EvidenceImpl.class, new EvidenceSerializer());
        prettyWriterModule.addSerializer(UniProtKBDatabase.class, new UniProtDatabaseSerializer());

        simpleObjMapper.registerModule(prettyWriterModule);
        return simpleObjMapper;
    }
}
