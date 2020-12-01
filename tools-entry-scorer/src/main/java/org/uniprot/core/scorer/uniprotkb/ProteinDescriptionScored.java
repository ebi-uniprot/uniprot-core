package org.uniprot.core.scorer.uniprotkb;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.uniprot.core.util.Utils.emptyOrList;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.uniprotkb.description.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 13:34:03 To change this template
 * use File | Settings | File Templates.
 */
public class ProteinDescriptionScored implements HasScore {
    private final ProteinDescription description;
    private final List<EvidenceDatabase> evidenceDatabases;

    public ProteinDescriptionScored(
            ProteinDescription description, List<EvidenceDatabase> evidenceDatabases) {
        this.description = description;
        this.evidenceDatabases = evidenceDatabases;
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
        for (ProteinName name : description.getAlternativeNames()) {
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
        return ScoreUtil.hasEvidence(name.getEvidences(), evidenceDatabases);
    }

    private boolean hasValidEvidences(ProteinName name) {
        List<Evidence> evidences = extractProteinNameEvidences(name);
        return ScoreUtil.hasEvidence(evidences, evidenceDatabases);
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

    private void addECsFromRecName(ProteinName proteinName, Set<EC> ecs) {
        if (nonNull(proteinName)) {
            ecs.addAll(emptyOrList(proteinName.getEcNumbers()));
        }
    }

    private void addECsFromAltName(List<ProteinName> proteinNames, Set<EC> ecs) {
        ecs.addAll(
                proteinNames.stream()
                        .map(ProteinName::getEcNumbers)
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

    private List<Evidence> extractProteinNameEvidences(ProteinName name) {
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
