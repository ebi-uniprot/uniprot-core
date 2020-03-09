package org.uniprot.core.uniprot.comment.impl;

import java.util.Objects;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionType;
import org.uniprot.core.uniprot.comment.Interactor;
import org.uniprot.core.util.Utils;

public class InteractionImpl implements Interaction {
    private static final long serialVersionUID = -1102213995267310387L;
    private InteractionType type;
    private UniProtAccession uniProtAccession;
    private String geneName;
    private int numberOfExperiments;
    private Interactor firstInteractor;
    private Interactor secondInteractor;

    // no arg constructor for JSON deserialization
    InteractionImpl() {}

    InteractionImpl(
            InteractionType type,
            UniProtAccession uniProtAccession,
            String geneName,
            int numberOfExperiments,
            Interactor firstInteractor,
            Interactor secondInteractor) {
        this.type = type;
        this.uniProtAccession = uniProtAccession;
        this.geneName = geneName;
        this.numberOfExperiments = numberOfExperiments;
        this.firstInteractor = firstInteractor;
        this.secondInteractor = secondInteractor;
    }

    @Override
    public InteractionType getType() {
        return type;
    }

    @Override
    public UniProtAccession getUniProtAccession() {
        return uniProtAccession;
    }

    @Override
    public String getGeneName() {
        return geneName;
    }

    @Override
    public int getNumberOfExperiments() {
        return numberOfExperiments;
    }

    @Override
    public Interactor getFirstInteractor() {
        return firstInteractor;
    }

    @Override
    public Interactor getSecondInteractor() {
        return secondInteractor;
    }

    @Override
    public boolean hasUniProtAccession() {
        return this.uniProtAccession != null
                && Utils.notNullNotEmpty(this.uniProtAccession.getValue());
    }

    @Override
    public boolean hasGeneName() {
        return Utils.notNullNotEmpty(this.geneName);
    }

    @Override
    public boolean hasNumberOfExperiments() {
        return this.numberOfExperiments > 0;
    }

    @Override
    public boolean hasFirstInteractor() {
        return this.firstInteractor != null
                && Utils.notNullNotEmpty(this.firstInteractor.getValue());
    }

    @Override
    public boolean hasSecondInteractor() {
        return this.secondInteractor != null
                && Utils.notNullNotEmpty(this.secondInteractor.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractionImpl that = (InteractionImpl) o;
        return numberOfExperiments == that.numberOfExperiments
                && type == that.type
                && Objects.equals(uniProtAccession, that.uniProtAccession)
                && Objects.equals(geneName, that.geneName)
                && Objects.equals(firstInteractor, that.firstInteractor)
                && Objects.equals(secondInteractor, that.secondInteractor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                type,
                uniProtAccession,
                geneName,
                numberOfExperiments,
                firstInteractor,
                secondInteractor);
    }

    public static class InteractorImpl extends ValueImpl implements Interactor {
        /** */
        private static final long serialVersionUID = 1L;

        // no arg constructor for JSON deserialization
        InteractorImpl() {
            super(null);
        }

        public InteractorImpl(String value) {
            super(value);
        }
    }
}
