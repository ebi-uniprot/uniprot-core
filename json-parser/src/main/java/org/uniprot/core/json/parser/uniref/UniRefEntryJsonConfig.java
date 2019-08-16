package org.uniprot.core.json.parser.uniref;

import java.time.LocalDate;

import org.uniprot.core.Sequence;
import org.uniprot.core.Value;
import org.uniprot.core.impl.SequenceImpl;
import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.deserializer.LocalDateDeserializer;
import org.uniprot.core.json.parser.serializer.LocalDateSerializer;
import org.uniprot.core.json.parser.uniparc.UniParcIdSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.UniProtAccessionSerializer;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniparc.impl.UniParcIdImpl;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.impl.TaxonomyImpl;
import org.uniprot.core.uniref.GoTerm;
import org.uniprot.core.uniref.OverlapRegion;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.impl.GoTermImpl;
import org.uniprot.core.uniref.impl.OverlapRegionImpl;
import org.uniprot.core.uniref.impl.RepresentativeMemberImpl;
import org.uniprot.core.uniref.impl.UniRefEntryIdImpl;
import org.uniprot.core.uniref.impl.UniRefEntryImpl;
import org.uniprot.core.uniref.impl.UniRefMemberImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 *
 * @author jluo
 * @date: 13 Aug 2019
 *
*/

public class UniRefEntryJsonConfig extends JsonConfig {

	
	private static UniRefEntryJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper prettyMapper;

    private UniRefEntryJsonConfig() {
        this.objectMapper = initObjectMapper();
        this.prettyMapper = initPrettyObjectMapper();
    }

    public static synchronized UniRefEntryJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniRefEntryJsonConfig();
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
        mod.addAbstractTypeMapping(UniRefEntry.class, UniRefEntryImpl.class);
  
        mod.addAbstractTypeMapping(GoTerm.class, GoTermImpl.class);
        mod.addAbstractTypeMapping(OverlapRegion.class, OverlapRegionImpl.class);
        
        mod.addAbstractTypeMapping(Value.class, ValueImpl.class);
        mod.addAbstractTypeMapping(UniParcId.class, UniParcIdImpl.class);
        mod.addAbstractTypeMapping(UniRefEntryId.class, UniRefEntryIdImpl.class);
        mod.addAbstractTypeMapping(UniRefMember.class, UniRefMemberImpl.class);
        mod.addAbstractTypeMapping(RepresentativeMember.class, RepresentativeMemberImpl.class);
        mod.addAbstractTypeMapping(Sequence.class, SequenceImpl.class);
        mod.addAbstractTypeMapping(UniProtAccession.class, UniProtAccessionImpl.class);
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initPrettyObjectMapper() {
        ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();
        SimpleModule simpleMod = new SimpleModule();
        simpleMod.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleMod.addSerializer(UniParcIdImpl.class, new UniParcIdSerializer());
        
        simpleMod.addSerializer(UniRefEntryIdImpl.class, new UniRefEntryIdSerializer());
        simpleMod.addSerializer(UniProtAccessionImpl.class, new UniProtAccessionSerializer());
        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }

}

