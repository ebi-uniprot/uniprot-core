package org.uniprot.core.uniprotkb.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

public class BPCPCommentImpl extends CommentHasMoleculeImpl implements BPCPComment {
    private static final long serialVersionUID = -8649046279103961092L;
    private Absorption absorption;
    private KineticParameters kineticParameters;
    private PhDependence phDependence;
    private RedoxPotential redoxPotential;
    private TemperatureDependence temperatureDependence;

    // no arg constructor for JSON deserialization
    BPCPCommentImpl() {
        super(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, null);
    }

    BPCPCommentImpl(
            String molecule,
            Absorption absorption,
            KineticParameters kineticParameters,
            PhDependence phDependence,
            RedoxPotential redoxPotential,
            TemperatureDependence temperatureDependence) {
        super(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, molecule);
        this.absorption = absorption;
        this.kineticParameters = kineticParameters;
        this.phDependence = phDependence;
        this.redoxPotential = redoxPotential;
        this.temperatureDependence = temperatureDependence;
    }

    @Override
    public Absorption getAbsorption() {
        return this.absorption;
    }

    @Override
    public PhDependence getPhDependence() {
        return this.phDependence;
    }

    @Override
    public RedoxPotential getRedoxPotential() {
        return this.redoxPotential;
    }

    @Override
    public TemperatureDependence getTemperatureDependence() {
        return this.temperatureDependence;
    }

    @Override
    public KineticParameters getKineticParameters() {
        return this.kineticParameters;
    }

    @Override
    public boolean hasAbsorption() {
        return this.absorption != null;
    }

    @Override
    public boolean hasKineticParameters() {
        return this.kineticParameters != null;
    }

    @Override
    public boolean hasPhDependence() {
        return this.phDependence != null;
    }

    @Override
    public boolean hasRedoxPotential() {
        return this.redoxPotential != null;
    }

    @Override
    public boolean hasTemperatureDependence() {
        return this.temperatureDependence != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BPCPCommentImpl that = (BPCPCommentImpl) o;
        return Objects.equals(absorption, that.absorption)
                && Objects.equals(kineticParameters, that.kineticParameters)
                && Objects.equals(phDependence, that.phDependence)
                && Objects.equals(redoxPotential, that.redoxPotential)
                && Objects.equals(temperatureDependence, that.temperatureDependence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                absorption,
                kineticParameters,
                phDependence,
                redoxPotential,
                temperatureDependence);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCommentType().getDisplayName());
        sb.append(":");

        if (hasAbsorption()) {
            sb.append("\nAbsorption:\n").append(getAbsorption().toString());
        }

        if (this.hasKineticParameters()) {
            sb.append("\nKinetic parameters:\n").append(getKineticParameters().toString());
        }
        if (this.hasPhDependence()) {
            sb.append("\npH dependence:\n").append(getPhDependence().toString());
        }
        if (this.hasRedoxPotential()) {
            sb.append("\nRedox potential:\n").append(getRedoxPotential().toString());
        }
        if (this.hasTemperatureDependence()) {
            sb.append("\nTemperature dependence:\n").append(getTemperatureDependence().toString());
        }
        return sb.toString();
    }

    public static class RedoxPotentialImpl extends FreeTextImpl implements RedoxPotential {

        /** */
        private static final long serialVersionUID = 4549243776349161129L;

        // no arg constructor for JSON deserialization
        RedoxPotentialImpl() {
            super(Collections.emptyList());
        }

        RedoxPotentialImpl(List<EvidencedValue> texts) {
            super(texts);
        }
    }

    public static class PhDependenceImpl extends FreeTextImpl implements PhDependence {

        /** */
        private static final long serialVersionUID = -5623099152387117244L;

        // no arg constructor for JSON deserialization
        PhDependenceImpl() {
            super(Collections.emptyList());
        }

        PhDependenceImpl(List<EvidencedValue> texts) {
            super(texts);
        }
    }

    public static class TemperatureDependenceImpl extends FreeTextImpl
            implements TemperatureDependence {

        /** */
        private static final long serialVersionUID = -2126530892957285968L;

        // no arg constructor for JSON deserialization
        TemperatureDependenceImpl() {
            super(Collections.emptyList());
        }

        TemperatureDependenceImpl(List<EvidencedValue> texts) {
            super(texts);
        }
    }
}
