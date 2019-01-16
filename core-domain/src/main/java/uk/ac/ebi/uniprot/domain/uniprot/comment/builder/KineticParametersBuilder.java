package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.comment.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.KineticParametersImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class KineticParametersBuilder implements Builder2<KineticParametersBuilder, KineticParameters> {
    private List<MaximumVelocity> maximumVelocities = new ArrayList<>();
    private List<MichaelisConstant> michaelisConstants = new ArrayList<>();
    private Note note;

    public KineticParametersBuilder maximumVelocities(List<MaximumVelocity> maximumVelocities) {
        this.maximumVelocities.addAll(maximumVelocities);
        return this;
    }

    public KineticParametersBuilder addMaximumVelocitie(MaximumVelocity maximumVelocity) {
        this.maximumVelocities.add(maximumVelocity);
        return this;
    }

    public KineticParametersBuilder michaelisConstants(List<MichaelisConstant> michaelisConstants) {
        this.michaelisConstants.addAll(michaelisConstants);
        return this;
    }

    public KineticParametersBuilder addMichaelisConstant(MichaelisConstant michaelisConstant) {
        this.michaelisConstants.add(michaelisConstant);
        return this;
    }

    public KineticParametersBuilder note(Note note) {
        this.note = note;
        return this;
    }

    @Override
    public KineticParameters build() {
        return new KineticParametersImpl(this);
    }

    @Override
    public KineticParametersBuilder from(KineticParameters instance) {
        return new KineticParametersBuilder()
                .maximumVelocities(instance.getMaximumVelocities())
                .michaelisConstants(instance.getMichaelisConstants())
                .note(instance.getNote());
    }

    public List<MaximumVelocity> getMaximumVelocities() {
        return maximumVelocities;
    }

    public List<MichaelisConstant> getMichaelisConstants() {
        return michaelisConstants;
    }

    public Note getNote() {
        return note;
    }
}
