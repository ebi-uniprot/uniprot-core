package org.uniprot.core.uniprot.comment.builder;

import static java.util.Objects.nonNull;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionType;
import org.uniprot.core.uniprot.comment.Interactor;
import org.uniprot.core.uniprot.comment.impl.InteractionImpl;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;

public final class InteractionBuilder implements Builder<Interaction> {
    private InteractionType type;
    private UniProtAccession uniProtAccession;
    private String geneName;
    private int numberOfExperiments;
    private Interactor firstInteractor;
    private Interactor secondInteractor;

    public @Nonnull Interaction build() {
        return new InteractionImpl(
                type,
                uniProtAccession,
                geneName,
                numberOfExperiments,
                firstInteractor,
                secondInteractor);
    }

    public static @Nonnull InteractionBuilder from(@Nonnull Interaction instance) {
        InteractionBuilder builder = new InteractionBuilder();
        builder.uniProtAccession(instance.getUniProtAccession())
                .geneName(instance.getGeneName())
                .interactionType(instance.getType())
                .numberOfExperiments(instance.getNumberOfExperiments());
        if (nonNull(instance.getFirstInteractor()))
            builder.firstInteractor(instance.getFirstInteractor().getValue());
        if (nonNull((instance.getSecondInteractor())))
            builder.secondInteractor(instance.getSecondInteractor().getValue());
        return builder;
    }

    public @Nonnull InteractionBuilder interactionType(InteractionType type) {
        this.type = type;
        return this;
    }

    public @Nonnull InteractionBuilder geneName(String geneName) {
        this.geneName = geneName;
        return this;
    }

    public @Nonnull InteractionBuilder numberOfExperiments(int nbExp) {
        this.numberOfExperiments = nbExp;
        return this;
    }

    public @Nonnull InteractionBuilder firstInteractor(String firstInteractor) {
        this.firstInteractor = new InteractionImpl.InteractorImpl(firstInteractor);
        return this;
    }

    public @Nonnull InteractionBuilder secondInteractor(String secondInteractor) {
        this.secondInteractor = new InteractionImpl.InteractorImpl(secondInteractor);
        return this;
    }

    public @Nonnull InteractionBuilder uniProtAccession(UniProtAccession uniprotAccession) {
        this.uniProtAccession = uniprotAccession;
        return this;
    }

    public @Nonnull InteractionBuilder uniProtAccession(String uniProtAccession) {
        this.uniProtAccession = new UniProtAccessionImpl(uniProtAccession);
        return this;
    }
}
