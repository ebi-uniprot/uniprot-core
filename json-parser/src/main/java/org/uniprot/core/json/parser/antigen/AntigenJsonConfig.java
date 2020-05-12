package org.uniprot.core.json.parser.antigen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.uniprot.core.CrossReference;
import org.uniprot.core.Sequence;
import org.uniprot.core.antigen.AntigenEntry;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.antigen.impl.AntigenEntryImpl;
import org.uniprot.core.antigen.impl.AntigenFeatureImpl;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.feature.FeatureDescription;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceImpl;
import org.uniprot.core.feature.impl.FeatureDescriptionImpl;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.impl.SequenceImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.uniprot.serializer.EvidenceSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.FeatureDescriptionSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.UniProtIdSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.UniProtKBAccessionSerializer;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceImpl;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionImpl;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdImpl;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismImpl;

/**
 * @author lgonzales
 * @since 11/05/2020
 */
public class AntigenJsonConfig extends JsonConfig {

    private static AntigenJsonConfig instance;

    private final ObjectMapper fullMapper;
    private final ObjectMapper simpleMapper;

    private AntigenJsonConfig() {
        this.fullMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized AntigenJsonConfig getInstance() {
        if (instance == null) {
            instance = new AntigenJsonConfig();
        }
        return instance;
    }

    @Override
    public ObjectMapper getSimpleObjectMapper() {
        return simpleMapper;
    }

    @Override
    public ObjectMapper getFullObjectMapper() {
        return fullMapper;
    }

    private ObjectMapper initFullObjectMapper() {
        ObjectMapper objMapper = getDefaultFullObjectMapper();

        // customise the default mapper
        SimpleModule mod = new SimpleModule();
        mod.addAbstractTypeMapping(AntigenEntry.class, AntigenEntryImpl.class);
        mod.addAbstractTypeMapping(AntigenFeature.class, AntigenFeatureImpl.class);
        mod.addAbstractTypeMapping(UniProtKBAccession.class, UniProtKBAccessionImpl.class);
        mod.addAbstractTypeMapping(UniProtKBId.class, UniProtKBIdImpl.class);
        mod.addAbstractTypeMapping(Organism.class, OrganismImpl.class);
        mod.addAbstractTypeMapping(FeatureDescription.class, FeatureDescriptionImpl.class);
        mod.addAbstractTypeMapping(AlternativeSequence.class, AlternativeSequenceImpl.class);
        mod.addAbstractTypeMapping(Evidence.class, EvidenceImpl.class);
        mod.addAbstractTypeMapping(CrossReference.class, CrossReferenceImpl.class);
        mod.addAbstractTypeMapping(Sequence.class, SequenceImpl.class);
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        ObjectMapper simpleObjectMapper = getDefaultSimpleObjectMapper();

        SimpleModule simpleMod = new SimpleModule();
        simpleMod.addSerializer(UniProtKBAccessionImpl.class, new UniProtKBAccessionSerializer());
        simpleMod.addSerializer(FeatureDescriptionImpl.class, new FeatureDescriptionSerializer());
        simpleMod.addSerializer(UniProtKBAccessionImpl.class, new UniProtKBAccessionSerializer());
        simpleMod.addSerializer(UniProtKBIdImpl.class, new UniProtIdSerializer());
        simpleMod.addSerializer(EvidenceImpl.class, new EvidenceSerializer());

        simpleObjectMapper.registerModule(simpleMod);
        return simpleObjectMapper;
    }
}
