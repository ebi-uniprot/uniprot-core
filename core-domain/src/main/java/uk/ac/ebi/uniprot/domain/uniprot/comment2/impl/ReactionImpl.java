package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.ReactionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ReactionImpl implements Reaction, Serializable {
    private static final long serialVersionUID = 6812355712639887613L;
    private String name;
    private List<DBCrossReference<ReactionReferenceType>> reactionReferences;
    private ECNumber ecNumber;
    private List<Evidence> evidences;

    private ReactionImpl() {
        this.evidences = Collections.emptyList();
        this.reactionReferences = Collections.emptyList();
    }

    public ReactionImpl(ReactionBuilder builder) {
        this.name = builder.getName();
        if ((builder.getReactionReferences() == null) || builder.getReactionReferences().isEmpty()) {
            this.reactionReferences = Collections.emptyList();
        } else {
            this.reactionReferences = Collections.unmodifiableList(builder.getReactionReferences());
        }
        this.ecNumber = builder.getEcNumber();
        if ((builder.getEvidences() == null) || builder.getEvidences().isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(builder.getEvidences());
        }
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
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
