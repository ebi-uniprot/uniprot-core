package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.comment.KineticParameters;
import org.uniprot.core.uniprot.comment.MaximumVelocity;
import org.uniprot.core.uniprot.comment.MichaelisConstant;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.impl.KineticParametersImpl;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class KineticParametersBuilder implements Builder<KineticParameters> {
    private List<MaximumVelocity> maximumVelocities = new ArrayList<>();
    private List<MichaelisConstant> michaelisConstants = new ArrayList<>();
    private Note note;

    public @Nonnull KineticParametersBuilder maximumVelocitiesSet(
            List<MaximumVelocity> maximumVelocities) {
        this.maximumVelocities = modifiableList(maximumVelocities);
        return this;
    }

    public @Nonnull KineticParametersBuilder maximumVelocitiesAdd(MaximumVelocity maximumVelocity) {
        addOrIgnoreNull(maximumVelocity, this.maximumVelocities);
        return this;
    }

    public @Nonnull KineticParametersBuilder michaelisConstantsSet(
            List<MichaelisConstant> michaelisConstants) {
        this.michaelisConstants = modifiableList(michaelisConstants);
        return this;
    }

    public @Nonnull KineticParametersBuilder michaelisConstantsAdd(
            MichaelisConstant michaelisConstant) {
        addOrIgnoreNull(michaelisConstant, this.michaelisConstants);
        return this;
    }

    public @Nonnull KineticParametersBuilder note(Note note) {
        this.note = note;
        return this;
    }

    @Override
    public @Nonnull KineticParameters build() {
        return new KineticParametersImpl(maximumVelocities, michaelisConstants, note);
    }

    public static @Nonnull KineticParametersBuilder from(@Nonnull KineticParameters instance) {
        return new KineticParametersBuilder()
                .maximumVelocitiesSet(instance.getMaximumVelocities())
                .michaelisConstantsSet(instance.getMichaelisConstants())
                .note(instance.getNote());
    }
}
