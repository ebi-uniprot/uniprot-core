package uk.ac.ebi.uniprot.json.parser.proteome;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.Value;
import uk.ac.ebi.uniprot.domain.citation.*;
import uk.ac.ebi.uniprot.domain.citation.impl.*;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.impl.DefaultDatabaseType;
import uk.ac.ebi.uniprot.domain.impl.ValueImpl;
import uk.ac.ebi.uniprot.domain.proteome.*;
import uk.ac.ebi.uniprot.domain.proteome.impl.*;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyLineage;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonomyLineageImpl;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtAccessionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.impl.TaxonomyImpl;
import uk.ac.ebi.uniprot.json.parser.JsonConfig;
import uk.ac.ebi.uniprot.json.parser.deserializer.LocalDateDeserializer;
import uk.ac.ebi.uniprot.json.parser.serializer.*;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.UniProtAccessionSerializer;

import java.time.LocalDate;

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
        return prettyObjMapper;
    }
}
