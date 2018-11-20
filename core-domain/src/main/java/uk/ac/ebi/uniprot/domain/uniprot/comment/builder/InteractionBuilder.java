package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.InteractionImpl;

public final class InteractionBuilder {
    private InteractionType type;
    private UniProtAccession uniprotAccession;
    private String geneName;
    private int nbExp;
    private Interactor firstInteractor;
    private Interactor secondInteractor;

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

    public InteractionBuilder firstInteractor(Interactor firstInteractor) {
        this.firstInteractor = firstInteractor;
        return this;
    }

    public InteractionBuilder secondInteractor(Interactor secondInteractor) {
        this.secondInteractor = secondInteractor;
        return this;
    }

    public InteractionBuilder uniProtAccession(UniProtAccession uniprotAccession) {
        this.uniprotAccession = uniprotAccession;
        return this;
    }

 
    public static Interactor createInteractor(String value) {
        return InteractionImpl.createInteractor(value);
    }
}
