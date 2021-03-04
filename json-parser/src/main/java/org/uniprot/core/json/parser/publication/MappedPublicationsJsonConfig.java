package org.uniprot.core.json.parser.publication;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.citation.impl.*;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.serializer.*;
import org.uniprot.core.json.parser.uniprot.serializer.*;
import org.uniprot.core.publication.*;
import org.uniprot.core.publication.impl.*;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class MappedPublicationsJsonConfig extends JsonConfig {
    private static MappedPublicationsJsonConfig instance;

    private final ObjectMapper objectMapper;
    private final ObjectMapper
            simpleMapper; // mapper without unwanted fields like type, lighter version

    private MappedPublicationsJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized MappedPublicationsJsonConfig getInstance() {
        if (instance == null) {
            instance = new MappedPublicationsJsonConfig();
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
        mod.addAbstractTypeMapping(MappedPublications.class, MappedPublicationsImpl.class);
        mod.addAbstractTypeMapping(
                CommunityMappedReference.class, CommunityMappedReferenceImpl.class);
        mod.addAbstractTypeMapping(CommunityAnnotation.class, CommunityAnnotationImpl.class);
        mod.addAbstractTypeMapping(
                ComputationallyMappedReference.class, ComputationallyMappedReferenceImpl.class);
        mod.addAbstractTypeMapping(
                UniProtKBMappedReference.class, UniProtKBMappedReferenceImpl.class);
        mod.addAbstractTypeMapping(MappedSource.class, MappedSourceImpl.class);
        mod.addAbstractTypeMapping(ReferenceComment.class, ReferenceCommentImpl.class);
        mod.addAbstractTypeMapping(EvidencedValue.class, EvidencedValueImpl.class);
        mod.addAbstractTypeMapping(Evidence.class, EvidenceImpl.class);

        mod.addAbstractTypeMapping(
                UniProtKBCrossReference.class, UniProtKBCrossReferenceImpl.class);
        mod.addAbstractTypeMapping(UniProtKBDatabase.class, UniProtKBDatabaseImpl.class);

        mod.addAbstractTypeMapping(PublicationDate.class, PublicationDateImpl.class);
        mod.addAbstractTypeMapping(Author.class, AuthorImpl.class);
        mod.addAbstractTypeMapping(UniProtKBAccession.class, UniProtKBAccessionImpl.class);
        mod.addAbstractTypeMapping(CrossReference.class, CrossReferenceImpl.class);

        mod.addAbstractTypeMapping(ElectronicArticle.class, ElectronicArticleImpl.class);
        mod.addAbstractTypeMapping(Submission.class, SubmissionImpl.class);
        mod.addAbstractTypeMapping(Journal.class, JournalImpl.class);
        mod.addAbstractTypeMapping(Unpublished.class, UnpublishedImpl.class);
        mod.addAbstractTypeMapping(Patent.class, PatentImpl.class);
        mod.addAbstractTypeMapping(Thesis.class, ThesisImpl.class);
        mod.addAbstractTypeMapping(Literature.class, LiteratureImpl.class);
        mod.addAbstractTypeMapping(Book.class, BookImpl.class);
        mod.addAbstractTypeMapping(JournalArticle.class, JournalArticleImpl.class);

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
        SimpleModule prettyWriterModule = new SimpleModule();
        prettyWriterModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        prettyWriterModule.addSerializer(AuthorImpl.class, new AuthorSerializer());
        prettyWriterModule.addSerializer(EvidenceImpl.class, new EvidenceSerializer());
        prettyWriterModule.addSerializer(
                PublicationDateImpl.class, new PublicationDateSerializer());
        prettyWriterModule.addSerializer(
                ElectronicArticleImpl.LocatorImpl.class, new LocatorSerializer());
        prettyWriterModule.addSerializer(JournalImpl.class, new JournalSerializer());
        prettyWriterModule.addSerializer(UniProtKBDatabase.class, new UniProtDatabaseSerializer());

        Set<Class<?>> ignoredTypes = new HashSet<>();
        ignoredTypes.add(UniProtKBAccession.class);
        ObjectMapper mapper = getSimpleObjectMapperWithIgnoredTypes(ignoredTypes);

        mapper.registerModule(prettyWriterModule);

        return mapper;
    }
}
