package uk.ac.ebi.uniprot.domain.uniprot.comment2.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Interactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.impl.InteractionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtAccessionImpl;

public final class InteractionBuilder implements Builder2<InteractionBuilder, Interaction> {
    private InteractionType type;
    private UniProtAccession uniProtAccession;
    private String geneName;
    private int numberOfExperiments;
    private Interactor firstInteractor;
    private Interactor secondInteractor;

    public Interaction build() {
        return new InteractionImpl(this);
    }

    @Override
    public InteractionBuilder from(Interaction instance) {
        return new InteractionBuilder()
                .uniProtAccession(instance.getUniProtAccession())
                .geneName(instance.getGeneName())
                .interactionType(instance.getType())
                .numberOfExperiments(instance.getNumberOfExperiments())
                .firstInteractor(instance.getFirstInteractor())
                .secondInteractor(instance.getSecondInteractor());
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

    public InteractionBuilder firstInteractor(Interactor firstInteractor) {
        this.firstInteractor = firstInteractor;
        return this;
    }

    public InteractionBuilder secondInteractor(Interactor secondInteractor) {
        this.secondInteractor = secondInteractor;
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

    public InteractionType getType() {
        return type;
    }

    public UniProtAccession getUniProtAccession() {
        return uniProtAccession;
    }

    public String getGeneName() {
        return geneName;
    }

    public int getNumberOfExperiments() {
        return numberOfExperiments;
    }

    public Interactor getFirstInteractor() {
        return firstInteractor;
    }

    public Interactor getSecondInteractor() {
        return secondInteractor;
    }
}
