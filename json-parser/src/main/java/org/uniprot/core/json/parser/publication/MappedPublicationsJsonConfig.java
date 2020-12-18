package org.uniprot.core.json.parser.publication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.publication.CommunityAnnotation;
import org.uniprot.core.publication.CommunityMappedReference;
import org.uniprot.core.publication.ComputationallyMappedReference;
import org.uniprot.core.publication.MappedPublications;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.publication.UniProtKBMappedReference;
import org.uniprot.core.publication.impl.CommunityAnnotationImpl;
import org.uniprot.core.publication.impl.CommunityMappedReferenceImpl;
import org.uniprot.core.publication.impl.ComputationallyMappedReferenceImpl;
import org.uniprot.core.publication.impl.MappedPublicationsImpl;
import org.uniprot.core.publication.impl.MappedSourceImpl;
import org.uniprot.core.publication.impl.UniProtKBMappedReferenceImpl;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceImpl;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueImpl;
import org.uniprot.core.uniprotkb.impl.ReferenceCommentImpl;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionImpl;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtKBCrossReferenceImpl;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

public class MappedPublicationsJsonConfig extends JsonConfig {
    private static MappedPublicationsJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper
            simpleMapper; // mapper without unwanted fields like type, lighter version

    private MappedPublicationsJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized MappedPublicationsJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MappedPublicationsJsonConfig();
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
        mod.addAbstractTypeMapping(MappedPublications.class, MappedPublicationsImpl.class);
        mod.addAbstractTypeMapping(
                CommunityMappedReference.class, CommunityMappedReferenceImpl.class);
        mod.addAbstractTypeMapping(CommunityAnnotation.class, CommunityAnnotationImpl.class);
        mod.addAbstractTypeMapping(ComputationallyMappedReference.class, ComputationallyMappedReferenceImpl.class);
        mod.addAbstractTypeMapping(UniProtKBMappedReference.class, UniProtKBMappedReferenceImpl.class);
        mod.addAbstractTypeMapping(UniProtKBAccession.class, UniProtKBAccessionImpl.class);
        mod.addAbstractTypeMapping(MappedSource.class, MappedSourceImpl.class);
        mod.addAbstractTypeMapping(ReferenceComment.class, ReferenceCommentImpl.class);
        mod.addAbstractTypeMapping(EvidencedValue.class, EvidencedValueImpl.class);
        mod.addAbstractTypeMapping(Evidence.class, EvidenceImpl.class);

        mod.addAbstractTypeMapping(CrossReference.class, CrossReferenceImpl.class);
        mod.addAbstractTypeMapping(
                UniProtKBCrossReference.class, UniProtKBCrossReferenceImpl.class);
        mod.addAbstractTypeMapping(UniProtKBDatabase.class, UniProtKBDatabaseImpl.class);
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        ObjectMapper simpleObjMapper = getDefaultSimpleObjectMapper();
        return simpleObjMapper;
    }
}
