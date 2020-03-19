package org.uniprot.core.uniprotkb.comment.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.Interactant;

public final class InteractionBuilder implements Builder<Interaction> {

  private Interactant firstInteractant;
  private Interactant secondInteractant;
  private int numberOfExperiments;
  private boolean organismDiffer = false;

  public @Nonnull Interaction build() {
    return new InteractionImpl(firstInteractant, secondInteractant, numberOfExperiments, organismDiffer);
  }

  public static @Nonnull InteractionBuilder from(@Nonnull Interaction instance) {
    InteractionBuilder builder = new InteractionBuilder();
    builder
        .firstInteractant(instance.getFirstInteractant())
        .secondInteractant(instance.getSecondInteractant())
        .numberOfExperiments(instance.getNumberOfExperiments())
        .isOrganismDiffer(instance.isOrganismsDiffer());
    return builder;
  }

  public @Nonnull InteractionBuilder numberOfExperiments(int nbExp) {
    this.numberOfExperiments = nbExp;
    return this;
  }

  public @Nonnull InteractionBuilder firstInteractant(Interactant firstInteractant) {
    this.firstInteractant = firstInteractant;
    return this;
  }

  public @Nonnull InteractionBuilder secondInteractant(Interactant secondInteractant) {
    this.secondInteractant = secondInteractant;
    return this;
  }

  public @Nonnull InteractionBuilder isOrganismDiffer(boolean organismDiffer) {
    this.organismDiffer = organismDiffer;
    return this;
  }
}
