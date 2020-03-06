package org.uniprot.core.uniprot.comment.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.CrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.impl.ECNumberBuilder;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.ReactionDatabase;
import org.uniprot.core.uniprot.evidence.Evidence;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class ReactionBuilder implements Builder<Reaction> {
    private String name;
    private ECNumber ecNumber;
    private List<CrossReference<ReactionDatabase>> reactionCrossReferences = new ArrayList<>();
    private List<Evidence> evidences = new ArrayList<>();

    public @Nonnull ReactionBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull ReactionBuilder reactionCrossReferencesSet(
            List<CrossReference<ReactionDatabase>> reactionCrossReferences) {
        this.reactionCrossReferences = modifiableList(reactionCrossReferences);
        return this;
    }

    public @Nonnull ReactionBuilder reactionCrossReferencesAdd(
            CrossReference<ReactionDatabase> reactionCrossReference) {
        addOrIgnoreNull(reactionCrossReference, this.reactionCrossReferences);
        return this;
    }

    public @Nonnull ReactionBuilder ecNumber(String ecNumber) {
        this.ecNumber = new ECNumberBuilder(ecNumber).build();
        return this;
    }

    public @Nonnull ReactionBuilder ecNumber(ECNumber ecNumber) {
        this.ecNumber = ecNumber;
        return this;
    }

    public @Nonnull ReactionBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull ReactionBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }

    public @Nonnull ReactionImpl build() {
        return new ReactionImpl(name, reactionCrossReferences, ecNumber, evidences);
    }

    public static @Nonnull ReactionBuilder from(@Nonnull Reaction instance) {
        return new ReactionBuilder()
                .ecNumber(instance.getEcNumber())
                .evidencesSet(instance.getEvidences())
                .name(instance.getName())
                .reactionCrossReferencesSet(instance.getReactionCrossReferences());
    }
}
