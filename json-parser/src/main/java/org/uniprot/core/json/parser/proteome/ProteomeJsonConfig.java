package org.uniprot.core.json.parser.proteome;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.uniprot.core.CrossReference;
import org.uniprot.core.Database;
import org.uniprot.core.Value;
import org.uniprot.core.citation.*;
import org.uniprot.core.citation.impl.*;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.impl.DefaultDatabase;
import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.deserializer.LocalDateDeserializer;
import org.uniprot.core.json.parser.serializer.*;
import org.uniprot.core.json.parser.uniprot.serializer.UniProtKBAccessionSerializer;
import org.uniprot.core.proteome.*;
import org.uniprot.core.proteome.impl.*;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageImpl;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionImpl;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyImpl;

import java.time.LocalDate;

public class ProteomeJsonConfig extends JsonConfig {
    private static ProteomeJsonConfig instance;

    private final ObjectMapper objectMapper;
    private final ObjectMapper prettyMapper;

    private ProteomeJsonConfig() {
        this.objectMapper = initObjectMapper();
        this.prettyMapper = initPrettyObjectMapper();
    }

    public static synchronized ProteomeJsonConfig getInstance() {
        if (instance == null) {
            instance = new ProteomeJsonConfig();
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
        mod.addSerializer(LocalDate.class, new LocalDateSerializer());
        mod.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        mod.addAbstractTypeMapping(ProteomeEntry.class, ProteomeEntryImpl.class);
        mod.addAbstractTypeMapping(Component.class, ComponentImpl.class);
        mod.addAbstractTypeMapping(ProteomeId.class, ProteomeIdImpl.class);
        mod.addAbstractTypeMapping(Taxonomy.class, TaxonomyImpl.class);
        mod.addAbstractTypeMapping(TaxonomyLineage.class, TaxonomyLineageImpl.class);
        mod.addAbstractTypeMapping(ProteomeStatistics.class, ProteomeStatisticsImpl.class);

        mod.addAbstractTypeMapping(Value.class, ValueImpl.class);
        mod.addAbstractTypeMapping(RedundantProteome.class, RedundantProteomeImpl.class);
        mod.addAbstractTypeMapping(CrossReference.class, CrossReferenceImpl.class);
        mod.addAbstractTypeMapping(Database.class, DefaultDatabase.class);

        mod.addAbstractTypeMapping(GenomeAnnotation.class, GenomeAnnotationImpl.class);
        mod.addAbstractTypeMapping(UniProtKBAccession.class, UniProtKBAccessionImpl.class);

        mod.addAbstractTypeMapping(
                ProteomeCompletenessReport.class, ProteomeCompletenessReportImpl.class);
        mod.addAbstractTypeMapping(BuscoReport.class, BuscoReportImpl.class);
        mod.addAbstractTypeMapping(CPDReport.class, CPDReportImpl.class);
        mod.addAbstractTypeMapping(GenomeAssembly.class, GenomeAssemblyImpl.class);

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
        simpleMod.addSerializer(UniProtKBAccessionImpl.class, new UniProtKBAccessionSerializer());
        simpleMod.addSerializer(AuthorImpl.class, new AuthorSerializer());
        simpleMod.addSerializer(PublicationDateImpl.class, new PublicationDateSerializer());
        simpleMod.addSerializer(ElectronicArticleImpl.LocatorImpl.class, new LocatorSerializer());
        simpleMod.addSerializer(JournalImpl.class, new JournalSerializer());

        simpleMod.addSerializer(ProteomeIdImpl.class, new ProteomeIdSerializer());
        simpleMod.addSerializer(BuscoReportImpl.class, new BuscoReportSerializer());

        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }
}
