package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.impl.ECNumberImpl;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.ReactionReferenceType;
import org.uniprot.core.uniprot.comment.impl.ReactionImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class ReactionBuilder implements Builder<ReactionBuilder, Reaction> {
    private String name;
    private ECNumber ecNumber;
    private List<DBCrossReference<ReactionReferenceType>> reactionReferences = new ArrayList<>();
    private List<Evidence> evidences = new ArrayList<>();

    public @Nonnull ReactionBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull ReactionBuilder references(
            List<DBCrossReference<ReactionReferenceType>> reactionReferences) {
        this.reactionReferences = modifiableList(reactionReferences);
        return this;
    }

    public @Nonnull ReactionBuilder addReactionReference(
            DBCrossReference<ReactionReferenceType> reactionReference) {
        addOrIgnoreNull(reactionReference, this.reactionReferences);
        return this;
    }

    public @Nonnull ReactionBuilder ecNumber(String ecNumber) {
        this.ecNumber = new ECNumberImpl(ecNumber);
        return this;
    }

    public @Nonnull ReactionBuilder ecNumber(ECNumber ecNumber) {
        this.ecNumber = ecNumber;
        return this;
    }

    public @Nonnull ReactionBuilder evidences(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull ReactionBuilder addEvidence(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }

    public @Nonnull ReactionImpl build() {
        return new ReactionImpl(name, reactionReferences, ecNumber, evidences);
    }

    @Override
    public @Nonnull ReactionBuilder from(@Nonnull Reaction instance) {
        evidences.clear();
        reactionReferences.clear();
        return this.ecNumber(instance.getEcNumber())
                .evidences(instance.getEvidences())
                .name(instance.getName())
                .references(instance.getReactionReferences());
    }
}
