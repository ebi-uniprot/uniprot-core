package org.uniprot.core.json.parser.publication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.publication.CommunityAnnotation;
import org.uniprot.core.publication.CommunityMappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.publication.impl.CommunityAnnotationImpl;
import org.uniprot.core.publication.impl.CommunityMappedReferenceImpl;
import org.uniprot.core.publication.impl.MappedSourceImpl;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionImpl;

public class CommunityMappedReferenceJsonConfig extends JsonConfig {
    private static CommunityMappedReferenceJsonConfig instance;

    private final ObjectMapper objectMapper;
    private final ObjectMapper
            simpleMapper; // mapper without unwanted fields like type, lighter version

    private CommunityMappedReferenceJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized CommunityMappedReferenceJsonConfig getInstance() {
        if (instance == null) {
            instance = new CommunityMappedReferenceJsonConfig();
        }
        return instance;
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
                CommunityMappedReference.class, CommunityMappedReferenceImpl.class);
        mod.addAbstractTypeMapping(CommunityAnnotation.class, CommunityAnnotationImpl.class);
        mod.addAbstractTypeMapping(UniProtKBAccession.class, UniProtKBAccessionImpl.class);
        mod.addAbstractTypeMapping(MappedSource.class, MappedSourceImpl.class);
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        return getDefaultSimpleObjectMapper();
    }
}
