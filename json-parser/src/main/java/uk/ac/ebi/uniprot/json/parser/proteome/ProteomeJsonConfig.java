package uk.ac.ebi.uniprot.json.parser.proteome;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.Value;
import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation.Journal;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.Locator;
import uk.ac.ebi.uniprot.domain.citation.Patent;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.Thesis;
import uk.ac.ebi.uniprot.domain.citation.Unpublished;
import uk.ac.ebi.uniprot.domain.citation.impl.AuthorImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.BookImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.ElectronicArticleImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.JournalArticleImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.JournalImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.PatentImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.PublicationDateImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.SubmissionImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.ThesisImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.UnpublishedImpl;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.impl.DefaultDatabaseType;
import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.proteome.CanonicalProtein;
import uk.ac.ebi.uniprot.domain.proteome.Component;
import uk.ac.ebi.uniprot.domain.proteome.Protein;
import uk.ac.ebi.uniprot.domain.proteome.Proteome;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeId;
import uk.ac.ebi.uniprot.domain.proteome.RedundantProteome;
import uk.ac.ebi.uniprot.domain.proteome.impl.CanonicalProteinImpl;
import uk.ac.ebi.uniprot.domain.proteome.impl.ComponentImpl;
import uk.ac.ebi.uniprot.domain.proteome.impl.ProteinImpl;
import uk.ac.ebi.uniprot.domain.proteome.impl.ProteomeIdImpl;
import uk.ac.ebi.uniprot.domain.proteome.impl.ProteomeImpl;
import uk.ac.ebi.uniprot.domain.proteome.impl.RedundantProteomeImpl;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.APIsoformImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.InteractionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.FlagImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureDescriptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtAccessionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ValueImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.json.parser.CustomAnnotationIntrospector;
import uk.ac.ebi.uniprot.json.parser.JsonConfig;
import uk.ac.ebi.uniprot.json.parser.SimpleAnnotationIntrospector;
import uk.ac.ebi.uniprot.json.parser.uniprot.deserializer.LocalDateDeserializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.AuthorSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.ECNumberSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.EvidenceSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.FeatureDescriptionSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.FeatureIdSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.FlagSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.InteractorSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.IsoformIdImplSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.JournalSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.LocalDateSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.LocatorSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.PublicationDateSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.UniProtAccessionSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.UniProtIdSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.UniProtXDbTypeSerializer;

public class ProteomeJsonConfig implements JsonConfig {
	 private static ProteomeJsonConfig INSTANCE;

	    private final ObjectMapper objectMapper;
	    private final ObjectMapper prettyMapper;

	    private ProteomeJsonConfig(){
	        this.objectMapper = initObjectMapper();
	        this.prettyMapper = initPrettyObjectMapper();
	    }

	    public static synchronized ProteomeJsonConfig getInstance(){
	        if(INSTANCE == null){
	            INSTANCE = new ProteomeJsonConfig();
	        }
	        return INSTANCE;
	    }
	    
	    

	    private ObjectMapper initObjectMapper() {
	        ObjectMapper objMapper = new ObjectMapper();
	        objMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

	        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	        objMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
	        objMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
	        objMapper.setAnnotationIntrospector(new CustomAnnotationIntrospector());
	        SimpleModule mod = new SimpleModule();
	        mod.addSerializer(LocalDate.class, new LocalDateSerializer());
	        mod.addDeserializer(LocalDate.class, new LocalDateDeserializer());
	        mod.addAbstractTypeMapping(Proteome.class, ProteomeImpl.class);
	        mod.addAbstractTypeMapping(Component.class, ComponentImpl.class);
	        mod.addAbstractTypeMapping(ProteomeId.class, ProteomeIdImpl.class);
	        mod.addAbstractTypeMapping(Value.class, ValueImpl.class);
	        mod.addAbstractTypeMapping(RedundantProteome.class, RedundantProteomeImpl.class);
	        mod.addAbstractTypeMapping(DBCrossReference.class, DBCrossReferenceImpl.class);
	        
	        mod.addAbstractTypeMapping(DatabaseType.class, DefaultDatabaseType.class);
	        
	        mod.addAbstractTypeMapping(Protein.class, ProteinImpl.class);
	        mod.addAbstractTypeMapping(CanonicalProtein.class, CanonicalProteinImpl.class);
	        mod.addAbstractTypeMapping(UniProtAccession.class, UniProtAccessionImpl.class);
	        
	        
	        mod.addAbstractTypeMapping(PublicationDate.class, PublicationDateImpl.class);
	        mod.addAbstractTypeMapping(Locator.class, ElectronicArticleImpl.LocatorImpl.class);
	        mod.addAbstractTypeMapping(ElectronicArticle.class,ElectronicArticleImpl.class);
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
	        mod.registerSubtypes(new NamedType(JournalArticleImpl.class,"JournalArticle"));
	        mod.registerSubtypes(new NamedType(PatentImpl.class, "Patent"));
	        mod.registerSubtypes(new NamedType(SubmissionImpl.class, "Submission"));
	        mod.registerSubtypes(new NamedType(ThesisImpl.class, "Thesis"));
	        mod.registerSubtypes(new NamedType(UnpublishedImpl.class, "Unpublished"));


	        objMapper.registerModule(mod);
	        
	        return objMapper;
	    }
	    
	    private ObjectMapper initPrettyObjectMapper() {
	        ObjectMapper prettyObjMapper = new ObjectMapper();
	        prettyObjMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

	        prettyObjMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	        prettyObjMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
	        prettyObjMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
	        prettyObjMapper.setAnnotationIntrospector(new SimpleAnnotationIntrospector());

	        SimpleModule simpleMod = new SimpleModule();
	        simpleMod.addSerializer(LocalDate.class, new LocalDateSerializer());
	        simpleMod.addSerializer(UniProtAccessionImpl.class, new UniProtAccessionSerializer());
	        simpleMod.addSerializer(AuthorImpl.class, new AuthorSerializer());
	        simpleMod.addSerializer(PublicationDateImpl.class,new PublicationDateSerializer());
	        simpleMod.addSerializer(ElectronicArticleImpl.LocatorImpl.class,new LocatorSerializer());
	        simpleMod.addSerializer(JournalImpl.class,new JournalSerializer());
	        return prettyObjMapper;
	    }

	
	    @Override
	    public ObjectMapper getPrettyObjectMapper() {
	        return this.prettyMapper;
	    }

	    @Override
	    public ObjectMapper getObjectMapper() {
	        return this.objectMapper;
	    }


}
