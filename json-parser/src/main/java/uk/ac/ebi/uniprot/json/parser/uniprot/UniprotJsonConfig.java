package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;

import uk.ac.ebi.uniprot.json.parser.JsonConfig;
import uk.ac.ebi.uniprot.json.parser.deserializer.LocalDateDeserializer;

import java.time.LocalDate;

import org.uniprot.core.*;
import org.uniprot.core.citation.*;
import org.uniprot.core.citation.impl.*;
import org.uniprot.core.gene.*;
import org.uniprot.core.impl.*;
import org.uniprot.core.uniprot.*;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.impl.*;
import org.uniprot.core.uniprot.description.*;
import org.uniprot.core.uniprot.description.impl.*;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceLine;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.impl.EvidenceImpl;
import org.uniprot.core.uniprot.evidence.impl.EvidenceLineImpl;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;
import org.uniprot.core.uniprot.feature.AlternativeSequence;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureDescription;
import org.uniprot.core.uniprot.feature.FeatureId;
import org.uniprot.core.uniprot.feature.impl.AlternativeSequenceImpl;
import org.uniprot.core.uniprot.feature.impl.FeatureDescriptionImpl;
import org.uniprot.core.uniprot.feature.impl.FeatureIdImpl;
import org.uniprot.core.uniprot.feature.impl.FeatureImpl;
import org.uniprot.core.uniprot.impl.*;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;
import org.uniprot.core.uniprot.taxonomy.impl.OrganismHostImpl;
import org.uniprot.core.uniprot.taxonomy.impl.OrganismImpl;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtXDbType;
import org.uniprot.core.uniprot.xdb.impl.UniProtDBCrossReferenceImpl;

import uk.ac.ebi.uniprot.json.parser.serializer.AuthorSerializer;
import uk.ac.ebi.uniprot.json.parser.serializer.JournalSerializer;
import uk.ac.ebi.uniprot.json.parser.serializer.LocalDateSerializer;
import uk.ac.ebi.uniprot.json.parser.serializer.LocatorSerializer;
import uk.ac.ebi.uniprot.json.parser.serializer.PublicationDateSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.ECNumberSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.EvidenceSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.FeatureDescriptionSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.FeatureIdSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.FlagSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.InteractorSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.IsoformIdImplSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.UniProtAccessionSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.UniProtIdSerializer;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.UniProtXDbTypeSerializer;

/**
 * @author lgonzales
 */
public class UniprotJsonConfig extends JsonConfig {

    private static UniprotJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper prettyMapper;

    private UniprotJsonConfig() {
        this.objectMapper = initObjectMapper();
        this.prettyMapper = initPrettyObjectMapper();
    }

    public static synchronized UniprotJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniprotJsonConfig();
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
        ObjectMapper objMapper = getDefaultFullObjectMapper();

        SimpleModule mod = new SimpleModule();
        mod.addSerializer(LocalDate.class, new LocalDateSerializer());
        mod.addDeserializer(LocalDate.class, new LocalDateDeserializer());

        mod.addAbstractTypeMapping(UniProtEntry.class, UniProtEntryImpl.class);

        mod.addAbstractTypeMapping(UniProtAccession.class, UniProtAccessionImpl.class);
        mod.addAbstractTypeMapping(UniProtId.class, UniProtIdImpl.class);
        mod.addAbstractTypeMapping(Value.class, ValueImpl.class);
        mod.addAbstractTypeMapping(EntryAudit.class, EntryAuditImpl.class);

        mod.addAbstractTypeMapping(Organism.class, OrganismImpl.class);
        mod.addAbstractTypeMapping(OrganismHost.class, OrganismHostImpl.class);

        mod.addAbstractTypeMapping(ProteinDescription.class, ProteinDescriptionImpl.class);
        mod.addAbstractTypeMapping(ProteinRecName.class, ProteinRecNameImpl.class);
        mod.addAbstractTypeMapping(ProteinSubName.class, ProteinSubNameImpl.class);
        mod.addAbstractTypeMapping(ProteinAltName.class, ProteinAltNameImpl.class);
        mod.addAbstractTypeMapping(ProteinSection.class, ProteinSectionImpl.class);
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
        mod.addAbstractTypeMapping(Evidence.class, EvidenceImpl.class);

