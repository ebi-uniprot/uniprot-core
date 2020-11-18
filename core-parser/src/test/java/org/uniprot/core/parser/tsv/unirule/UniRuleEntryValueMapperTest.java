package org.uniprot.core.parser.tsv.unirule;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.unirule.CaseRule;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.Information;
import org.uniprot.core.unirule.Rule;
import org.uniprot.core.unirule.RuleStatus;
import org.uniprot.core.unirule.UniRuleEntry;
import org.uniprot.core.unirule.UniRuleId;
import org.uniprot.core.unirule.impl.CaseRuleBuilderTest;
import org.uniprot.core.unirule.impl.ConditionBuilder;
import org.uniprot.core.unirule.impl.ConditionSetBuilder;
import org.uniprot.core.unirule.impl.ConditionValueBuilder;
import org.uniprot.core.unirule.impl.InformationBuilder;
import org.uniprot.core.unirule.impl.RuleBuilder;
import org.uniprot.core.unirule.impl.RuleBuilderTest;
import org.uniprot.core.unirule.impl.UniRuleEntryBuilder;
import org.uniprot.core.unirule.impl.UniRuleIdBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UniRuleEntryValueMapperTest {

    @Test
    void checkWithoutUniProtAccessionEntity() {
        String uniRuleId = "UR123456789";
        UniRuleId uniRuleIdObject = new UniRuleIdBuilder(uniRuleId).build();
        RuleStatus status = RuleStatus.APPLY;
        Information information = new InformationBuilder("sample version").build();
        Condition condition = new ConditionBuilder("random type").build();
        ConditionSet conditionSet = new ConditionSetBuilder(condition).build();
        Rule mainRule = new RuleBuilder(conditionSet).build();
        UniRuleEntry entry = new UniRuleEntryBuilder(uniRuleIdObject, status, information, mainRule).proteinsAnnotatedCount(10L).build();

        Map<String, String> mappedEntries =
                new UniRuleEntryValueMapper().mapEntity(entry, Collections.emptyList());

        assertThat(mappedEntries, notNullValue());
        assertEquals(6, mappedEntries.size());
        assertEquals(uniRuleId, mappedEntries.get("uniRuleId"));
        assertEquals("10", mappedEntries.get("proteins_annotated"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("taxonomic_scope"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("annotation_covered"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("predicted_protein_name"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("template_entries"));
    }

    @Test
    void checkWithUniProtAccessionEntity() {
        String uniRuleId = "UR123456789";
        UniRuleId uniRuleIdObject = new UniRuleIdBuilder(uniRuleId).build();
        RuleStatus status = RuleStatus.APPLY;
        InformationBuilder informationBuilder = new InformationBuilder("sample version");
        informationBuilder.uniProtAccessionsAdd(new UniProtKBAccessionBuilder("P12345").build());
        informationBuilder.uniProtAccessionsAdd(new UniProtKBAccessionBuilder("Q12345").build());
        informationBuilder.uniProtAccessionsAdd(new UniProtKBAccessionBuilder("R12345").build());
        Condition condition = new ConditionBuilder("random type").build();
        ConditionSet conditionSet = new ConditionSetBuilder(condition).build();
        Rule mainRule = new RuleBuilder(conditionSet).build();
        UniRuleEntry entry = new UniRuleEntryBuilder(uniRuleIdObject, status, informationBuilder.build(), mainRule).build();

        Map<String, String> mappedEntries =
                new UniRuleEntryValueMapper().mapEntity(entry, Collections.emptyList());

        assertThat(mappedEntries, notNullValue());
        assertEquals(6, mappedEntries.size());
        assertEquals(uniRuleId, mappedEntries.get("uniRuleId"));
        assertEquals("P12345,Q12345,R12345", mappedEntries.get("template_entries"));
        assertEquals("0", mappedEntries.get("proteins_annotated"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("taxonomic_scope"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("annotation_covered"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("predicted_protein_name"));
    }


    @Test
    void testTaxonomicScope(){
        String uniRuleId = "UR123456789";
        UniRuleId uniRuleIdObject = new UniRuleIdBuilder(uniRuleId).build();
        RuleStatus status = RuleStatus.APPLY;
        Information information = new InformationBuilder("sample version").build();
        ConditionBuilder conditionBuilder = new ConditionBuilder("taxon");
        conditionBuilder.conditionValuesAdd(new ConditionValueBuilder("Archaea").cvId("2157").build());
        conditionBuilder.conditionValuesAdd(new ConditionValueBuilder("Eukaryota").cvId("2759").build());
        conditionBuilder.conditionValuesAdd(new ConditionValueBuilder("Bacteria").cvId("2").build());
        ConditionSet conditionSet = new ConditionSetBuilder(conditionBuilder.build()).build();
        Rule mainRule = new RuleBuilder(conditionSet).build();
        UniRuleEntry entry = new UniRuleEntryBuilder(uniRuleIdObject, status, information, mainRule).build();

        Map<String, String> mappedEntries =
                new UniRuleEntryValueMapper().mapEntity(entry, Collections.emptyList());
        assertThat(mappedEntries, notNullValue());
        assertEquals(6, mappedEntries.size());
        assertEquals(uniRuleId, mappedEntries.get("uniRuleId"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("template_entries"));
        assertEquals("0", mappedEntries.get("proteins_annotated"));
        assertEquals("Archaea[2157],Eukaryota[2759],Bacteria[2]", mappedEntries.get("taxonomic_scope"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("annotation_covered"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("predicted_protein_name"));
    }

    @Test
    void testAnnotationCovered(){
        Rule mainRule = RuleBuilderTest.createObject(3);
        String uniRuleId = "UR123456789";
        UniRuleId uniRuleIdObject = new UniRuleIdBuilder(uniRuleId).build();
        RuleStatus status = RuleStatus.APPLY;
        Information information = new InformationBuilder("sample version").build();
        UniRuleEntry entry = new UniRuleEntryBuilder(uniRuleIdObject, status, information, mainRule).proteinsAnnotatedCount(10L).build();

        Map<String, String> mappedEntries =
                new UniRuleEntryValueMapper().mapEntity(entry, Collections.emptyList());

        assertThat(mappedEntries, notNullValue());
        assertEquals(6, mappedEntries.size());
        assertEquals(uniRuleId, mappedEntries.get("uniRuleId"));
        assertEquals("10", mappedEntries.get("proteins_annotated"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("taxonomic_scope"));
        assertEquals("DOMAIN,EMBL,gene,keyword", mappedEntries.get("annotation_covered"));
        assertTrue(mappedEntries.get("predicted_protein_name").startsWith("Full:"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("template_entries"));
    }

    @Test
    void testPredictedProteinName(){
        Rule mainRule = RuleBuilderTest.createObject(3);
        List<CaseRule> caseRules = CaseRuleBuilderTest.createObjects(1, false);
        String uniRuleId = "UR123456789";
        UniRuleId uniRuleIdObject = new UniRuleIdBuilder(uniRuleId).build();
        RuleStatus status = RuleStatus.APPLY;
        Information information = new InformationBuilder("sample version").build();
        UniRuleEntry entry = new UniRuleEntryBuilder(uniRuleIdObject, status, information, mainRule)
                .proteinsAnnotatedCount(10L).otherRulesSet(caseRules).build();

        Map<String, String> mappedEntries =
                new UniRuleEntryValueMapper().mapEntity(entry, Collections.emptyList());

        assertThat(mappedEntries, notNullValue());
        assertEquals(6, mappedEntries.size());
        assertEquals(uniRuleId, mappedEntries.get("uniRuleId"));
        assertEquals("10", mappedEntries.get("proteins_annotated"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("taxonomic_scope"));
        assertEquals("DOMAIN,EMBL,gene,keyword", mappedEntries.get("annotation_covered"));
        String proteinNames = mappedEntries.get("predicted_protein_name");
        String[] proteinNamesTokens = proteinNames.split("\\|");
        assertEquals(10, proteinNamesTokens.length);
        assertTrue(proteinNamesTokens[0].startsWith("Full:Value - "));
        assertTrue(proteinNamesTokens[1].startsWith("Short:Value - "));
        assertTrue(proteinNamesTokens[4].startsWith("EC:value-"));
        assertTrue(proteinNamesTokens[7].startsWith("Full:Value - "));
        assertTrue(proteinNamesTokens[8].startsWith("Short:Value - "));
        assertTrue(proteinNamesTokens[9].startsWith("EC:value-"));
        assertEquals(UniRuleEntryValueMapper.EMPTY_STRING, mappedEntries.get("template_entries"));
    }
}
