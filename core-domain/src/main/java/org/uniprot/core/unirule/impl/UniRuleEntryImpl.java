package org.uniprot.core.unirule.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.Statistics;
import org.uniprot.core.unirule.*;
import org.uniprot.core.util.Utils;

/** @author sahmad */
public class UniRuleEntryImpl implements UniRuleEntry {
    private static final long serialVersionUID = -4460157798001059237L;
    private UniRuleId uniRuleId;
    private Information information;
    private RuleStatus ruleStatus;
    private Rule mainRule;
    private List<CaseRule> otherRules;
    private List<SamFeatureSet> samFeatureSets;
    private List<PositionFeatureSet> positionFeatureSets;
    private Statistics statistics;
    private String createdBy;
    private String modifiedBy;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    UniRuleEntryImpl() {
        this.otherRules = Collections.emptyList();
        this.samFeatureSets = Collections.emptyList();
        this.positionFeatureSets = Collections.emptyList();
    }

    UniRuleEntryImpl(
            UniRuleId uniRuleId,
            Information information,
            RuleStatus ruleStatus,
            Rule mainRule,
            List<CaseRule> otherRules,
            List<SamFeatureSet> samFeatureSets,
            List<PositionFeatureSet> positionFeatureSets,
            Statistics statistics,
            String createdBy,
            String modifiedBy,
            LocalDate createdDate,
            LocalDate modifiedDate) {
        if (Objects.isNull(uniRuleId)) {
            throw new IllegalArgumentException("id is a mandatory parameter for a UniRule entry.");
        } else if (Objects.isNull(ruleStatus)) {
            throw new IllegalArgumentException(
                    "status is a mandatory parameter for a UniRule entry.");
        } else if (Objects.isNull(information)) {
            throw new IllegalArgumentException(
                    "information is a mandatory parameter for a UniRule entry.");
        } else if (Objects.isNull(mainRule)) {
            throw new IllegalArgumentException(
                    "mainRule is a mandatory parameter for a UniRule entry.");
        }
        this.uniRuleId = uniRuleId;
        this.information = information;
        this.ruleStatus = ruleStatus;
        this.mainRule = mainRule;
        this.otherRules = Utils.unmodifiableList(otherRules);
        this.samFeatureSets = Utils.unmodifiableList(samFeatureSets);
        this.positionFeatureSets = Utils.unmodifiableList(positionFeatureSets);
        this.statistics = statistics;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    @Override
    public UniRuleId getUniRuleId() {
        return this.uniRuleId;
    }

    @Override
    public Information getInformation() {
        return this.information;
    }

    @Override
    public RuleStatus getRuleStatus() {
        return this.ruleStatus;
    }

    @Override
    public Rule getMainRule() {
        return this.mainRule;
    }

    @Override
    public List<CaseRule> getOtherRules() {
        return this.otherRules;
    }

    @Override
    public List<SamFeatureSet> getSamFeatureSets() {
        return this.samFeatureSets;
    }

    @Override
    public List<PositionFeatureSet> getPositionFeatureSets() {
        return this.positionFeatureSets;
    }

    @Override
    public Statistics getStatistics() {
        return statistics;
    }

    @Override
    public String getCreatedBy() {
        return this.createdBy;
    }

    @Override
    public String getModifiedBy() {
        return this.modifiedBy;
    }

    @Override
    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    @Override
    public LocalDate getModifiedDate() {
        return this.modifiedDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof UniRuleEntry)) {
            return false;
        }

        UniRuleEntryImpl that = (UniRuleEntryImpl) obj;

        return Objects.equals(this.uniRuleId, that.uniRuleId)
                && Objects.equals(this.information, that.information)
                && Objects.equals(this.ruleStatus, that.ruleStatus)
                && Objects.equals(this.mainRule, that.mainRule)
                && Objects.equals(this.otherRules, that.otherRules)
                && Objects.equals(this.samFeatureSets, that.samFeatureSets)
                && Objects.equals(this.positionFeatureSets, that.positionFeatureSets)
                && Objects.equals(this.statistics, that.statistics)
                && Objects.equals(this.createdBy, that.createdBy)
                && Objects.equals(this.modifiedBy, that.modifiedBy)
                && Objects.equals(this.createdDate, that.createdDate)
                && Objects.equals(this.modifiedDate, that.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.uniRuleId,
                this.information,
                this.ruleStatus,
                this.mainRule,
                this.otherRules,
                this.samFeatureSets,
                this.positionFeatureSets,
                this.statistics,
                this.createdBy,
                this.modifiedBy,
                this.createdDate,
                this.modifiedDate);
    }
}
