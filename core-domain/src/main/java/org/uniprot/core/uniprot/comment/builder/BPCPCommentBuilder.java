package org.uniprot.core.uniprot.comment.builder;

import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.impl.BPCPCommentImpl;

public final class BPCPCommentBuilder implements CommentBuilder<BPCPCommentBuilder, BPCPComment> {
    private Absorption absorption = null;
    private KineticParameters kineticParameters = null;
    private PhDependence phDependence = null;
    private RedoxPotential redoxPotential = null;
    private TemperatureDependence temperatureDependence = null;

    public BPCPComment build() {
        return new BPCPCommentImpl(
                absorption, kineticParameters, phDependence, redoxPotential, temperatureDependence);
    }

    @Override
    public BPCPCommentBuilder from(BPCPComment instance) {
        return this.kineticParameters(instance.getKineticParameters())
                .absorption(instance.getAbsorption())
                .phDependence(instance.getPhDependence())
                .redoxPotential(instance.getRedoxPotential())
                .temperatureDependence(instance.getTemperatureDependence());
    }

    public BPCPCommentBuilder absorption(Absorption absorption) {
        this.absorption = absorption;
        return this;
    }

    public BPCPCommentBuilder kineticParameters(KineticParameters kineticParameters) {
        this.kineticParameters = kineticParameters;
        return this;
    }

    public BPCPCommentBuilder phDependence(PhDependence phDependence) {
        this.phDependence = phDependence;
        return this;
    }

    public BPCPCommentBuilder redoxPotential(RedoxPotential redoxPotential) {
        this.redoxPotential = redoxPotential;
        return this;
    }

    public BPCPCommentBuilder temperatureDependence(TemperatureDependence temperatureDependence) {
        this.temperatureDependence = temperatureDependence;
        return this;
    }
}
