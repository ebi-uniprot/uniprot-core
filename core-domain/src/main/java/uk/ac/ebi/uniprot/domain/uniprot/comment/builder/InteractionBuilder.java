package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractorAccession;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.InteractionImpl;

public final class InteractionBuilder {
    private InteractionType type;
    private UniProtAccession uniprotAccession;
    private String geneName;
    private int nbExp;
    private InteractorAccession firstInteractor;
    private InteractorAccession secondInteractor;

    public static InteractionBuilder newInstance(){
        return new InteractionBuilder();
    }
    public Interaction build() {
        return new InteractionImpl(type, uniprotAccession,
                geneName, nbExp,
                firstInteractor, secondInteractor);
    }

    public InteractionBuilder interactionType(InteractionType type) {
        this.type = type;
        return this;
    }

    public InteractionBuilder geneName(String geneName) {
        this.geneName = geneName;
        return this;
    }

    public InteractionBuilder numberOfExperiments(int nbExp) {
        this.nbExp = nbExp;
        return this;
    }

    public InteractionBuilder firstInteractor(InteractorAccession firstInteractor) {
        this.firstInteractor = firstInteractor;
        return this;
    }

    public InteractionBuilder secondInteractor(InteractorAccession secondInteractor) {
        this.secondInteractor = secondInteractor;
        return this;
    }

    public InteractionBuilder uniProtAccession(UniProtAccession uniprotAccession) {
        this.uniprotAccession = uniprotAccession;
        return this;
    }

 
    public static InteractorAccession createInteractorAccession(String value) {
        return InteractionImpl.createInteractorAccession(value);
    }
}
