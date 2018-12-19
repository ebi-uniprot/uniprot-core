package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interactor;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ValueImpl;


public class InteractionImpl implements Interaction {

    private InteractionType type;
    private UniProtAccession uniProtAccession;
    private String geneName;
    private int numberOfExperiments;
    private Interactor firstInteractor;
    private Interactor secondInteractor;

    private InteractionImpl() {

    }

    public InteractionImpl(
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

    public static Interactor createInteractor(String value) {
        return new InteractorImpl(value);
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstInteractor == null) ? 0 : firstInteractor.hashCode());
        result = prime * result + ((geneName == null) ? 0 : geneName.hashCode());
        result = prime * result + numberOfExperiments;
        result = prime * result + ((secondInteractor == null) ? 0 : secondInteractor.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((uniProtAccession == null) ? 0 : uniProtAccession.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InteractionImpl other = (InteractionImpl) obj;
        if (firstInteractor == null) {
            if (other.firstInteractor != null)
                return false;
        } else if (!firstInteractor.equals(other.firstInteractor))
            return false;
        if (geneName == null) {
            if (other.geneName != null)
                return false;
        } else if (!geneName.equals(other.geneName))
            return false;
        if (numberOfExperiments != other.numberOfExperiments)
            return false;
        if (secondInteractor == null) {
            if (other.secondInteractor != null)
                return false;
        } else if (!secondInteractor.equals(other.secondInteractor))
            return false;
        if (type != other.type)
            return false;
        if (uniProtAccession == null) {
            if (other.uniProtAccession != null)
                return false;
        } else if (!uniProtAccession.equals(other.uniProtAccession))
            return false;
        return true;
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
