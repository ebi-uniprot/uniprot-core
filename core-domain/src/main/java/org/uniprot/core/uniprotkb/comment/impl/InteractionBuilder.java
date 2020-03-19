package org.uniprot.core.uniprotkb.comment.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.Interactant;

public final class InteractionBuilder implements Builder<Interaction> {

  private Interactant firstInteractor;
  private Interactant secondInteractor;
  private int numberOfExperiments;
  private boolean isXeno = false;

  public @Nonnull Interaction build() {
    return new InteractionImpl(firstInteractor, secondInteractor, numberOfExperiments, isXeno);
  }

  public static @Nonnull InteractionBuilder from(@Nonnull Interaction instance) {
    InteractionBuilder builder = new InteractionBuilder();
    builder
        .firstInteractor(instance.getFirstInteractant())
        .secondInteractor(instance.getSecondInteractant())
        .numberOfExperiments(instance.getNumberOfExperiments())
        .isXeno(instance.isXeno());
    return builder;
  }

  public @Nonnull InteractionBuilder numberOfExperiments(int nbExp) {
    this.numberOfExperiments = nbExp;
    return this;
  }

  public @Nonnull InteractionBuilder firstInteractor(Interactant firstInteractor) {
    this.firstInteractor = firstInteractor;
    return this;
  }

  public @Nonnull InteractionBuilder secondInteractor(Interactant secondInteractor) {
    this.secondInteractor = secondInteractor;
    return this;
  }

  public @Nonnull InteractionBuilder isXeno(boolean isXeno) {
    this.isXeno = isXeno;
    return this;
  }
}
