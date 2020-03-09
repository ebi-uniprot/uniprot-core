package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.ReactionDatabase;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class ReactionImpl implements Reaction {
    private static final long serialVersionUID = 7533995250813372108L;
    private String name;
    private List<CrossReference<ReactionDatabase>> reactionCrossReferences;
    private ECNumber ecNumber;
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    ReactionImpl() {
        this.evidences = Collections.emptyList();
        this.reactionCrossReferences = Collections.emptyList();
    }

    ReactionImpl(
            String name,
            List<CrossReference<ReactionDatabase>> reactionCrossReferences,
            ECNumber ecNumber,
            List<Evidence> evidences) {
        this.name = name;
        this.reactionCrossReferences = Utils.unmodifiableList(reactionCrossReferences);
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
    public List<CrossReference<ReactionDatabase>> getReactionCrossReferences() {
        return reactionCrossReferences;
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
    public boolean hasReactionCrossReferences() {
        return Utils.notNullNotEmpty(this.reactionCrossReferences);
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
                        reactionCrossReferences.stream()
                                .map(this::getCrossReferenceString)
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
                && Objects.equals(reactionCrossReferences, reaction.reactionCrossReferences)
                && Objects.equals(ecNumber, reaction.ecNumber)
                && Objects.equals(evidences, reaction.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, reactionCrossReferences, ecNumber, evidences);
    }

    private String getCrossReferenceString(CrossReference<ReactionDatabase> ref) {
        return ref.getDatabase().getName() + ":" + ref.getId();
    }
}
