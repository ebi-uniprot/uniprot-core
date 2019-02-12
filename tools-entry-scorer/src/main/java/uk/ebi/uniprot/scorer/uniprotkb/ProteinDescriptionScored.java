package uk.ebi.uniprot.scorer.uniprotkb;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.HasEvidences;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static uk.ac.ebi.uniprot.common.Utils.nonNullList;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 13:34:03 To change this template use File | Settings
 * | File Templates.
 */
public class ProteinDescriptionScored implements HasScore {
    private final ProteinDescription description;
    private final List<EvidenceType> evidenceTypes;

    public ProteinDescriptionScored(ProteinDescription description, List<EvidenceType> evidenceTypes) {
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

        // EC numbers avoid duplicate ECs
        int distinctECNumbersSize = getDistinctECs(description).size();

        score += distinctECNumbersSize * 3;

        // recname only in main section
        if (description.getRecommendedName() != null && hasProteinName(description.getRecommendedName())) {
            score += 5;
        }

        // altname only in main section
        for (ProteinName name : description.getAlternativeNames()) {
            if (hasProteinName(name)) {
                score += 2;
            }
        }
        return score;
    }

    private Set<EC> getDistinctECs(ProteinDescription description) {
        Set<EC> ecs = new HashSet<>();
        addECs(description.getRecommendedName(), ecs);
        addECs(description.getAlternativeNames(), ecs);
        description.getContains().forEach(proteinSection -> {
            addECs(proteinSection.getRecommendedName(), ecs);
            addECs(proteinSection.getAlternativeNames(), ecs);
        });
        description.getIncludes().forEach(proteinSection -> {
            addECs(proteinSection.getRecommendedName(), ecs);
            addECs(proteinSection.getAlternativeNames(), ecs);
        });
        return ecs;
    }

    private void addECs(List<ProteinName> proteinNames, Set<EC> ecs) {
        ecs.addAll(proteinNames.stream().map(ProteinName::getEcNumbers).filter(Objects::nonNull)
                           .flatMap(Collection::stream)
                           .collect(Collectors.toSet()));
    }

    private void addECs(ProteinName proteinName, Set<EC> ecs) {
        ecs.addAll(nonNullList(proteinName.getEcNumbers()));
    }


    private boolean hasProteinName(ProteinName name) {
        return ScoreUtil.hasEvidence(extractProteinNameEvidences(name), evidenceTypes);
    }

    private List<Evidence> extractProteinNameEvidences(ProteinName name) {
        List<Evidence> evs = new ArrayList<>();
        insertAllEvidences(name.getEcNumbers(), evs);
        insertAllEvidences(name.getShortNames(), evs);
        insertAllEvidences(name.getFullName(), evs);

        evs.addAll(name.getEcNumbers().stream().map(HasEvidences::getEvidences).flatMap(Collection::stream)
                           .collect(Collectors.toList()));

        return evs;
    }

    private <T extends HasEvidences> void insertAllEvidences(List<T> hasEvidences, List<Evidence> evidences) {
        evidences.addAll(hasEvidences.stream()
                                 .map(HasEvidences::getEvidences)
                                 .flatMap(Collection::stream)
                                 .collect(Collectors.toList()));
    }

    private <T extends HasEvidences> void insertAllEvidences(T hasEvidences, List<Evidence> evidences) {
        evidences.addAll(hasEvidences.getEvidences());
    }

    @Override
    public Consensus consensus() {
        return Consensus.COMPLEX;
    }
}
