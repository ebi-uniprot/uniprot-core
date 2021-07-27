package org.uniprot.core.json.parser.literature;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.citation.impl.*;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.serializer.AuthorSerializer;
import org.uniprot.core.json.parser.serializer.JournalSerializer;
import org.uniprot.core.json.parser.serializer.LocatorSerializer;
import org.uniprot.core.json.parser.serializer.PublicationDateSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.UniProtKBAccessionSerializer;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.LiteratureStatistics;
import org.uniprot.core.literature.impl.LiteratureEntryImpl;
import org.uniprot.core.literature.impl.LiteratureStatisticsImpl;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
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
        mod.addAbstractTypeMapping(PublicationDate.class, PublicationDateImpl.class);
        mod.addAbstractTypeMapping(Author.class, AuthorImpl.class);
        mod.addAbstractTypeMapping(Journal.class, JournalImpl.class);
        mod.addAbstractTypeMapping(UniProtKBAccession.class, UniProtKBAccessionImpl.class);
        mod.addAbstractTypeMapping(CrossReference.class, CrossReferenceImpl.class);

        mod.addAbstractTypeMapping(Submission.class, SubmissionImpl.class);
        mod.addAbstractTypeMapping(Journal.class, JournalImpl.class);
        mod.addAbstractTypeMapping(Unpublished.class, UnpublishedImpl.class);
        mod.addAbstractTypeMapping(Patent.class, PatentImpl.class);
        mod.addAbstractTypeMapping(Thesis.class, ThesisImpl.class);
        mod.addAbstractTypeMapping(Literature.class, LiteratureImpl.class);
        mod.addAbstractTypeMapping(Book.class, BookImpl.class);
        mod.addAbstractTypeMapping(JournalArticle.class, JournalArticleImpl.class);
        mod.addAbstractTypeMapping(Locator.class, ElectronicArticleImpl.LocatorImpl.class);

        mod.registerSubtypes(new NamedType(ElectronicArticleImpl.class, "ElectronicArticle"));
        mod.registerSubtypes(new NamedType(BookImpl.class, "Book"));
        mod.registerSubtypes(new NamedType(JournalArticleImpl.class, "JournalArticle"));
        mod.registerSubtypes(new NamedType(PatentImpl.class, "Patent"));
        mod.registerSubtypes(new NamedType(ThesisImpl.class, "Thesis"));
        mod.registerSubtypes(new NamedType(LiteratureImpl.class, "Literature"));
        mod.registerSubtypes(new NamedType(UnpublishedImpl.class, "Unpublished"));
        mod.registerSubtypes(new NamedType(SubmissionImpl.class, "Submission"));

        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();

        SimpleModule simpleMod = new SimpleModule();
        simpleMod.addSerializer(AuthorImpl.class, new AuthorSerializer());
        simpleMod.addSerializer(PublicationDateImpl.class, new PublicationDateSerializer());
        simpleMod.addSerializer(JournalImpl.class, new JournalSerializer());
        simpleMod.addSerializer(ElectronicArticleImpl.LocatorImpl.class, new LocatorSerializer());
        simpleMod.addSerializer(UniProtKBAccessionImpl.class, new UniProtKBAccessionSerializer());

        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }
}
