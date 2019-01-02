package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;

public interface Interaction {

    public InteractionType getType();

    public UniProtAccession getUniProtAccession();

    public String getGeneName();

    public int getNumberOfExperiments();

    public Interactor getFirstInteractor();

    public Interactor getSecondInteractor();
}
