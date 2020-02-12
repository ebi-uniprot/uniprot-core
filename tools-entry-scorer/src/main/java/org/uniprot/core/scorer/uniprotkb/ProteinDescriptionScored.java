package org.uniprot.core.scorer.uniprotkb;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.uniprot.core.util.Utils.emptyOrList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinAltName;
import org.uniprot.core.uniprot.description.ProteinDescription;
import org.uniprot.core.uniprot.description.ProteinRecName;
import org.uniprot.core.uniprot.description.ProteinSubName;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceType;
import org.uniprot.core.uniprot.evidence.HasEvidences;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 13:34:03 To change this template
 * use File | Settings | File Templates.
 */
public class ProteinDescriptionScored implements HasScore {
    private final ProteinDescription description;
    private final List<EvidenceType> evidenceTypes;

    public ProteinDescriptionScored(
            ProteinDescription description, List<EvidenceType> evidenceTypes) {
        this.description = description;
        this.evidenceTypes = evidenceTypes;
    }

    public ProteinDescriptionScored(ProteinDescription description) {
        this(description, null);
    }

    @Override
    public double score() {
        double score = 0;

        if (isNull(description)) {
            return score;
        }

        // distinct ECEntry numbers
        int distinctECNumbersSize = getDistinctECs(description).size();

        score += distinctECNumbersSize * 3;
        // recname only in main section
        if (description.hasRecommendedName()
                && hasValidEvidences(description.getRecommendedName())) {
            score += 5;
        }

        // altname only in main section
        for (ProteinAltName name : description.getAlternativeNames()) {
            if (hasValidEvidences(name)) {
                score += 2;
            }
        }
        if (isValidName(description.getAllergenName())) {
            score += 2;
        }
        if (isValidName(description.getBiotechName())) {
            score += 2;
        }
        if (description.hasCdAntigenNames()) {
            for (Name name : description.getCdAntigenNames()) {
                if (isValidName(name)) {
                    score += 2;
                }
            }
        }

        if (description.hasInnNames()) {
            for (Name name : description.getInnNames()) {
                if (isValidName(name)) {
                    score += 2;
                }
            }
        }

        return score;
    }

    private boolean isValidName(Name name) {
        if ((name == null) || !name.isValid()) {
            return false;
        }
        return ScoreUtil.hasEvidence(name.getEvidences(), evidenceTypes);
    }

    private boolean hasValidEvidences(ProteinAltName name) {

        List<Evidence> evidences = extractProteinAltNameEvidences(name);
        return ScoreUtil.hasEvidence(evidences, evidenceTypes);
    }

    private boolean hasValidEvidences(ProteinRecName name) {

        List<Evidence> evidences = extractProteinRecNameEvidences(name);
        return ScoreUtil.hasEvidence(evidences, evidenceTypes);
    }

    private Set<EC> getDistinctECs(ProteinDescription description) {
        Set<EC> ecs = new HashSet<>();
        addECsFromSubName(description.getSubmissionNames(), ecs);
        addECsFromRecName(description.getRecommendedName(), ecs);
        addECsFromAltName(description.getAlternativeNames(), ecs);
        description
                .getContains()
                .forEach(
                        proteinSection -> {
                            addECsFromRecName(proteinSection.getRecommendedName(), ecs);
                            addECsFromAltName(proteinSection.getAlternativeNames(), ecs);
                        });
        description
                .getIncludes()
                .forEach(
                        proteinSection -> {
                            addECsFromRecName(proteinSection.getRecommendedName(), ecs);
                            addECsFromAltName(proteinSection.getAlternativeNames(), ecs);
                        });
        return ecs;
    }

    private void addECsFromRecName(ProteinRecName proteinName, Set<EC> ecs) {
        if (nonNull(proteinName)) {
            ecs.addAll(emptyOrList(proteinName.getEcNumbers()));
        }
    }

    private void addECsFromAltName(List<ProteinAltName> proteinNames, Set<EC> ecs) {
        ecs.addAll(
                proteinNames.stream()
                        .map(ProteinAltName::getEcNumbers)
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet()));
    }

    private void addECsFromSubName(List<ProteinSubName> proteinNames, Set<EC> ecs) {
        ecs.addAll(
                proteinNames.stream()
                        .map(ProteinSubName::getEcNumbers)
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet()));
    }

    private List<Evidence> extractProteinAltNameEvidences(ProteinAltName name) {
        List<Evidence> evs = new ArrayList<>();
        insertAllEvidences(name.getEcNumbers(), evs);
        insertAllEvidences(name.getShortNames(), evs);
        insertAllEvidences(name.getFullName(), evs);

        evs.addAll(
                name.getEcNumbers().stream()
                        .map(HasEvidences::getEvidences)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()));

        return evs;
    }

    private List<Evidence> extractProteinRecNameEvidences(ProteinRecName name) {
        List<Evidence> evs = new ArrayList<>();
        insertAllEvidences(name.getEcNumbers(), evs);
        insertAllEvidences(name.getShortNames(), evs);
        insertAllEvidences(name.getFullName(), evs);

        evs.addAll(
                name.getEcNumbers().stream()
                        .map(HasEvidences::getEvidences)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()));

        return evs;
    }

    private <T extends HasEvidences> void insertAllEvidences(
            List<T> hasEvidences, List<Evidence> evidences) {
        evidences.addAll(
                hasEvidences.stream()
                        .map(HasEvidences::getEvidences)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()));
    }

    private <T extends HasEvidences> void insertAllEvidences(
            T hasEvidences, List<Evidence> evidences) {
        evidences.addAll(hasEvidences.getEvidences());
    }

    @Override
    public Consensus consensus() {
        return Consensus.COMPLEX;
    }
}
