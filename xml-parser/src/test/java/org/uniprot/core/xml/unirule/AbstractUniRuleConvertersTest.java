package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.*;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.uniprot.*;
import org.uniprot.core.xml.jaxb.unirule.RuleExceptionType;
import org.uniprot.core.xml.jaxb.unirule.SamTriggerType;
import org.uniprot.core.xml.uniprot.UniProtKBEntryConverterTest;

public abstract class AbstractUniRuleConvertersTest extends AbstractConverterTest {

    protected Object updateObject(Object uniObj) {
        Object updatedUniObj = uniObj;
        // update UniProtKBCrossReference object because domain does't have access to cv
        // and it has Mock object which doesn't work with converter
        if (uniObj instanceof Annotation) {
            if (Objects.nonNull(((Annotation) uniObj).getDbReference())) {
                updatedUniObj = updateDBRef((Annotation) uniObj);
            }
        } else if (uniObj instanceof Rule) {
            updatedUniObj = updateRule((Rule) uniObj);
        } else if (uniObj instanceof PositionFeatureSet) {
            updatedUniObj = updatePositionalFeatureSet((PositionFeatureSet) uniObj);
        } else if (uniObj instanceof RuleException) {
            updatedUniObj = updateRuleException((RuleException) uniObj);
        } else if (uniObj instanceof SamFeatureSet) {
            updatedUniObj = updateSamFeatureSet((SamFeatureSet) uniObj);
        } else if (uniObj instanceof UniRuleEntry) {
            Rule rule = updateRule(((UniRuleEntry) uniObj).getMainRule());
            List<CaseRule> caseRules = updateCaseRules(((UniRuleEntry) uniObj).getOtherRules());
            List<SamFeatureSet> samFeatureSets =
                    updateSamFeatureSets(((UniRuleEntry) uniObj).getSamFeatureSets());
            List<PositionFeatureSet> positionFeatureSets =
                    updatePositionFeatureSets(((UniRuleEntry) uniObj).getPositionFeatureSets());
            // update the uniruleentry object
            UniRuleEntryBuilder builder = UniRuleEntryBuilder.from((UniRuleEntry) uniObj);
            builder.mainRule(rule).otherRulesSet(caseRules);
            builder.samFeatureSetsSet(samFeatureSets);
            builder.positionFeatureSetsSet(positionFeatureSets);
            builder.statistics(null);
            updatedUniObj = builder.build();
        }

        return updatedUniObj;
    }

