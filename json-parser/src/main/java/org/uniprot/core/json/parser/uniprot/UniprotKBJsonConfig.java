package org.uniprot.core.json.parser.uniprot;

import java.time.LocalDate;

import org.uniprot.core.*;
import org.uniprot.core.citation.*;
import org.uniprot.core.citation.impl.*;
import org.uniprot.core.feature.FeatureDescription;
import org.uniprot.core.feature.FeatureType;
import org.uniprot.core.feature.impl.FeatureDescriptionImpl;
import org.uniprot.core.gene.*;
import org.uniprot.core.impl.*;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.deserializer.LocalDateDeserializer;
import org.uniprot.core.json.parser.serializer.*;
import org.uniprot.core.json.parser.uniprot.deserializer.EvidenceDeserializer;
import org.uniprot.core.json.parser.uniprot.deserializer.UniProtIdDeserializer;
import org.uniprot.core.json.parser.uniprot.deserializer.UniProtKBAccessionDeserializer;
import org.uniprot.core.json.parser.uniprot.deserializer.UniProtKBCrossReferenceDeserializer;
import org.uniprot.core.json.parser.uniprot.serializer.*;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageImpl;
import org.uniprot.core.uniprotkb.*;
import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.comment.impl.*;
import org.uniprot.core.uniprotkb.description.*;
import org.uniprot.core.uniprotkb.description.impl.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceLine;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceImpl;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceLineImpl;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueImpl;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.FeatureId;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceImpl;
import org.uniprot.core.uniprotkb.feature.impl.FeatureIdImpl;
import org.uniprot.core.uniprotkb.feature.impl.UniProtKBFeatureImpl;
import org.uniprot.core.uniprotkb.impl.*;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismHostImpl;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismImpl;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtKBCrossReferenceImpl;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;

/** @author lgonzales */
public class UniprotKBJsonConfig extends JsonConfig {

    private static UniprotKBJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper prettyMapper;
    private SimpleModule prettyWriterModule;

    private UniprotKBJsonConfig() {
        this.objectMapper = initObjectMapper();
        this.prettyMapper = initPrettyObjectMapper();
    }

    public static synchronized UniprotKBJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniprotKBJsonConfig();
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

    public SimpleModule getPrettyWriterModule() {
        return prettyWriterModule;
    }

