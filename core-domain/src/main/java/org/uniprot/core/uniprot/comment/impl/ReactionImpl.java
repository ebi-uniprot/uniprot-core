package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.ReactionReferenceType;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class ReactionImpl implements Reaction {
    private static final long serialVersionUID = 7533995250813372108L;
    private String name;
    private List<DBCrossReference<ReactionReferenceType>> reactionReferences;
    private ECNumber ecNumber;
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    ReactionImpl() {
        this.evidences = Collections.emptyList();
        this.reactionReferences = Collections.emptyList();
    }

    public ReactionImpl(
            String name,
            List<DBCrossReference<ReactionReferenceType>> reactionReferences,
            ECNumber ecNumber,
            List<Evidence> evidences) {
        this.name = name;
        this.reactionReferences = Utils.unmodifiableList(reactionReferences);
        this.evidences = Utils.unmodifiableList(evidences);
        this.ecNumber = ecNumber;
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullNotEmpty(this.evidences);
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
        return Utils.notNullNotEmpty(name);
    }

    @Override
    public boolean hasReactionReferences() {
        return Utils.notNullNotEmpty(this.reactionReferences);
    }

    @Override
    public boolean hasEcNumber() {
        return ecNumber != null && Utils.notNullNotEmpty(ecNumber.getValue());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reaction=").append(name).append(";");
        sb.append(" Xref=")
                .append(
                        reactionReferences.stream()
                                .map(this::getReferenceString)
                                .collect(Collectors.joining(", ")))
                .append(";");
        if ((ecNumber != null) && ecNumber.isValid()) {
            sb.append(" EC=").append(ecNumber.getValue()).append(";");
        }
        if (!evidences.isEmpty()) {
            sb.append(" Evidence={")
                    .append(
                            evidences.stream()
                                    .map(val -> val.toString())
                                    .collect(Collectors.joining(", ")))
                    .append("};");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReactionImpl reaction = (ReactionImpl) o;
        return Objects.equals(name, reaction.name)
                && Objects.equals(reactionReferences, reaction.reactionReferences)
                && Objects.equals(ecNumber, reaction.ecNumber)
                && Objects.equals(evidences, reaction.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, reactionReferences, ecNumber, evidences);
    }

    private String getReferenceString(DBCrossReference<ReactionReferenceType> ref) {
        return ref.getDatabaseType().getName() + ":" + ref.getId();
    }
}
