package org.uniprot.core.uniprotkb.comment.impl;

import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.comment.Interaction;

import java.util.Objects;

public class InteractionImpl implements Interaction {
    private static final long serialVersionUID = -1102213995267310387L;
    private Interactant interactantOne;
    private Interactant interactantTwo;
    private int numberOfExperiments;
    private boolean organismDiffer;

    InteractionImpl() {}

    InteractionImpl(
            Interactant interactantOne,
            Interactant interactantTwo,
            int numberOfExperiments,
            boolean organismDiffer) {
        super();
        this.interactantOne = interactantOne;
        this.interactantTwo = interactantTwo;
        this.numberOfExperiments = numberOfExperiments;
        this.organismDiffer = organismDiffer;
    }

    @Override
    public Interactant getInteractantOne() {
        return this.interactantOne;
    }

    @Override
    public Interactant getInteractantTwo() {
        return interactantTwo;
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
        return Objects.hash(interactantOne, interactantTwo, numberOfExperiments, organismDiffer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractionImpl interaction = (InteractionImpl) o;
        return Objects.equals(interactantOne, interaction.interactantOne)
                && Objects.equals(interactantTwo, interaction.interactantTwo)
                && Objects.equals(numberOfExperiments, interaction.numberOfExperiments)
                && Objects.equals(organismDiffer, interaction.organismDiffer);
    }
}
