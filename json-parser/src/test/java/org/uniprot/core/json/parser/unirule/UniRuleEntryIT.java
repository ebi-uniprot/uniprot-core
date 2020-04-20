package org.uniprot.core.json.parser.unirule;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.CaseRule;
import org.uniprot.core.unirule.RuleException;
import org.uniprot.core.unirule.UniRuleEntry;
import org.uniprot.core.unirule.impl.*;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

public class UniRuleEntryIT {
    @Test
    void testFullUniRuleEntryJsonRoundTrip() {
        UniRuleEntry entry = UniRuleEntryBuilderTest.createObject();
        //        ValidateJson.verifyJsonRoundTripParser(
        //                UniRuleJsonConfig.getInstance().getFullObjectMapper(), entry);
        //        ValidateJson.verifyEmptyFields(entry);
    }

    private UniRuleEntry createObject() {
        UniRuleEntry entry = UniRuleEntryBuilderTest.createObject();
        // convert the UniProtKBDatabaseMock to UniProtKBDatabaseImpl
        UniRuleEntryBuilder builder = UniRuleEntryBuilder.from(entry);
        RuleBuilder ruleBuilder = RuleBuilder.from(entry.getMainRule());
        List<Annotation> annotationList = entry.getMainRule().getAnnotations();
        List<Annotation> newAnnotationList = updateUniProtKBDatabaseObject(annotationList);
        ruleBuilder.annotationsSet(newAnnotationList);
        builder.mainRule(ruleBuilder.build());
        List<CaseRule> otherRules = entry.getOtherRules();
        List<CaseRule> updatedOtherRules = new ArrayList<>();
        for (CaseRule otherRule : otherRules) {
            CaseRuleBuilder caseRuleBuilder = CaseRuleBuilder.from(otherRule);
            List<Annotation> caseAnnotationList =
                    updateUniProtKBDatabaseObject(otherRule.getAnnotations());
            caseRuleBuilder.annotationsSet(caseAnnotationList);
            List<RuleException> ruleExceptions = otherRule.getRuleExceptions();
            List<RuleException> updatedRuleExceptions = new ArrayList<>();
            // update annotation's UniProtKBDatabaseImpl of RuleException
            for (RuleException ruleException : ruleExceptions) {
                Annotation annotation = (Annotation) ruleException.getAnnotation();
                Annotation updatedAnnotation = updateUniProtKBDatabaseObject(annotation);
                updatedRuleExceptions.add(
                        new AnnotationRuleExceptionImpl(
                                ruleException.getNote(),
                                ruleException.getCategory(),
                                updatedAnnotation,
                                ruleException.getAccessions()));
            }
            caseRuleBuilder.ruleExceptionsSet(updatedRuleExceptions);
            updatedOtherRules.add(caseRuleBuilder.build());
        }
        builder.otherRulesSet(updatedOtherRules);

        return builder.build();
    }

    private List<Annotation> updateUniProtKBDatabaseObject(List<Annotation> annotationList) {
        List<Annotation> newAnnotationList = new ArrayList<>();
        for (Annotation annotation : annotationList) {
            newAnnotationList.add(updateUniProtKBDatabaseObject(annotation));
        }

        return newAnnotationList;
    }

    private Annotation updateUniProtKBDatabaseObject(Annotation annotation) {
        AnnotationBuilder ab = AnnotationBuilder.from(annotation);
        UniProtKBCrossReference dbRef = annotation.getDbReference();
        UniProtCrossReferenceBuilder dbRefBuilder = UniProtCrossReferenceBuilder.from(dbRef);
        dbRefBuilder.database(new UniProtKBDatabaseImpl(dbRef.getDatabase().getName()));
        ab.dbReference(dbRefBuilder.build());
        return ab.build();
    }
}