    protected void verifyBusinessConstraints(Object xmlObject) {
        // inject some value to satisfy the test. e.g. annotation and positionalfeature are mutually
        // exclusive
        if (xmlObject instanceof RuleExceptionType) {
            if (Objects.isNull(((RuleExceptionType) xmlObject).getPositionalFeature())) {
                assertNotNull(((RuleExceptionType) xmlObject).getAnnotation());
                ((RuleExceptionType) xmlObject)
                        .setPositionalFeature(uniRuleObjectFactory.createPositionalFeatureType());
            } else if (Objects.isNull(((RuleExceptionType) xmlObject).getAnnotation())) {
                assertNotNull(((RuleExceptionType) xmlObject).getPositionalFeature());
                ((RuleExceptionType) xmlObject)
                        .setAnnotation(uniRuleObjectFactory.createAnnotationType());
            }
        } else if (xmlObject instanceof SamTriggerType) { // one field should be set
            boolean isOneSet =
                    (Objects.isNull(((SamTriggerType) xmlObject).getCoiledCoil())
                            || Objects.isNull(((SamTriggerType) xmlObject).getSignal())
                            || Objects.isNull(((SamTriggerType) xmlObject).getTransmembrane()));
            assertTrue(isOneSet, "one of the SamTriggerType should be set");
            ((SamTriggerType) xmlObject)
                    .setTransmembrane(uniRuleObjectFactory.createSamTransmembraneConditionType());
            ((SamTriggerType) xmlObject)
                    .setSignal(uniRuleObjectFactory.createSamSignalConditionType());
            ((SamTriggerType) xmlObject)
                    .setCoiledCoil(uniRuleObjectFactory.createSamCoiledCoilConditionType());
        } else if (xmlObject instanceof CommentType) {
            CommentType commentType = objectCreator.createLoremIpsumObject(CommentType.class);
            CommentType convertedComment = (CommentType) xmlObject;
            convertedComment.setAbsorption(commentType.getAbsorption());
            convertedComment
                    .getCofactor()
                    .add(objectCreator.createLoremIpsumObject(CofactorType.class));
            convertedComment.setConflict(commentType.getConflict());
            convertedComment.setKinetics(
                    objectCreator.createLoremIpsumObject(CommentType.Kinetics.class));
            convertedComment.setPhDependence(
                    objectCreator.createLoremIpsumObject(CommentType.PhDependence.class));
            convertedComment.setRedoxPotential(
                    objectCreator.createLoremIpsumObject(CommentType.RedoxPotential.class));
            convertedComment.setTemperatureDependence(
                    objectCreator.createLoremIpsumObject(CommentType.TemperatureDependence.class));
            convertedComment.setReaction(objectCreator.createLoremIpsumObject(ReactionType.class));
            convertedComment
                    .getPhysiologicalReaction()
                    .add(objectCreator.createLoremIpsumObject(PhysiologicalReactionType.class));
            convertedComment
                    .getLink()
                    .add(objectCreator.createLoremIpsumObject(CommentType.Link.class));
            convertedComment.getEvent().add(objectCreator.createLoremIpsumObject(EventType.class));
            convertedComment
                    .getIsoform()
                    .add(objectCreator.createLoremIpsumObject(IsoformType.class));
            convertedComment.setError("error");
            convertedComment.setExperiments(12);
            convertedComment
                    .getInteractant()
                    .add(objectCreator.createLoremIpsumObject(InteractantType.class));
            convertedComment.setOrganismsDiffer(true);
            convertedComment.setLocationType("loc");
            convertedComment.setName("dummy");
            convertedComment.setMass(12.0f);
            convertedComment.setMethod("method");
            convertedComment
                    .getLocation()
                    .add(objectCreator.createLoremIpsumObject(LocationType.class));
            convertedComment
                    .getSubcellularLocation()
                    .add(objectCreator.createLoremIpsumObject(SubcellularLocationType.class));
            convertedComment.setDisease(
                    objectCreator.createLoremIpsumObject(CommentType.Disease.class));
            convertedComment.getEvidence().add(12);
        }
    }

