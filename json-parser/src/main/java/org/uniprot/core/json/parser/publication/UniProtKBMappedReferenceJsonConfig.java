package org.uniprot.core.json.parser.publication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Book;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Journal;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.Literature;
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
import org.uniprot.core.citation.impl.LiteratureImpl;
import org.uniprot.core.citation.impl.PatentImpl;
import org.uniprot.core.citation.impl.PublicationDateImpl;
import org.uniprot.core.citation.impl.SubmissionImpl;
import org.uniprot.core.citation.impl.ThesisImpl;
import org.uniprot.core.citation.impl.UnpublishedImpl;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.serializer.AuthorSerializer;
import org.uniprot.core.json.parser.serializer.JournalSerializer;
import org.uniprot.core.json.parser.serializer.PublicationDateSerializer;
import org.uniprot.core.publication.CommunityAnnotation;
import org.uniprot.core.publication.CommunityMappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.publication.UniProtKBMappedReference;
import org.uniprot.core.publication.impl.CommunityAnnotationImpl;
import org.uniprot.core.publication.impl.CommunityMappedReferenceImpl;
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

public class UniProtKBMappedReferenceJsonConfig extends JsonConfig {
    private static UniProtKBMappedReferenceJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper
            simpleMapper; // mapper without unwanted fields like type, lighter version

    private UniProtKBMappedReferenceJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized UniProtKBMappedReferenceJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniProtKBMappedReferenceJsonConfig();
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
        ObjectMapper simpleObjMapper = getDefaultSimpleObjectMapper();
        return simpleObjMapper;
    }
}
