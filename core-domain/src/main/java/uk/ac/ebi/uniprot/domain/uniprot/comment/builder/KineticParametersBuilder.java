package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.KineticParametersImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class KineticParametersBuilder implements Builder<KineticParametersBuilder, KineticParameters> {
    private List<MaximumVelocity> maximumVelocities = new ArrayList<>();
    private List<MichaelisConstant> michaelisConstants = new ArrayList<>();
    private Note note;

    public KineticParametersBuilder maximumVelocities(List<MaximumVelocity> maximumVelocities) {
        this.maximumVelocities = nonNullList(maximumVelocities);
        return this;
    }

    public KineticParametersBuilder addMaximumVelocitie(MaximumVelocity maximumVelocity) {
        nonNullAdd(maximumVelocity, this.maximumVelocities);
        return this;
    }

    public KineticParametersBuilder michaelisConstants(List<MichaelisConstant> michaelisConstants) {
        this.michaelisConstants = nonNullList(michaelisConstants);
        return this;
    }

    public KineticParametersBuilder addMichaelisConstant(MichaelisConstant michaelisConstant) {
        nonNullAdd(michaelisConstant, this.michaelisConstants);
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
        maximumVelocities.clear();
        michaelisConstants.clear();
        return this
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
