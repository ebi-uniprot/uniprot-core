package org.uniprot.core.uniprotkb.comment.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.comment.Interaction;

public final class InteractionBuilder implements Builder<Interaction> {

    private Interactant interactantOne;
    private Interactant interactantTwo;
    private int numberOfExperiments;
    private boolean organismDiffer = false;

    public @Nonnull Interaction build() {
        return new InteractionImpl(
                interactantOne, interactantTwo, numberOfExperiments, organismDiffer);
    }

    public static @Nonnull InteractionBuilder from(@Nonnull Interaction instance) {
        InteractionBuilder builder = new InteractionBuilder();
        builder.interactantOne(instance.getInteractantOne())
                .interactantTwo(instance.getInteractantTwo())
                .numberOfExperiments(instance.getNumberOfExperiments())
                .isOrganismDiffer(instance.isOrganismDiffer());
        return builder;
    }

    public @Nonnull InteractionBuilder numberOfExperiments(int nbExp) {
        this.numberOfExperiments = nbExp;
        return this;
    }

    public @Nonnull InteractionBuilder interactantOne(Interactant interactantOne) {
        this.interactantOne = interactantOne;
        return this;
    }

    public @Nonnull InteractionBuilder interactantTwo(Interactant interactantTwo) {
        this.interactantTwo = interactantTwo;
        return this;
    }

    public @Nonnull InteractionBuilder isOrganismDiffer(boolean organismDiffer) {
        this.organismDiffer = organismDiffer;
        return this;
    }
}
