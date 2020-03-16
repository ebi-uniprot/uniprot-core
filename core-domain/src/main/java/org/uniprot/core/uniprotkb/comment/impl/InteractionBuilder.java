package org.uniprot.core.uniprotkb.comment.impl;

import static java.util.Objects.nonNull;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.UniProtkbAccession;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionType;
import org.uniprot.core.uniprotkb.comment.Interactor;
import org.uniprot.core.uniprotkb.impl.UniProtkbAccessionBuilder;

public final class InteractionBuilder implements Builder<Interaction> {
    private InteractionType type;
    private UniProtkbAccession uniProtkbAccession;
    private String geneName;
    private int numberOfExperiments;
    private Interactor firstInteractor;
    private Interactor secondInteractor;

    public @Nonnull Interaction build() {
        return new InteractionImpl(
                type,
                uniProtkbAccession,
                geneName,
                numberOfExperiments,
                firstInteractor,
                secondInteractor);
    }

    public static @Nonnull InteractionBuilder from(@Nonnull Interaction instance) {
        InteractionBuilder builder = new InteractionBuilder();
        builder.uniProtAccession(instance.getUniProtkbAccession())
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

    public @Nonnull InteractionBuilder uniProtAccession(UniProtkbAccession uniprotAccession) {
        this.uniProtkbAccession = uniprotAccession;
        return this;
    }

    public @Nonnull InteractionBuilder uniProtAccession(String uniProtAccession) {
        this.uniProtkbAccession = new UniProtkbAccessionBuilder(uniProtAccession).build();
        return this;
    }
}
