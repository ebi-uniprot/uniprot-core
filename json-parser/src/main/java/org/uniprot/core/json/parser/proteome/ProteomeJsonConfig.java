package org.uniprot.core.json.parser.proteome;

import java.time.LocalDate;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.DatabaseType;
import org.uniprot.core.Value;
import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Book;
import org.uniprot.core.citation.ElectronicArticle;
import org.uniprot.core.citation.Journal;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.Locator;
import org.uniprot.core.citation.Patent;
import org.uniprot.core.citation.PublicationDate;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.Thesis;
import org.uniprot.core.citation.Unpublished;
import org.uniprot.core.citation.impl.AuthorImpl;
import org.uniprot.core.citation.impl.BookImpl;
import org.uniprot.core.citation.impl.ElectronicArticleImpl;
import org.uniprot.core.citation.impl.JournalArticleImpl;
import org.uniprot.core.citation.impl.JournalImpl;
import org.uniprot.core.citation.impl.PatentImpl;
import org.uniprot.core.citation.impl.PublicationDateImpl;
import org.uniprot.core.citation.impl.SubmissionImpl;
import org.uniprot.core.citation.impl.ThesisImpl;
import org.uniprot.core.citation.impl.UnpublishedImpl;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.impl.DefaultDatabaseType;
import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.deserializer.LocalDateDeserializer;
import org.uniprot.core.json.parser.serializer.AuthorSerializer;
import org.uniprot.core.json.parser.serializer.JournalSerializer;
import org.uniprot.core.json.parser.serializer.LocalDateSerializer;
import org.uniprot.core.json.parser.serializer.LocatorSerializer;
import org.uniprot.core.json.parser.serializer.PublicationDateSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.UniProtAccessionSerializer;
import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.proteome.ProteomeEntry;
import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.RedundantProteome;
import org.uniprot.core.proteome.impl.CanonicalProteinImpl;
import org.uniprot.core.proteome.impl.ComponentImpl;
import org.uniprot.core.proteome.impl.ProteinImpl;
import org.uniprot.core.proteome.impl.ProteomeEntryImpl;
import org.uniprot.core.proteome.impl.ProteomeIdImpl;
import org.uniprot.core.proteome.impl.RedundantProteomeImpl;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageImpl;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.impl.TaxonomyImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ProteomeJsonConfig extends JsonConfig {
    private static ProteomeJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper prettyMapper;

    private ProteomeJsonConfig() {
        this.objectMapper = initObjectMapper();
        this.prettyMapper = initPrettyObjectMapper();
    }

    public static synchronized ProteomeJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProteomeJsonConfig();
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
        mod.addAbstractTypeMapping(ProteomeEntry.class, ProteomeEntryImpl.class);
        mod.addAbstractTypeMapping(Component.class, ComponentImpl.class);
        mod.addAbstractTypeMapping(ProteomeId.class, ProteomeIdImpl.class);
        mod.addAbstractTypeMapping(Taxonomy.class, TaxonomyImpl.class);
        mod.addAbstractTypeMapping(TaxonomyLineage.class, TaxonomyLineageImpl.class);
        
        mod.addAbstractTypeMapping(Value.class, ValueImpl.class);
        mod.addAbstractTypeMapping(RedundantProteome.class, RedundantProteomeImpl.class);
        mod.addAbstractTypeMapping(DBCrossReference.class, DBCrossReferenceImpl.class);
        mod.addAbstractTypeMapping(DatabaseType.class, DefaultDatabaseType.class);

        mod.addAbstractTypeMapping(Protein.class, ProteinImpl.class);
        mod.addAbstractTypeMapping(CanonicalProtein.class, CanonicalProteinImpl.class);
        mod.addAbstractTypeMapping(UniProtAccession.class, UniProtAccessionImpl.class);


        mod.addAbstractTypeMapping(PublicationDate.class, PublicationDateImpl.class);
        mod.addAbstractTypeMapping(Locator.class, ElectronicArticleImpl.LocatorImpl.class);
        mod.addAbstractTypeMapping(ElectronicArticle.class, ElectronicArticleImpl.class);
        mod.addAbstractTypeMapping(Submission.class, SubmissionImpl.class);
        mod.addAbstractTypeMapping(Journal.class, JournalImpl.class);
        mod.addAbstractTypeMapping(Patent.class, PatentImpl.class);
        mod.addAbstractTypeMapping(Author.class, AuthorImpl.class);
        mod.addAbstractTypeMapping(Unpublished.class, UnpublishedImpl.class);
        mod.addAbstractTypeMapping(Thesis.class, ThesisImpl.class);
        mod.addAbstractTypeMapping(Book.class, BookImpl.class);
        mod.addAbstractTypeMapping(JournalArticle.class, JournalArticleImpl.class);
        mod.registerSubtypes(new NamedType(BookImpl.class, "Book"));
        mod.registerSubtypes(new NamedType(ElectronicArticleImpl.class, "ElectronicArticle"));
        mod.registerSubtypes(new NamedType(JournalArticleImpl.class, "JournalArticle"));
        mod.registerSubtypes(new NamedType(PatentImpl.class, "Patent"));
        mod.registerSubtypes(new NamedType(SubmissionImpl.class, "Submission"));
        mod.registerSubtypes(new NamedType(ThesisImpl.class, "Thesis"));
        mod.registerSubtypes(new NamedType(UnpublishedImpl.class, "Unpublished"));


        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initPrettyObjectMapper() {

        ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();

        SimpleModule simpleMod = new SimpleModule();
        simpleMod.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleMod.addSerializer(UniProtAccessionImpl.class, new UniProtAccessionSerializer());
        simpleMod.addSerializer(AuthorImpl.class, new AuthorSerializer());
        simpleMod.addSerializer(PublicationDateImpl.class, new PublicationDateSerializer());
        simpleMod.addSerializer(ElectronicArticleImpl.LocatorImpl.class, new LocatorSerializer());
        simpleMod.addSerializer(JournalImpl.class, new JournalSerializer());
        
        simpleMod.addSerializer(ProteomeIdImpl.class, new ProteomeIdSerializer());
        
        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }
}