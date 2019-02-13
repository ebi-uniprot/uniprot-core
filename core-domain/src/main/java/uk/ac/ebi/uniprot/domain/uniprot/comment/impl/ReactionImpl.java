package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ReactionImpl implements Reaction {
    private String name;
    private List<DBCrossReference<ReactionReferenceType>> reactionReferences;
    private ECNumber ecNumber;
    private List<Evidence> evidences;

    private ReactionImpl() {
        this.evidences = Collections.emptyList();
        this.reactionReferences = Collections.emptyList();
    }

    public ReactionImpl(String name,
                        List<DBCrossReference<ReactionReferenceType>> reactionReferences,
                        ECNumber ecNumber, List<Evidence> evidences) {
        this.name = name;
        if ((reactionReferences == null) || reactionReferences.isEmpty()) {
            this.reactionReferences = Collections.emptyList();
        } else {
            this.reactionReferences = Collections.unmodifiableList(reactionReferences);
        }
        this.ecNumber = ecNumber;
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(evidences);
        }
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notEmpty(this.evidences);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<DBCrossReference<ReactionReferenceType>> getReactionReferences() {
        return reactionReferences;
    }

    @Override
    public ECNumber getEcNumber() {
        return ecNumber;
    }

    @Override
    public boolean hasName() {
        return Utils.notEmpty(name);
    }

    @Override
    public boolean hasReactionReferences() {
        return Utils.notEmpty(this.reactionReferences);
    }

    @Override
    public boolean hasEcNumber() {
        return ecNumber != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reaction=").append(name).append(";");
        sb.append(" Xref=")
                .append(reactionReferences.stream().map(this::getReferenceString).collect(Collectors.joining(", ")))
                .append(";");
        if ((ecNumber != null) && ecNumber.isValid()) {
            sb.append(" EC=").append(ecNumber.getValue()).append(";");
        }
        if (!evidences.isEmpty()) {
            sb.append(" Evidence={")
                    .append(evidences.stream().map(val -> val.toString()).collect(Collectors.joining(", ")))
                    .append("};");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReactionImpl reaction = (ReactionImpl) o;
        return Objects.equals(name, reaction.name) &&
                Objects.equals(reactionReferences, reaction.reactionReferences) &&
                Objects.equals(ecNumber, reaction.ecNumber) &&
                Objects.equals(evidences, reaction.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, reactionReferences, ecNumber, evidences);
    }

    private String getReferenceString(DBCrossReference<ReactionReferenceType> ref) {
        return ref.getDatabaseType().getName() + ":" + ref.getId();
    }
}
