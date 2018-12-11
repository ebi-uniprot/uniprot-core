package uk.ac.ebi.uniprot.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.ac.ebi.uniprot.domain.citation.*;
import uk.ac.ebi.uniprot.domain.citation.impl.*;
import uk.ac.ebi.uniprot.domain.gene.*;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.impl.SequenceImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.*;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.*;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.*;
import uk.ac.ebi.uniprot.domain.uniprot.description.*;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.*;
import uk.ac.ebi.uniprot.domain.uniprot.feature.*;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureDescriptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.*;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferencesImpl;
import uk.ac.ebi.uniprot.domain.util.json.LocalDateDeserializer;
import uk.ac.ebi.uniprot.domain.util.json.LocalDateSerializer;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestHelper {
	public static  <T> void verifyJson(T obj  ) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
		objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

		SimpleModule mod = new SimpleModule();
		mod.addSerializer(LocalDate.class, new LocalDateSerializer());
		mod.addDeserializer(LocalDate.class, new LocalDateDeserializer());


		mod.addAbstractTypeMapping(UniProtEntry.class, UniProtEntryImpl.class);

		mod.addAbstractTypeMapping(UniProtAccession.class, UniProtAccessionImpl.class);
		mod.addAbstractTypeMapping(UniProtId.class,UniProtIdImpl.class);
		mod.addAbstractTypeMapping(Value.class, ValueImpl.class);
		mod.addAbstractTypeMapping(EntryAudit.class,EntryAuditImpl.class);

		mod.addAbstractTypeMapping(UniProtTaxonId.class,UniProtTaxonIdImpl.class);
		mod.addAbstractTypeMapping(TaxonNode.class, TaxonNodeImpl.class);
		mod.addAbstractTypeMapping(Taxon.class, TaxonImpl.class);
		mod.addAbstractTypeMapping(TaxonName.class, TaxonNameImpl.class);

		mod.addAbstractTypeMapping(Organism.class, OrganismImpl.class);
		mod.addAbstractTypeMapping(OrganismName.class, OrganismNameImpl.class);

		mod.addAbstractTypeMapping(ProteinDescription.class, ProteinDescriptionImpl.class);
		mod.addAbstractTypeMapping(ProteinName.class, ProteinNameImpl.class);
		mod.addAbstractTypeMapping(ProteinSection.class,ProteinSectionImpl.class);
		mod.addAbstractTypeMapping(Name.class, NameImpl.class);
		mod.addAbstractTypeMapping(EC.class, ECImpl.class);
		mod.addAbstractTypeMapping(ECNumber.class, ECNumberImpl.class);
		mod.addAbstractTypeMapping(Flag.class, FlagImpl.class);

		mod.addAbstractTypeMapping(Gene.class, GeneImpl.class);
		mod.addAbstractTypeMapping(GeneName.class, GeneImpl.GeneNameImpl.class);
		mod.addAbstractTypeMapping(GeneNameSynonym.class, GeneImpl.GeneNameSynonymImpl.class);
		mod.addAbstractTypeMapping(OrderedLocusName.class, GeneImpl.OrderedLocusNameImpl.class);
		mod.addAbstractTypeMapping(ORFName.class, GeneImpl.ORFNameImpl.class);
		mod.addAbstractTypeMapping(EvidencedValue.class, EvidencedValueImpl.class);


		mod.addAbstractTypeMapping(Comments.class, CommentsImpl.class);
		mod.addAbstractTypeMapping(AlternativeProductsComment.class, AlternativeProductsCommentImpl.class);
		mod.addAbstractTypeMapping(APIsoform.class, APIsoformImpl.class);
		mod.addAbstractTypeMapping(IsoformName.class, APIsoformImpl.IsoformNameImpl.class);
		mod.addAbstractTypeMapping(IsoformId.class, APIsoformImpl.IsoformIdImpl.class);
		mod.addAbstractTypeMapping(FreeText.class,FreeTextImpl.class);
		mod.addAbstractTypeMapping(PhysiologicalReaction.class, PhysiologicalReactionImpl.class);
		mod.addAbstractTypeMapping(DiseaseDescription.class, DiseaseImpl.DiseaseDescriptionImpl.class);
		mod.addAbstractTypeMapping(Absorption.class, AbsorptionImpl.class);
		mod.addAbstractTypeMapping(SequenceCautionComment.class, SequenceCautionCommentImpl.class);
		mod.addAbstractTypeMapping(MassSpectrometryComment.class, MassSpectrometryCommentImpl.class);
		mod.addAbstractTypeMapping(RedoxPotential.class, BPCPCommentImpl.RedoxPotentialImpl.class);
		mod.addAbstractTypeMapping(RnaEdPosition.class, RnaEditingCommentImpl.RnaEdPositionImpl.class);
		mod.addAbstractTypeMapping(RnaEditingComment.class, RnaEditingCommentImpl.class);
		mod.addAbstractTypeMapping(MichaelisConstant.class, MichaelisConstantImpl.class);
		mod.addAbstractTypeMapping(MassSpectrometryRange.class, MassSpectrometryRangeImpl.class);
		mod.addAbstractTypeMapping(KineticParameters.class, KineticParametersImpl.class);
		mod.addAbstractTypeMapping(SubcellularLocation.class, SubcellularLocationImpl.class);
		mod.addAbstractTypeMapping(TemperatureDependence.class, BPCPCommentImpl.TemperatureDependenceImpl.class);
		mod.addAbstractTypeMapping(Interactor.class, InteractionImpl.InteractorImpl.class);
		mod.addAbstractTypeMapping(DiseaseComment.class, DiseaseCommentImpl.class);
		mod.addAbstractTypeMapping(Cofactor.class, CofactorImpl.class);
		mod.addAbstractTypeMapping(SubcellularLocationValue.class, SubcellularLocationImpl.SubcellularLocationValueImpl.class);
		mod.addAbstractTypeMapping(Disease.class, DiseaseImpl.class);
		mod.addAbstractTypeMapping(InteractionComment.class, InteractionCommentImpl.class);
		mod.addAbstractTypeMapping(MaximumVelocity.class, MaximumVelocityImpl.class);
		mod.addAbstractTypeMapping(BPCPComment.class, BPCPCommentImpl.class);
		mod.addAbstractTypeMapping(CatalyticActivityComment.class, CatalyticActivityCommentImpl.class);
		mod.addAbstractTypeMapping(CofactorComment.class, CofactorCommentImpl.class);
		mod.addAbstractTypeMapping(Reaction.class, ReactionImpl.class);
		mod.addAbstractTypeMapping(FreeTextComment.class, FreeTextCommentImpl.class);
		mod.addAbstractTypeMapping(Interaction.class, InteractionImpl.class);
		mod.addAbstractTypeMapping(WebResourceComment.class, WebResourceCommentImpl.class);
		mod.addAbstractTypeMapping(Note.class, NoteImpl.class);
		mod.addAbstractTypeMapping(PhDependence.class, BPCPCommentImpl.PhDependenceImpl.class);
		mod.addAbstractTypeMapping(SubcellularLocationComment.class, SubcellularLocationCommentImpl.class);


		mod.addAbstractTypeMapping(ReferenceComment.class,ReferenceCommentImpl.class);
		mod.addAbstractTypeMapping(PublicationDate.class, PublicationDateImpl.class);
		mod.addAbstractTypeMapping(Locator.class, ElectronicArticleImpl.LocatorImpl.class);
		mod.addAbstractTypeMapping(ElectronicArticle.class,ElectronicArticleImpl.class);


		mod.addAbstractTypeMapping(CitationXrefs.class, CitationXrefsImpl.class);
		mod.addAbstractTypeMapping(Submission.class, SubmissionImpl.class);
		mod.addAbstractTypeMapping(Journal.class, JournalImpl.class);
		mod.addAbstractTypeMapping(Patent.class, PatentImpl.class);
		mod.addAbstractTypeMapping(Author.class, AuthorImpl.class);
		mod.addAbstractTypeMapping(Unpublished.class, UnpublishedImpl.class);
		mod.addAbstractTypeMapping(Thesis.class, ThesisImpl.class);
		mod.addAbstractTypeMapping(Book.class, BookImpl.class);
		mod.addAbstractTypeMapping(JournalArticle.class, JournalArticleImpl.class);

		mod.addAbstractTypeMapping(UniProtReference.class, UniProtReferenceImpl.class);
		mod.addAbstractTypeMapping(InternalLine.class, InternalLineImpl.class);
		mod.addAbstractTypeMapping(UniProtReferences.class, UniProtReferencesImpl.class);
		mod.addAbstractTypeMapping(EvidenceLine.class, EvidenceLineImpl.class);
		mod.addAbstractTypeMapping(InternalSection.class, InternalSectionImpl.class);
		mod.addAbstractTypeMapping(Keyword.class, KeywordImpl.class);
		mod.addAbstractTypeMapping(EntryInactiveReason.class, EntryInactiveReasonImpl.class);
		mod.addAbstractTypeMapping(Organelle.class, OrganelleImpl.class);
		mod.addAbstractTypeMapping(SourceLine.class, SourceLineImpl.class);
		mod.addAbstractTypeMapping(Sequence.class, SequenceImpl.class);

		mod.addAbstractTypeMapping(DBCrossReference.class, DBCrossReferenceImpl.class);
		mod.addAbstractTypeMapping(UniProtDBCrossReference.class, UniProtDBCrossReferenceImpl.class);
		mod.addAbstractTypeMapping(UniProtDBCrossReferences.class, UniProtDBCrossReferencesImpl.class);

		mod.addAbstractTypeMapping(AlternativeSequence.class, AlternativeSequenceImpl.class);
		mod.addAbstractTypeMapping(SequenceReport.class, AlternativeSequenceImpl.SequenceReportImpl.class);

		mod.addAbstractTypeMapping(FeatureId.class, FeatureIdImpl.class);
		mod.addAbstractTypeMapping(FeatureDescription.class, FeatureDescriptionImpl.class);
		mod.addAbstractTypeMapping(Feature.class, FeatureImpl.class);
/*

./citation/Citation.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.BookImpl.class, name = "BookImpl"),
./citation/Citation.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.ElectronicArticleImpl.class, name = "ElectronicArticleImpl"),
./citation/Citation.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.JournalArticleImpl.class, name = "JournalArticleImpl"),
./citation/Citation.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.PatentImpl.class, name = "PatentImpl"),
./citation/Citation.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.SubmissionImpl.class, name = "SubmissionImpl"),
./citation/Citation.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.ThesisImpl.class, name = "ThesisImpl"),
./citation/Citation.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.citation.impl.UnpublishedImpl.class, name = "UnpublishedImpl")

./DatabaseType.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.impl.DefaultDatabaseType.class, name = "DefaultDatabaseType"),
./DatabaseType.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType.class, name = "EvidenceType"),
./DatabaseType.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType.class, name = "UniProtXDbType"),
./DatabaseType.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureXDbType.class, name = "FeatureXDbType"),
./DatabaseType.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType.class, name = "CofactorReferenceType"),
./DatabaseType.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType.class, name = "ReactionReferenceType")

./uniprot/comment/Comment.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.AlternativeProductsCommentImpl.class, name = "APComment"),
./uniprot/comment/Comment.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.BPCPCommentImpl.class, name = "BPCPComment"),
./uniprot/comment/Comment.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CatalyticActivityCommentImpl.class, name = "CatalyticActivityComment"),
./uniprot/comment/Comment.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CofactorCommentImpl.class, name = "CofactorComment"),
./uniprot/comment/Comment.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.DiseaseCommentImpl.class, name = "DiseaseComment"),
./uniprot/comment/Comment.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.FreeTextCommentImpl.class, name = "FreeTextComment"),
./uniprot/comment/Comment.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.InteractionCommentImpl.class, name = "InteractionComment"),
./uniprot/comment/Comment.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MassSpectrometryCommentImpl.class, name = "MassSpectrometryComment"),
./uniprot/comment/Comment.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.RnaEditingCommentImpl.class, name = "RnaEditingComment"),
./uniprot/comment/Comment.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SequenceCautionCommentImpl.class, name = "SequenceCautionComment"),
./uniprot/comment/Comment.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SubcellularLocationCommentImpl.class, name = "SubcellularLocationComment"),
./uniprot/comment/Comment.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.WebResourceCommentImpl.class, name = "WebResourceComment")


./uniprot/feature/SequenceReport.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl.SequenceReportImpl.class, name = "SequenceReportImpl")
./uniprot/feature/AlternativeSequence.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl.class, name = "AlternativeSequenceImpl")
./uniprot/feature/FeatureId.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureIdImpl.class, name = "FeatureIdImpl")
./uniprot/feature/FeatureDescription.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureDescriptionImpl.class, name = "FeatureDescriptionImpl")
./uniprot/feature/Feature.java:  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.feature.impl.FeatureImpl.class, name = "FeatureImpl")

*/
		
		
		
		
		objectMapper.registerModule(mod);

		try {
			String json =objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			System.out.println(json);
			T converted =  objectMapper.readValue(json,(Class<T>) obj.getClass());
			assertEquals(obj, converted);
			}catch(Exception e) {
	    		fail(e.getMessage());
	    	}
	}
}
