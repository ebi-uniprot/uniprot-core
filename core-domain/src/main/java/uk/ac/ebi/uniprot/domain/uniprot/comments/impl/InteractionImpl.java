package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comments.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.InteractorAccession;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ValueImpl;


public class InteractionImpl implements Interaction {
    public static InteractorAccession createInteractorAccession(String value){
        return new InteractorAccessionImpl(value);
    }
    private final InteractionType type;
    private final UniProtAccession uniprotAccession;
    private final String geneName;
    private final int nbExp;
    private final InteractorAccession firstInteractor;
    private final InteractorAccession secondInteractor;
   
    public InteractionImpl(InteractionType type, UniProtAccession uniprotAccession,
            String geneName, int nbExp,
            InteractorAccession firstInteractor, InteractorAccession secondInteractor){
        this.type =type;
        this.uniprotAccession = uniprotAccession;
        this.geneName = geneName;
        this.nbExp = nbExp;
        this.firstInteractor = firstInteractor;
        this.secondInteractor = secondInteractor;
        
    }

    @Override
    public InteractionType getInteractionType() {
        return type;
    }

    @Override
    public UniProtAccession getInteractorUniProtAccession() {
        return uniprotAccession;
    }

    @Override
    public String getInteractionGeneName() {
        return geneName;
    }

    @Override
    public int getNumberOfExperiments() {
        return nbExp;
    }

    @Override
    public InteractorAccession getFirstInteractor() {
       return firstInteractor;
    }

    @Override
    public InteractorAccession getSecondInteractor() {
        return secondInteractor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstInteractor == null) ? 0 : firstInteractor.hashCode());
        result = prime * result + ((geneName == null) ? 0 : geneName.hashCode());
        result = prime * result + nbExp;
        result = prime * result + ((secondInteractor == null) ? 0 : secondInteractor.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((uniprotAccession == null) ? 0 : uniprotAccession.hashCode());
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
        if (nbExp != other.nbExp)
            return false;
        if (secondInteractor == null) {
            if (other.secondInteractor != null)
                return false;
        } else if (!secondInteractor.equals(other.secondInteractor))
            return false;
        if (type != other.type)
            return false;
        if (uniprotAccession == null) {
            if (other.uniprotAccession != null)
                return false;
        } else if (!uniprotAccession.equals(other.uniprotAccession))
            return false;
        return true;
    }

    static class InteractorAccessionImpl extends ValueImpl implements InteractorAccession{

        public InteractorAccessionImpl(String value) {
            super(value);
        }
    }
}
