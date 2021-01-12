package org.uniprot.core.json.parser.uniref;

import org.uniprot.core.Sequence;
import org.uniprot.core.Value;
import org.uniprot.core.impl.SequenceImpl;
import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.uniparc.UniParcIdSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.UniProtKBAccessionSerializer;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniparc.impl.UniParcIdImpl;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionImpl;
import org.uniprot.core.uniref.*;
import org.uniprot.core.uniref.impl.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author lgonzales
 * @since 12/01/2021
 */
public class UniRefMemberJsonConfig extends JsonConfig {

    private static UniRefMemberJsonConfig instance;

    private final ObjectMapper objectMapper;
    private final ObjectMapper prettyMapper;

    private UniRefMemberJsonConfig() {
        this.objectMapper = initObjectMapper();
        this.prettyMapper = initPrettyObjectMapper();
    }

    public static synchronized UniRefMemberJsonConfig getInstance() {
        if (instance == null) {
            instance = new UniRefMemberJsonConfig();
        }
        return instance;
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
        ObjectMapper objMapper = getDefaultFullObjectMapper();

        SimpleModule mod = new SimpleModule();
        mod.addAbstractTypeMapping(OverlapRegion.class, OverlapRegionImpl.class);

        mod.addAbstractTypeMapping(Value.class, ValueImpl.class);
        mod.addAbstractTypeMapping(UniParcId.class, UniParcIdImpl.class);
        mod.addAbstractTypeMapping(UniRefEntryId.class, UniRefEntryIdImpl.class);
        mod.addAbstractTypeMapping(UniRefMember.class, RepresentativeMemberImpl.class);
        mod.addAbstractTypeMapping(RepresentativeMember.class, RepresentativeMemberImpl.class);
        mod.addAbstractTypeMapping(Sequence.class, SequenceImpl.class);
        mod.addAbstractTypeMapping(UniProtKBAccession.class, UniProtKBAccessionImpl.class);
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initPrettyObjectMapper() {
        ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();
        SimpleModule simpleMod = new SimpleModule();
        simpleMod.addSerializer(UniParcIdImpl.class, new UniParcIdSerializer());

        simpleMod.addSerializer(UniRefEntryIdImpl.class, new UniRefEntryIdSerializer());
        simpleMod.addSerializer(UniProtKBAccessionImpl.class, new UniProtKBAccessionSerializer());
        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }
}
