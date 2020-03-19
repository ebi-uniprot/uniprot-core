package org.uniprot.core.uniprotkb.comment.impl;

import java.util.Objects;

import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.Interactant;

public class InteractionImpl implements Interaction {
  private static final long serialVersionUID = -1102213995267310387L;
  private Interactant firstInteractor;
  private Interactant secondInteractor;
  private int numberOfExperiments;
  private boolean organismDiffer;

  InteractionImpl() {}

  InteractionImpl(
      Interactant firstInteractor,
      Interactant secondInteractor,
      int numberOfExperiments,
      boolean organismDiffer) {
    super();
    this.firstInteractor = firstInteractor;
    this.secondInteractor = secondInteractor;
    this.numberOfExperiments = numberOfExperiments;
    this.organismDiffer = organismDiffer;
  }

  @Override
  public Interactant getFirstInteractant() {
    return this.firstInteractor;
  }

  @Override
  public Interactant getSecondInteractant() {
    return secondInteractor;
  }

  @Override
  public int getNumberOfExperiments() {
    return this.numberOfExperiments;
  }

  @Override
  public boolean isOrganismsDiffer() {
    return this.organismDiffer;
  }

  @Override
  public int hashCode() {
	  return Objects.hash(firstInteractor, secondInteractor, numberOfExperiments, organismDiffer);
	
  }

  @Override
  public boolean equals(Object o) {
	  if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    InteractionImpl interaction = (InteractionImpl) o;
	    return Objects.equals(firstInteractor, interaction.firstInteractor)
	        && Objects.equals(secondInteractor, interaction.secondInteractor)
	        && Objects.equals(numberOfExperiments, interaction.numberOfExperiments)
	        && Objects.equals(organismDiffer, interaction.organismDiffer);
  }
}
