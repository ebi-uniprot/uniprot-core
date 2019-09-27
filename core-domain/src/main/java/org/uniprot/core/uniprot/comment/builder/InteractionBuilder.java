package org.uniprot.core.uniprot.comment.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionType;
import org.uniprot.core.uniprot.comment.Interactor;
import org.uniprot.core.uniprot.comment.impl.InteractionImpl;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;

public final class InteractionBuilder implements Builder<InteractionBuilder, Interaction> {
    private InteractionType type;
    private UniProtAccession uniProtAccession;
    private String geneName;
    private int numberOfExperiments;
    private Interactor firstInteractor;
    private Interactor secondInteractor;

    public Interaction build() {
        return new InteractionImpl(
                type,
                uniProtAccession,
                geneName,
                numberOfExperiments,
                firstInteractor,
                secondInteractor);
    }

    @Override
    public InteractionBuilder from(Interaction instance) {
        return this.uniProtAccession(instance.getUniProtAccession())
                .geneName(instance.getGeneName())
                .interactionType(instance.getType())
                .numberOfExperiments(instance.getNumberOfExperiments())
                .firstInteractor(instance.getFirstInteractor().getValue())
                .secondInteractor(instance.getSecondInteractor().getValue());
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
        this.numberOfExperiments = nbExp;
        return this;
    }

    public InteractionBuilder firstInteractor(String firstInteractor) {
        this.firstInteractor = new InteractionImpl.InteractorImpl(firstInteractor);
        return this;
    }

    public InteractionBuilder secondInteractor(String secondInteractor) {
        this.secondInteractor = new InteractionImpl.InteractorImpl(secondInteractor);
        return this;
    }

    public InteractionBuilder uniProtAccession(UniProtAccession uniprotAccession) {
        this.uniProtAccession = uniprotAccession;
        return this;
    }

    public InteractionBuilder uniProtAccession(String uniProtAccession) {
        this.uniProtAccession = new UniProtAccessionImpl(uniProtAccession);
        return this;
    }
}
