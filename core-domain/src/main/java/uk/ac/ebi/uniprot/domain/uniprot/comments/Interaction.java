package uk.ac.ebi.uniprot.domain.uniprot.comments;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;

public interface Interaction {

    public InteractionType getInteractionType();

    public UniProtAccession getInteractorUniProtAccession();

    public String getInteractionGeneName();

    public int getNumberOfExperiments();

    public InteractorAccession getFirstInteractor();

    public InteractorAccession getSecondInteractor();
}
