package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.impl.ValueImpl;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interactor;

import java.util.Objects;


public class InteractionImpl implements Interaction {
    private static final long serialVersionUID = -1102213995267310387L;
    private InteractionType type;
    private UniProtAccession uniProtAccession;
    private String geneName;
    private int numberOfExperiments;
    private Interactor firstInteractor;
    private Interactor secondInteractor;

    private InteractionImpl() {

    }

    public InteractionImpl(InteractionType type,
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
        return this.uniProtAccession != null && Utils.notEmpty(this.uniProtAccession.getValue());
    }

    @Override
    public boolean hasGeneName() {
        return Utils.notEmpty(this.geneName);
    }

    @Override
    public boolean hasNumberOfExperiments() {
        return this.numberOfExperiments > 0;
    }

    @Override
    public boolean hasFirstInteractor() {
        return this.firstInteractor != null && Utils.notEmpty(this.firstInteractor.getValue());
    }

    @Override
    public boolean hasSecondInteractor() {
        return this.secondInteractor != null && Utils.notEmpty(this.secondInteractor.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractionImpl that = (InteractionImpl) o;
        return numberOfExperiments == that.numberOfExperiments &&
                type == that.type &&
                Objects.equals(uniProtAccession, that.uniProtAccession) &&
                Objects.equals(geneName, that.geneName) &&
                Objects.equals(firstInteractor, that.firstInteractor) &&
                Objects.equals(secondInteractor, that.secondInteractor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, uniProtAccession, geneName, numberOfExperiments, firstInteractor, secondInteractor);
    }

    public static class InteractorImpl extends ValueImpl implements Interactor {
        private InteractorImpl() {
            super(null);
        }

        public InteractorImpl(String value) {
            super(value);
        }
    }
}