    private ObjectMapper initObjectMapper() {
        ObjectMapper objMapper = getDefaultFullObjectMapper();

        SimpleModule mod = new SimpleModule();
        mod.addSerializer(LocalDate.class, new LocalDateSerializer());
        mod.addDeserializer(LocalDate.class, new LocalDateDeserializer());

        mod.addSerializer(EvidenceImpl.class, new EvidenceSerializer());
        mod.addDeserializer(EvidenceImpl.class, new EvidenceDeserializer());

        mod.addSerializer(UniProtKBIdImpl.class, new UniProtIdSerializer());
        mod.addDeserializer(UniProtKBIdImpl.class, new UniProtIdDeserializer());

        mod.addSerializer(UniProtKBAccessionImpl.class, new UniProtKBAccessionSerializer());
        mod.addDeserializer(UniProtKBAccessionImpl.class, new UniProtKBAccessionDeserializer());

        mod.addSerializer(
                UniProtKBCrossReferenceImpl.class, new UniProtKBCrossReferenceSerializer());
        mod.addDeserializer(
                UniProtKBCrossReferenceImpl.class, new UniProtKBCrossReferenceDeserializer());

        mod.addAbstractTypeMapping(UniProtKBEntry.class, UniProtKBEntryImpl.class);

        mod.addAbstractTypeMapping(UniProtKBAccession.class, UniProtKBAccessionImpl.class);
        mod.addAbstractTypeMapping(UniProtKBId.class, UniProtKBIdImpl.class);
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

        mod.addAbstractTypeMapping(
                AlternativeProductsComment.class, AlternativeProductsCommentImpl.class);
        mod.addAbstractTypeMapping(APIsoform.class, APIsoformImpl.class);
        mod.addAbstractTypeMapping(IsoformName.class, APIsoformImpl.IsoformNameImpl.class);
        mod.addAbstractTypeMapping(IsoformId.class, APIsoformImpl.IsoformIdImpl.class);
        mod.addAbstractTypeMapping(FreeText.class, FreeTextImpl.class);
        mod.addAbstractTypeMapping(PhysiologicalReaction.class, PhysiologicalReactionImpl.class);
        mod.addAbstractTypeMapping(Absorption.class, AbsorptionImpl.class);
        mod.addAbstractTypeMapping(SequenceCautionComment.class, SequenceCautionCommentImpl.class);
        mod.addAbstractTypeMapping(
                MassSpectrometryComment.class, MassSpectrometryCommentImpl.class);
        mod.addAbstractTypeMapping(RedoxPotential.class, BPCPCommentImpl.RedoxPotentialImpl.class);
        mod.addAbstractTypeMapping(
                RnaEdPosition.class, RnaEditingCommentImpl.RnaEdPositionImpl.class);
        mod.addAbstractTypeMapping(RnaEditingComment.class, RnaEditingCommentImpl.class);
        mod.addAbstractTypeMapping(MichaelisConstant.class, MichaelisConstantImpl.class);
        mod.addAbstractTypeMapping(KineticParameters.class, KineticParametersImpl.class);
        mod.addAbstractTypeMapping(SubcellularLocation.class, SubcellularLocationImpl.class);
        mod.addAbstractTypeMapping(
                TemperatureDependence.class, BPCPCommentImpl.TemperatureDependenceImpl.class);
        mod.addAbstractTypeMapping(Interactant.class, InteractantImpl.class);
        mod.addAbstractTypeMapping(DiseaseComment.class, DiseaseCommentImpl.class);
        mod.addAbstractTypeMapping(Cofactor.class, CofactorImpl.class);
        mod.addAbstractTypeMapping(
                SubcellularLocationValue.class,
                SubcellularLocationImpl.SubcellularLocationValueImpl.class);
        mod.addAbstractTypeMapping(Disease.class, DiseaseImpl.class);
        mod.addAbstractTypeMapping(InteractionComment.class, InteractionCommentImpl.class);
        mod.addAbstractTypeMapping(MaximumVelocity.class, MaximumVelocityImpl.class);
        mod.addAbstractTypeMapping(BPCPComment.class, BPCPCommentImpl.class);
        mod.addAbstractTypeMapping(
                CatalyticActivityComment.class, CatalyticActivityCommentImpl.class);
        mod.addAbstractTypeMapping(CofactorComment.class, CofactorCommentImpl.class);
        mod.addAbstractTypeMapping(Reaction.class, ReactionImpl.class);
        mod.addAbstractTypeMapping(FreeTextComment.class, FreeTextCommentImpl.class);
        mod.addAbstractTypeMapping(Interaction.class, InteractionImpl.class);
        mod.addAbstractTypeMapping(WebResourceComment.class, WebResourceCommentImpl.class);
        mod.addAbstractTypeMapping(Note.class, NoteImpl.class);
        mod.addAbstractTypeMapping(PhDependence.class, BPCPCommentImpl.PhDependenceImpl.class);
        mod.addAbstractTypeMapping(
                SubcellularLocationComment.class, SubcellularLocationCommentImpl.class);

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
        mod.addAbstractTypeMapping(Literature.class, LiteratureImpl.class);

        mod.addAbstractTypeMapping(UniProtKBReference.class, UniProtKBReferenceImpl.class);
        mod.addAbstractTypeMapping(InternalLine.class, InternalLineImpl.class);
        mod.addAbstractTypeMapping(EvidenceLine.class, EvidenceLineImpl.class);
        mod.addAbstractTypeMapping(InternalSection.class, InternalSectionImpl.class);
        mod.addAbstractTypeMapping(Keyword.class, KeywordImpl.class);
        mod.addAbstractTypeMapping(EntryInactiveReason.class, EntryInactiveReasonImpl.class);
        mod.addAbstractTypeMapping(GeneLocation.class, GeneLocationImpl.class);
        mod.addAbstractTypeMapping(SourceLine.class, SourceLineImpl.class);
        mod.addAbstractTypeMapping(Sequence.class, SequenceImpl.class);

        mod.addAbstractTypeMapping(CrossReference.class, CrossReferenceImpl.class);
        mod.addAbstractTypeMapping(
                UniProtKBCrossReference.class, UniProtKBCrossReferenceImpl.class);

        mod.addAbstractTypeMapping(AlternativeSequence.class, AlternativeSequenceImpl.class);

        mod.addAbstractTypeMapping(FeatureId.class, FeatureIdImpl.class);
        mod.addAbstractTypeMapping(FeatureType.class, UniprotKBFeatureType.class);
        mod.addAbstractTypeMapping(FeatureDescription.class, FeatureDescriptionImpl.class);
        mod.addAbstractTypeMapping(UniProtKBFeature.class, UniProtKBFeatureImpl.class);
        mod.addAbstractTypeMapping(UniProtKBDatabase.class, UniProtKBDatabaseImpl.class);

        mod.addAbstractTypeMapping(Database.class, DefaultDatabase.class);

        mod.registerSubtypes(new NamedType(AlternativeProductsCommentImpl.class, "AP"));
        mod.registerSubtypes(new NamedType(BPCPCommentImpl.class, "BPCP"));
        mod.registerSubtypes(
                new NamedType(CatalyticActivityCommentImpl.class, "CatalyticActivity"));
        mod.registerSubtypes(new NamedType(CofactorCommentImpl.class, "Cofactor"));
        mod.registerSubtypes(new NamedType(DiseaseCommentImpl.class, "DiseaseEntry"));
        mod.registerSubtypes(new NamedType(FreeTextCommentImpl.class, "FreeText"));
        mod.registerSubtypes(new NamedType(InteractionCommentImpl.class, "Interaction"));
        mod.registerSubtypes(new NamedType(MassSpectrometryCommentImpl.class, "MassSpectrometry"));
        mod.registerSubtypes(new NamedType(RnaEditingCommentImpl.class, "RnaEditing"));
        mod.registerSubtypes(new NamedType(SequenceCautionCommentImpl.class, "SequenceCaution"));
        mod.registerSubtypes(
                new NamedType(SubcellularLocationCommentImpl.class, "SubcellularLocation"));
        mod.registerSubtypes(new NamedType(WebResourceCommentImpl.class, "WebResource"));

        mod.registerSubtypes(new NamedType(BookImpl.class, "Book"));
        mod.registerSubtypes(new NamedType(ElectronicArticleImpl.class, "ElectronicArticle"));
        mod.registerSubtypes(new NamedType(JournalArticleImpl.class, "JournalArticle"));
        mod.registerSubtypes(new NamedType(PatentImpl.class, "Patent"));
        mod.registerSubtypes(new NamedType(SubmissionImpl.class, "Submission"));
        mod.registerSubtypes(new NamedType(ThesisImpl.class, "Thesis"));
        mod.registerSubtypes(new NamedType(UnpublishedImpl.class, "Unpublished"));
        mod.registerSubtypes(new NamedType(LiteratureImpl.class, "Literature"));

        mod.addAbstractTypeMapping(TaxonomyLineage.class, TaxonomyLineageImpl.class);

        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initPrettyObjectMapper() {
        ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();

        initPrettyModule();
        prettyObjMapper.registerModule(prettyWriterModule);
        return prettyObjMapper;
    }

    private void initPrettyModule() {
        prettyWriterModule = new SimpleModule();
        prettyWriterModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        prettyWriterModule.addSerializer(
                UniProtKBAccessionImpl.class, new UniProtKBAccessionSerializer());
        prettyWriterModule.addSerializer(UniProtKBIdImpl.class, new UniProtIdSerializer());
        prettyWriterModule.addSerializer(AuthorImpl.class, new AuthorSerializer());
        prettyWriterModule.addSerializer(
                APIsoformImpl.IsoformIdImpl.class, new IsoformIdImplSerializer());
        prettyWriterModule.addSerializer(EvidenceImpl.class, new EvidenceSerializer());
        prettyWriterModule.addSerializer(ECNumberImpl.class, new ECNumberSerializer());
        prettyWriterModule.addSerializer(FlagImpl.class, new FlagSerializer());
        prettyWriterModule.addSerializer(
                PublicationDateImpl.class, new PublicationDateSerializer());
        prettyWriterModule.addSerializer(
                ElectronicArticleImpl.LocatorImpl.class, new LocatorSerializer());
        prettyWriterModule.addSerializer(JournalImpl.class, new JournalSerializer());
        prettyWriterModule.addSerializer(UniProtKBDatabase.class, new UniProtDatabaseSerializer());
        prettyWriterModule.addSerializer(
                FeatureDescriptionImpl.class, new FeatureDescriptionSerializer());
        prettyWriterModule.addSerializer(FeatureIdImpl.class, new FeatureIdSerializer());
    }
}
