package org.uniprot.core.json.parser.literature;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Journal;
import org.uniprot.core.citation.PublicationDate;
import org.uniprot.core.citation.impl.AuthorImpl;
import org.uniprot.core.citation.impl.JournalImpl;
import org.uniprot.core.citation.impl.PublicationDateImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.serializer.AuthorSerializer;
import org.uniprot.core.json.parser.serializer.JournalSerializer;
import org.uniprot.core.json.parser.serializer.PublicationDateSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.UniProtAccessionSerializer;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.literature.LiteratureStatistics;
import org.uniprot.core.literature.impl.LiteratureEntryImpl;
import org.uniprot.core.literature.impl.LiteratureMappedReferenceImpl;
import org.uniprot.core.literature.impl.LiteratureStatisticsImpl;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class LiteratureJsonConfig extends JsonConfig {
    private static LiteratureJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper
            simpleMapper; // mapper without unwanted fields like type, lighter version

    private LiteratureJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized LiteratureJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LiteratureJsonConfig();
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
        mod.addAbstractTypeMapping(LiteratureEntry.class, LiteratureEntryImpl.class);
        mod.addAbstractTypeMapping(LiteratureStatistics.class, LiteratureStatisticsImpl.class);
        mod.addAbstractTypeMapping(
                LiteratureMappedReference.class, LiteratureMappedReferenceImpl.class);
        mod.addAbstractTypeMapping(PublicationDate.class, PublicationDateImpl.class);
        mod.addAbstractTypeMapping(Author.class, AuthorImpl.class);
        mod.addAbstractTypeMapping(Journal.class, JournalImpl.class);
        mod.addAbstractTypeMapping(UniProtAccession.class, UniProtAccessionImpl.class);
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();

        SimpleModule simpleMod = new SimpleModule();
        simpleMod.addSerializer(AuthorImpl.class, new AuthorSerializer());
        simpleMod.addSerializer(PublicationDateImpl.class, new PublicationDateSerializer());
        simpleMod.addSerializer(JournalImpl.class, new JournalSerializer());
        simpleMod.addSerializer(UniProtAccessionImpl.class, new UniProtAccessionSerializer());

        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }
}
