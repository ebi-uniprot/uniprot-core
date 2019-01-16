package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Interactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ValueImpl;

import java.util.Objects;


public class InteractionImpl implements Interaction {
    private InteractionType type;
    private UniProtAccession uniProtAccession;
    private String geneName;
    private int numberOfExperiments;
    private Interactor firstInteractor;
    private Interactor secondInteractor;

    private InteractionImpl() {

    }

    public InteractionImpl(InteractionBuilder builder) {
        this.type = builder.getType();
        this.uniProtAccession = builder.getUniProtAccession();
        this.geneName = builder.getGeneName();
        this.numberOfExperiments = builder.getNumberOfExperiments();
        this.firstInteractor = builder.getFirstInteractor();
        this.secondInteractor = builder.getSecondInteractor();
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
