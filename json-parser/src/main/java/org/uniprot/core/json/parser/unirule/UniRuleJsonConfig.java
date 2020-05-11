package org.uniprot.core.json.parser.unirule;

import java.time.LocalDate;

import org.uniprot.core.CrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.gene.*;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.impl.ECNumberImpl;
import org.uniprot.core.json.parser.JsonConfig;
import org.uniprot.core.json.parser.deserializer.LocalDateDeserializer;
import org.uniprot.core.json.parser.serializer.LocalDateSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.ECNumberSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.EvidenceSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.UniProtDatabaseSerializer;
import org.uniprot.core.json.parser.uniprot.serializer.UniProtKBAccessionSerializer;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.comment.impl.*;
import org.uniprot.core.uniprotkb.description.*;
import org.uniprot.core.uniprotkb.description.impl.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceImpl;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueImpl;
import org.uniprot.core.uniprotkb.impl.GeneImpl;
import org.uniprot.core.uniprotkb.impl.KeywordImpl;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionImpl;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtKBCrossReferenceImpl;
import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.*;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class UniRuleJsonConfig extends JsonConfig {

    private static UniRuleJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper prettyMapper;

    private UniRuleJsonConfig() {
        this.prettyMapper = initPrettyObjectMapper();
        this.objectMapper = initObjectMapper();
    }

    public static synchronized UniRuleJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniRuleJsonConfig();
        }
        return INSTANCE;
    }

    @Override
    public ObjectMapper getSimpleObjectMapper() {
        return this.prettyMapper;
    }

    @Override
    public ObjectMapper getFullObjectMapper() {
        return objectMapper;
    }

    private ObjectMapper initObjectMapper() {
        ObjectMapper objMapper = getDefaultFullObjectMapper();
        SimpleModule mod = new SimpleModule();
        mod.addSerializer(LocalDate.class, new LocalDateSerializer());
        mod.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        mod.addAbstractTypeMapping(UniRuleEntry.class, UniRuleEntryImpl.class);
        mod.addAbstractTypeMapping(UniRuleId.class, UniRuleIdImpl.class);
        mod.addAbstractTypeMapping(Information.class, InformationImpl.class);
        mod.addAbstractTypeMapping(Fusion.class, FusionImpl.class);
        mod.addAbstractTypeMapping(UniProtKBAccession.class, UniProtKBAccessionImpl.class);
        mod.addAbstractTypeMapping(Rule.class, RuleImpl.class);
        mod.addAbstractTypeMapping(ConditionSet.class, ConditionSetImpl.class);
        mod.addAbstractTypeMapping(Condition.class, ConditionImpl.class);
        mod.addAbstractTypeMapping(ConditionValue.class, ConditionValueImpl.class);
        mod.addAbstractTypeMapping(Annotation.class, AnnotationImpl.class);
        mod.addAbstractTypeMapping(RuleException.class, RuleExceptionImpl.class);

        // comment
        mod.addAbstractTypeMapping(
                AlternativeProductsComment.class, AlternativeProductsCommentImpl.class);
        mod.addAbstractTypeMapping(SequenceCautionComment.class, SequenceCautionCommentImpl.class);
        mod.addAbstractTypeMapping(
                MassSpectrometryComment.class, MassSpectrometryCommentImpl.class);
        mod.addAbstractTypeMapping(RnaEditingComment.class, RnaEditingCommentImpl.class);
        mod.addAbstractTypeMapping(DiseaseComment.class, DiseaseCommentImpl.class);
        mod.registerSubtypes(new NamedType(DiseaseCommentImpl.class));
        mod.addAbstractTypeMapping(Disease.class, DiseaseImpl.class);
        mod.addAbstractTypeMapping(InteractionComment.class, InteractionCommentImpl.class);
        mod.addAbstractTypeMapping(BPCPComment.class, BPCPCommentImpl.class);
        mod.addAbstractTypeMapping(
                CatalyticActivityComment.class, CatalyticActivityCommentImpl.class);
        mod.addAbstractTypeMapping(CofactorComment.class, CofactorCommentImpl.class);
        mod.addAbstractTypeMapping(WebResourceComment.class, WebResourceCommentImpl.class);
        mod.addAbstractTypeMapping(
                SubcellularLocationComment.class, SubcellularLocationCommentImpl.class);
        mod.addAbstractTypeMapping(Note.class, NoteImpl.class);
        mod.addAbstractTypeMapping(SubcellularLocation.class, SubcellularLocationImpl.class);
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

        mod.addAbstractTypeMapping(Keyword.class, KeywordImpl.class);

        mod.addAbstractTypeMapping(Gene.class, GeneImpl.class);
        mod.addAbstractTypeMapping(GeneName.class, GeneImpl.GeneNameImpl.class);
        mod.addAbstractTypeMapping(GeneNameSynonym.class, GeneImpl.GeneNameSynonymImpl.class);
        mod.addAbstractTypeMapping(OrderedLocusName.class, GeneImpl.OrderedLocusNameImpl.class);
        mod.addAbstractTypeMapping(ORFName.class, GeneImpl.ORFNameImpl.class);
        mod.addAbstractTypeMapping(EvidencedValue.class, EvidencedValueImpl.class);
        mod.addAbstractTypeMapping(Evidence.class, EvidenceImpl.class);

        mod.addAbstractTypeMapping(CrossReference.class, CrossReferenceImpl.class);
        mod.addAbstractTypeMapping(
                UniProtKBCrossReference.class, UniProtKBCrossReferenceImpl.class);
        mod.addAbstractTypeMapping(UniProtKBDatabase.class, UniProtKBDatabaseImpl.class);

        mod.addAbstractTypeMapping(ProteinDescription.class, ProteinDescriptionImpl.class);
        mod.addAbstractTypeMapping(ProteinRecName.class, ProteinRecNameImpl.class);
        mod.addAbstractTypeMapping(ProteinSubName.class, ProteinSubNameImpl.class);
        mod.addAbstractTypeMapping(ProteinAltName.class, ProteinAltNameImpl.class);
        mod.addAbstractTypeMapping(ProteinSection.class, ProteinSectionImpl.class);
        mod.addAbstractTypeMapping(Name.class, NameImpl.class);
        mod.addAbstractTypeMapping(EC.class, ECImpl.class);
        mod.addAbstractTypeMapping(ECNumber.class, ECNumberImpl.class);
        mod.addAbstractTypeMapping(Flag.class, FlagImpl.class);

        mod.addAbstractTypeMapping(PositionalFeature.class, PositionalFeatureImpl.class);
        mod.addAbstractTypeMapping(CaseRule.class, CaseRuleImpl.class);
        mod.addAbstractTypeMapping(SamFeatureSet.class, SamFeatureSetImpl.class);
        mod.addAbstractTypeMapping(SamTrigger.class, SamTriggerImpl.class);
        mod.addAbstractTypeMapping(PositionFeatureSet.class, PositionFeatureSetImpl.class);
        mod.registerSubtypes(new NamedType(AnnotationImpl.class, "annotation"));
        mod.registerSubtypes(new NamedType(PositionalFeatureImpl.class, "positional"));

        mod.addAbstractTypeMapping(
                FeatureTagConditionValue.class, FeatureTagConditionValueImpl.class);
        objMapper.registerModule(mod);
        return objMapper;
    }

    private ObjectMapper initPrettyObjectMapper() {
        ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();
        SimpleModule simpleMod = new SimpleModule();
        simpleMod.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleMod.addSerializer(UniRuleIdImpl.class, new UniRuleIdImplSerializer());
        simpleMod.addSerializer(UniProtKBAccessionImpl.class, new UniProtKBAccessionSerializer());
        simpleMod.addSerializer(EvidenceImpl.class, new EvidenceSerializer());
        simpleMod.addSerializer(ECNumberImpl.class, new ECNumberSerializer());
        simpleMod.addSerializer(UniProtKBDatabase.class, new UniProtDatabaseSerializer());
        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }
}
