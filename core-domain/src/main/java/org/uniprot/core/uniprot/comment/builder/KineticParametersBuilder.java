package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.comment.KineticParameters;
import org.uniprot.core.uniprot.comment.MaximumVelocity;
import org.uniprot.core.uniprot.comment.MichaelisConstant;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.impl.KineticParametersImpl;

import javax.annotation.Nonnull;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class KineticParametersBuilder
        implements Builder<KineticParametersBuilder, KineticParameters> {
    private List<MaximumVelocity> maximumVelocities = new ArrayList<>();
    private List<MichaelisConstant> michaelisConstants = new ArrayList<>();
    private Note note;

    public KineticParametersBuilder maximumVelocities(List<MaximumVelocity> maximumVelocities) {
        this.maximumVelocities = modifiableList(maximumVelocities);
        return this;
    }

    public KineticParametersBuilder addMaximumVelocitie(MaximumVelocity maximumVelocity) {
        addOrIgnoreNull(maximumVelocity, this.maximumVelocities);
        return this;
    }

    public KineticParametersBuilder michaelisConstants(List<MichaelisConstant> michaelisConstants) {
        this.michaelisConstants = modifiableList(michaelisConstants);
        return this;
    }

    public KineticParametersBuilder addMichaelisConstant(MichaelisConstant michaelisConstant) {
        addOrIgnoreNull(michaelisConstant, this.michaelisConstants);
        return this;
    }

    public KineticParametersBuilder note(Note note) {
        this.note = note;
        return this;
    }

    @Override
    public @Nonnull KineticParameters build() {
        return new KineticParametersImpl(maximumVelocities, michaelisConstants, note);
    }

    @Override
    public @Nonnull KineticParametersBuilder from(@Nonnull KineticParameters instance) {
        maximumVelocities.clear();
        michaelisConstants.clear();
        return this.maximumVelocities(instance.getMaximumVelocities())
                .michaelisConstants(instance.getMichaelisConstants())
                .note(instance.getNote());
    }
}