        mod.addAbstractTypeMapping(AlternativeProductsComment.class, AlternativeProductsCommentImpl.class);
        mod.addAbstractTypeMapping(APIsoform.class, APIsoformImpl.class);
        mod.addAbstractTypeMapping(IsoformName.class, APIsoformImpl.IsoformNameImpl.class);
        mod.addAbstractTypeMapping(IsoformId.class, APIsoformImpl.IsoformIdImpl.class);
        mod.addAbstractTypeMapping(FreeText.class, FreeTextImpl.class);
        mod.addAbstractTypeMapping(PhysiologicalReaction.class, PhysiologicalReactionImpl.class);
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

        mod.addAbstractTypeMapping(ReferenceComment.class, ReferenceCommentImpl.class);
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

        mod.addAbstractTypeMapping(UniProtReference.class, UniProtReferenceImpl.class);
        mod.addAbstractTypeMapping(InternalLine.class, InternalLineImpl.class);
        mod.addAbstractTypeMapping(EvidenceLine.class, EvidenceLineImpl.class);
        mod.addAbstractTypeMapping(InternalSection.class, InternalSectionImpl.class);
        mod.addAbstractTypeMapping(Keyword.class, KeywordImpl.class);
        mod.addAbstractTypeMapping(EntryInactiveReason.class, EntryInactiveReasonImpl.class);
        mod.addAbstractTypeMapping(GeneLocation.class, GeneLocationImpl.class);
        mod.addAbstractTypeMapping(SourceLine.class, SourceLineImpl.class);
        mod.addAbstractTypeMapping(Sequence.class, SequenceImpl.class);

        mod.addAbstractTypeMapping(DBCrossReference.class, DBCrossReferenceImpl.class);
        mod.addAbstractTypeMapping(UniProtDBCrossReference.class, UniProtDBCrossReferenceImpl.class);

        mod.addAbstractTypeMapping(AlternativeSequence.class, AlternativeSequenceImpl.class);

        mod.addAbstractTypeMapping(FeatureId.class, FeatureIdImpl.class);
        mod.addAbstractTypeMapping(FeatureDescription.class, FeatureDescriptionImpl.class);
        mod.addAbstractTypeMapping(Feature.class, FeatureImpl.class);

        mod.addAbstractTypeMapping(DatabaseType.class, DefaultDatabaseType.class);

        mod.registerSubtypes(new NamedType(AlternativeProductsCommentImpl.class, "AP"));
        mod.registerSubtypes(new NamedType(BPCPCommentImpl.class, "BPCP"));
        mod.registerSubtypes(new NamedType(CatalyticActivityCommentImpl.class, "CatalyticActivity"));
        mod.registerSubtypes(new NamedType(CofactorCommentImpl.class, "Cofactor"));
        mod.registerSubtypes(new NamedType(DiseaseCommentImpl.class, "Disease"));
        mod.registerSubtypes(new NamedType(FreeTextCommentImpl.class, "FreeText"));
        mod.registerSubtypes(new NamedType(InteractionCommentImpl.class, "Interaction"));
        mod.registerSubtypes(new NamedType(MassSpectrometryCommentImpl.class, "MassSpectrometry"));
        mod.registerSubtypes(new NamedType(RnaEditingCommentImpl.class, "RnaEditing"));
        mod.registerSubtypes(new NamedType(SequenceCautionCommentImpl.class, "SequenceCaution"));
        mod.registerSubtypes(new NamedType(SubcellularLocationCommentImpl.class, "SubcellularLocation"));
        mod.registerSubtypes(new NamedType(WebResourceCommentImpl.class, "WebResource"));

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
        simpleMod.addSerializer(UniProtIdImpl.class, new UniProtIdSerializer());
        simpleMod.addSerializer(AuthorImpl.class, new AuthorSerializer());
        simpleMod.addSerializer(InteractionImpl.InteractorImpl.class, new InteractorSerializer());
        simpleMod.addSerializer(APIsoformImpl.IsoformIdImpl.class, new IsoformIdImplSerializer());
        simpleMod.addSerializer(EvidenceImpl.class, new EvidenceSerializer());
        simpleMod.addSerializer(ECNumberImpl.class, new ECNumberSerializer());
        simpleMod.addSerializer(FlagImpl.class, new FlagSerializer());
        simpleMod.addSerializer(PublicationDateImpl.class, new PublicationDateSerializer());
        simpleMod.addSerializer(ElectronicArticleImpl.LocatorImpl.class, new LocatorSerializer());
        simpleMod.addSerializer(JournalImpl.class, new JournalSerializer());
        simpleMod.addSerializer(UniProtXDbType.class, new UniProtXDbTypeSerializer());
        simpleMod.addSerializer(FeatureDescriptionImpl.class, new FeatureDescriptionSerializer());
        simpleMod.addSerializer(FeatureIdImpl.class, new FeatureIdSerializer());

        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }

}
