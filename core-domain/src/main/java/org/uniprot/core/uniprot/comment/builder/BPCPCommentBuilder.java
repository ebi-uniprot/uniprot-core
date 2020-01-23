package org.uniprot.core.uniprot.comment.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.impl.BPCPCommentImpl;

public final class BPCPCommentBuilder implements CommentBuilder<BPCPComment> {
    private String molecule;
    private Absorption absorption = null;
    private KineticParameters kineticParameters = null;
    private PhDependence phDependence = null;
    private RedoxPotential redoxPotential = null;
    private TemperatureDependence temperatureDependence = null;

    public @Nonnull BPCPComment build() {
        return new BPCPCommentImpl(
                molecule,
                absorption,
                kineticParameters,
                phDependence,
                redoxPotential,
                temperatureDependence);
    }

    public static @Nonnull BPCPCommentBuilder from(@Nonnull BPCPComment instance) {
        return new BPCPCommentBuilder().kineticParameters(instance.getKineticParameters())
                .molecule(instance.getMolecule())
                .absorption(instance.getAbsorption())
                .phDependence(instance.getPhDependence())
                .redoxPotential(instance.getRedoxPotential())
                .temperatureDependence(instance.getTemperatureDependence());
    }

    public @Nonnull BPCPCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public @Nonnull BPCPCommentBuilder absorption(Absorption absorption) {
        this.absorption = absorption;
        return this;
    }

    public @Nonnull BPCPCommentBuilder kineticParameters(KineticParameters kineticParameters) {
        this.kineticParameters = kineticParameters;
        return this;
    }

    public @Nonnull BPCPCommentBuilder phDependence(PhDependence phDependence) {
        this.phDependence = phDependence;
        return this;
    }

    public @Nonnull BPCPCommentBuilder redoxPotential(RedoxPotential redoxPotential) {
        this.redoxPotential = redoxPotential;
        return this;
    }

    public @Nonnull BPCPCommentBuilder temperatureDependence(
            TemperatureDependence temperatureDependence) {
        this.temperatureDependence = temperatureDependence;
        return this;
    }
}
