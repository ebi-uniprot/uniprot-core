package org.uniprot.core.parser.tsv.unirule;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.disease.impl.DiseaseCrossReferenceBuilder;
import org.uniprot.core.cv.disease.impl.DiseaseEntryBuilder;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;
import org.uniprot.core.parser.tsv.disease.DiseaseEntryValueMapper;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.Information;
import org.uniprot.core.unirule.Rule;
import org.uniprot.core.unirule.RuleStatus;
import org.uniprot.core.unirule.UniRuleEntry;
import org.uniprot.core.unirule.UniRuleId;
import org.uniprot.core.unirule.impl.ConditionBuilder;
import org.uniprot.core.unirule.impl.ConditionSetBuilder;
import org.uniprot.core.unirule.impl.InformationBuilder;
import org.uniprot.core.unirule.impl.RuleBuilder;
import org.uniprot.core.unirule.impl.UniRuleEntryBuilder;
import org.uniprot.core.unirule.impl.UniRuleIdBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        UniRuleEntry entry = new UniRuleEntryBuilder(uniRuleIdObject, status, information, mainRule).build();

        Map<String, String> mappedEntries =
                new UniRuleEntryValueMapper().mapEntity(entry, Collections.emptyList());

        assertThat(mappedEntries, notNullValue());
        assertEquals(2, mappedEntries.size());
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
        assertEquals(2, mappedEntries.size());
        assertEquals("P12345,Q12345,R12345", mappedEntries.get("template_entries"));
    }

}
