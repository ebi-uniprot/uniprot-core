package uk.ac.ebi.uniprot.domain.uniprot.comments;

public interface Interaction extends Comment {

    public InteractionType getInteractionType();

    public InteractorUniProtAccession getInteractorUniProtAccession();

    public InteractionGeneName getInteractionGeneName();

    public int getNumberOfExperiments();

    public InteractorAccession getFirstInteractor();

    public InteractorAccession getSecondInteractor();
}
