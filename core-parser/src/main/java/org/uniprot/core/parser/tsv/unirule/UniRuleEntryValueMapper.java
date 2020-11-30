package org.uniprot.core.parser.tsv.unirule;

import org.uniprot.core.gene.Gene;
import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.CaseRule;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.ConditionValue;
import org.uniprot.core.unirule.Information;
import org.uniprot.core.unirule.Rule;
import org.uniprot.core.unirule.UniRuleEntry;
import org.uniprot.core.util.Utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UniRuleEntryValueMapper implements EntityValueMapper<UniRuleEntry> {
    static final String EMPTY_STRING = "";

    @Override
    public Map<String, String> mapEntity(UniRuleEntry uniRuleEntry, List<String> fields) {
        Map<String, String> map = new HashMap<>();
        map.put("uniRuleId", uniRuleEntry.getUniRuleId().getValue());
        map.put("proteins_annotated", String.valueOf(getProteinAnnotatedCount(uniRuleEntry)));
        map.put("taxonomic_scope", getTaxonomicScope(uniRuleEntry));
        map.put("annotation_covered", getAnnotationCovered(uniRuleEntry));
        map.put("predicted_protein_name", getPredictedProteinName(uniRuleEntry));
        map.put("template_entries", getUniProtAccessions(uniRuleEntry.getInformation()));
        return map;
    }

    private Long getProteinAnnotatedCount(UniRuleEntry uniRuleEntry) {
        Long proteinAnnotatedCount = uniRuleEntry.getProteinsAnnotatedCount();
        if (Utils.notNull(proteinAnnotatedCount)) {
            return proteinAnnotatedCount;
        } else {
            return 0L;
        }
    }

    private String getTaxonomicScope(UniRuleEntry uniRuleEntry) {
        Rule mainRule = uniRuleEntry.getMainRule();
        return mainRule.getConditionSets().stream().map(ConditionSet::getConditions)
                .flatMap(Collection::stream).filter(condition -> "taxon".equals(condition.getType())).findFirst()
                .map(this::getConditionValues).orElse(EMPTY_STRING);
    }

    private String getConditionValues(Condition condition) {
        List<ConditionValue> conditionValues = condition.getConditionValues();
        if (Utils.notNullNotEmpty(conditionValues)) {
            List<String> taxonScopes =
                    conditionValues.stream()
                            .map(this::getTaxonomicScope)
                            .collect(Collectors.toList());
            return String.join(",", taxonScopes);
        }
        return EMPTY_STRING;
    }

    private String getTaxonomicScope(ConditionValue cv) {
        StringBuilder builder = new StringBuilder(cv.getValue());
        builder.append("[").append(cv.getCvId()).append("]");
        return builder.toString();
    }

    private String getUniProtAccessions(Information information) {
        List<UniProtKBAccession> uniProtAccessions = information.getUniProtAccessions();
        if (Utils.notNullNotEmpty(uniProtAccessions)) {
            return uniProtAccessions.stream().map(UniProtKBAccession::getValue).collect(Collectors.joining(","));
        } else {
            return EMPTY_STRING;
        }
    }

    private String getAnnotationCovered(UniRuleEntry entry) {
        List<Annotation> annotations = entry.getMainRule().getAnnotations();
        if (Utils.notNullNotEmpty(annotations)) {
            List<String> commentTypes = annotations.stream().map(Annotation::getComment)
                    .map(Comment::getCommentType).map(CommentType::getName).collect(Collectors.toList());
            List<String> dbType = annotations.stream().map(Annotation::getDbReference)
                    .map(UniProtKBCrossReference::getDatabase).map(UniProtKBDatabase::getName).collect(Collectors.toList());
            Keyword keyword = annotations.stream().map(Annotation::getKeyword).findFirst().orElse(null);
            Gene gene = annotations.stream().map(Annotation::getGene).findFirst().orElse(null);
            Set<String> annotCovered = new HashSet<>(commentTypes);
            annotCovered.addAll(dbType);
            if (Utils.notNull(keyword)) {
                annotCovered.add("keyword");
            }
            if (Utils.notNull(gene)) {
                annotCovered.add("gene");
            }
            return String.join(",", annotCovered);
        }
        return EMPTY_STRING;
    }

    private String getPredictedProteinName(UniRuleEntry uniRuleEntry) {
        // get protein name from main rule
        List<Annotation> annotations = uniRuleEntry.getMainRule().getAnnotations();
        StringBuilder builder = new StringBuilder();
        if (Utils.notNullNotEmpty(annotations)) {
            builder = getProteinName(annotations);
        }
        // get protein name from case rules
        List<CaseRule> otherRules = uniRuleEntry.getOtherRules();
        if (Utils.notNullNotEmpty(otherRules)) {
            for (CaseRule caseRule : otherRules) {
                StringBuilder proteinNameBuilder = getProteinName(caseRule.getAnnotations());
                if (builder.length() > 0) {
                    builder.append("|");
                }
                if (Utils.notNullNotEmpty(proteinNameBuilder.toString())) {
                    builder.append(proteinNameBuilder);
                }
            }
        }

        return builder.toString();
    }

    private StringBuilder getProteinName(List<Annotation> annotations) {
        ProteinDescription proteinDescription = annotations.stream().map(Annotation::getProteinDescription)
                .findFirst().orElse(null);
        StringBuilder builder = new StringBuilder();
        if (Utils.notNull(proteinDescription)) {
            String fullName = null;
            List<String> ecNumbers = null;
            List<String> shortNames = null;
            if (Utils.notNull(proteinDescription.getRecommendedName())) {
                fullName = proteinDescription.getRecommendedName().getFullName().getValue();
                ecNumbers = proteinDescription.getRecommendedName().getEcNumbers().stream()
                        .map(EC::getValue).filter(Utils::notNullNotEmpty)
                        .map(val -> "EC:" + val).collect(Collectors.toList());

                shortNames = proteinDescription.getRecommendedName().getShortNames().stream()
                        .map(Name::getValue).filter(Utils::notNullNotEmpty)
                        .map(val -> "Short:" + val).collect(Collectors.toList());
            }

            if (Utils.notNullNotEmpty(fullName)) {
                builder.append("Full:").append(fullName);
            }

            if (Utils.notNullNotEmpty(shortNames)) {
                if (builder.length() > 0) {
                    builder.append("|");
                }
                builder.append(String.join("|", shortNames));
            }
            if (Utils.notNullNotEmpty(ecNumbers)) {
                if (builder.length() > 0) {
                    builder.append("|");
                }
                builder.append(String.join("|", ecNumbers));
            }
        }
        return builder;
    }
}