    protected static void verifyBean(Object xmlObject) throws Exception {
        for (PropertyDescriptor pd :
                Introspector.getBeanInfo(xmlObject.getClass()).getPropertyDescriptors()) {
            if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
                Object fieldVal = pd.getReadMethod().invoke(xmlObject);
                assertNotNull(
                        fieldVal, xmlObject.getClass() + " field `" + pd.getName() + "` is null");
                if (fieldVal instanceof Collection) {
                    assertFalse(
                            ((List) fieldVal).isEmpty(),
                            xmlObject.getClass() + " field `" + pd.getName() + " is empty");
                } else if (fieldVal instanceof Map) {
                    assertFalse(((Map) fieldVal).isEmpty(), pd.getName() + " is empty");
                }
            }
        }
    }

    protected List<PositionFeatureSet> updatePositionFeatureSets(
            List<PositionFeatureSet> positionFeatureSets) {
        List<PositionFeatureSet> updatedPositionFeatureSets = null;
        if (Utils.notNullNotEmpty(positionFeatureSets)) {
            updatedPositionFeatureSets =
                    positionFeatureSets.stream()
                            .map(pfs -> updatePositionalFeatureSet(pfs))
                            .collect(Collectors.toList());
        }
        return updatedPositionFeatureSets;
    }

    protected List<SamFeatureSet> updateSamFeatureSets(List<SamFeatureSet> samFeatureSets) {
        List<SamFeatureSet> updatedSamFeatureSets = null;
        if (Utils.notNullNotEmpty(samFeatureSets)) {
            updatedSamFeatureSets =
                    samFeatureSets.stream()
                            .map(sfs -> updateSamFeatureSet(sfs))
                            .collect(Collectors.toList());
        }
        return updatedSamFeatureSets;
    }

    protected List<CaseRule> updateCaseRules(List<CaseRule> otherRules) {
        List<CaseRule> updatedOtherRules = null;
        if (Utils.notNullNotEmpty(otherRules)) {
            updatedOtherRules =
                    otherRules.stream()
                            .map(caseRule -> updateRule(caseRule))
                            .map(caseRule -> (CaseRule) caseRule)
                            .collect(Collectors.toList());
        }
        return updatedOtherRules;
    }

    protected SamFeatureSet updateSamFeatureSet(SamFeatureSet uniObj) {
        SamFeatureSet updatedUniObj = uniObj;
        List<Annotation> annotations = ((SamFeatureSet) uniObj).getAnnotations();
        updatedUniObj =
                SamFeatureSetBuilder.from((SamFeatureSet) uniObj)
                        .annotationsSet(updateDBRefOfAnnotations(annotations))
                        .build();
        return updatedUniObj;
    }

    protected Object updateRuleException(RuleException uniObj) {
        RuleException updatedUniObj = uniObj;
        RuleExceptionAnnotation annotation = uniObj.getAnnotation();
        if (annotation instanceof Annotation) {
            if (Objects.nonNull(annotation)) {
                Annotation updatedAnnotation = updateDBRef((Annotation) annotation);
                updatedUniObj =
                        RuleExceptionBuilder.from(uniObj).annotation(updatedAnnotation).build();
            }
        }
        return updatedUniObj;
    }

    protected PositionFeatureSet updatePositionalFeatureSet(PositionFeatureSet uniObj) {
        PositionFeatureSet updatedUniObj = uniObj;
        List<Annotation> annotations = uniObj.getAnnotations();
        List<RuleException> ruleExceptions = uniObj.getRuleExceptions();
        updatedUniObj =
                PositionFeatureSetBuilder.from(uniObj)
                        .annotationsSet(updateDBRefOfAnnotations(annotations))
                        .ruleExceptionsSet(updateDBRefOfExceptions(ruleExceptions))
                        .build();
        return updatedUniObj;
    }

    protected Rule updateRule(Rule uniObj) {
        Rule updatedUniObj = uniObj;
        List<Annotation> annotations = uniObj.getAnnotations();
        List<RuleException> ruleExceptions = uniObj.getRuleExceptions();
        // update the object
        if (uniObj instanceof CaseRule) {
            updatedUniObj =
                    CaseRuleBuilder.from((CaseRule) uniObj)
                            .annotationsSet(updateDBRefOfAnnotations(annotations))
                            .ruleExceptionsSet(updateDBRefOfExceptions(ruleExceptions))
                            .build();
        } else {
            updatedUniObj =
                    RuleBuilder.from(uniObj)
                            .annotationsSet(updateDBRefOfAnnotations(annotations))
                            .ruleExceptionsSet(updateDBRefOfExceptions(ruleExceptions))
                            .build();
        }
        return updatedUniObj;
    }

    protected RuleException updateDBRef(RuleException uniObj) {
        RuleExceptionBuilder builder = RuleExceptionBuilder.from(uniObj);
        builder.annotation(updateDBRef((Annotation) uniObj.getAnnotation()));
        return builder.build();
    }

    protected Annotation updateDBRef(Annotation uniObj) {
        AnnotationBuilder builder = AnnotationBuilder.from(uniObj);
        builder.dbReference(UniProtKBEntryConverterTest.createUniProtKBCrossReference());
        return builder.build();
    }

    protected List<Annotation> updateDBRefOfAnnotations(List<Annotation> annotations) {
        List<Annotation> updatedAnnotations = null;
        if (Utils.notNullNotEmpty(annotations)) {
            updatedAnnotations =
                    annotations.stream()
                            .map(annotation -> updateDBRef(annotation))
                            .collect(Collectors.toList());
        }
        return updatedAnnotations;
    }

    protected List<RuleException> updateDBRefOfExceptions(List<RuleException> ruleExceptions) {
        List<RuleException> updatedRuleExceptions = null;
        if (Utils.notNullNotEmpty(ruleExceptions)) {
            updatedRuleExceptions =
                    ruleExceptions.stream()
                            .filter(re -> re.getAnnotation() instanceof Annotation)
                            .map(re -> updateDBRef(re))
                            .collect(Collectors.toList());
        }
        return updatedRuleExceptions;
    }
}
