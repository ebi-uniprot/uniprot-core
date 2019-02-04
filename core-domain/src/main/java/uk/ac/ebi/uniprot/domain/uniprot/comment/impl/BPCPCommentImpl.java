package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BPCPCommentImpl extends CommentImpl implements BPCPComment {
    private Absorption absorption;
    private KineticParameters kineticParameters;
    private PhDependence phDependence;
    private RedoxPotential redoxPotential;
    private TemperatureDependence temperatureDependence;

    private BPCPCommentImpl() {
        super(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
    }

    public BPCPCommentImpl(Absorption absorption,
                           KineticParameters kineticParameters,
                           PhDependence phDependence,
                           RedoxPotential redoxPotential,
                           TemperatureDependence temperatureDependence) {
        super(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BPCPCommentImpl that = (BPCPCommentImpl) o;
        return Objects.equals(absorption, that.absorption) &&
                Objects.equals(kineticParameters, that.kineticParameters) &&
                Objects.equals(phDependence, that.phDependence) &&
                Objects.equals(redoxPotential, that.redoxPotential) &&
                Objects.equals(temperatureDependence, that.temperatureDependence);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(super.hashCode(), absorption, kineticParameters, phDependence, redoxPotential, temperatureDependence);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        // if (commentsCounter>0)
        sb.append("\n");
        sb.append("CC   -!- ");
        sb.append(getCommentType().toDisplayName());
        sb.append(":");
        if (getAbsorption() != null) {
            sb.append(getAbsorption());
        }

        if (getKineticParameters() != null) {
            sb.append("\nCC       Kinetic parameters:");

            if (null != getKineticParameters().getMichaelisConstants()) {
                List<MichaelisConstant> michaelisConstants = getKineticParameters().getMichaelisConstants();
                System.err.println("michaelisConstants.size() = " + michaelisConstants.size());
                for (MichaelisConstant michaelisConstant : michaelisConstants) {
                    StringBuilder temp = sb;
                    temp.append("\nCC         KM=");
                    temp.append(michaelisConstant.getConstant());
                    temp.append(" ");
                    temp.append(michaelisConstant.getUnit().toDisplayNameString());
                    temp.append(" for ");
                    temp.append(michaelisConstant.getSubstrate());
                    temp.append(";");
                    sb.append("\n");

                }
            }

            if (null != getKineticParameters().getMaximumVelocities()
                    && !getKineticParameters().getMaximumVelocities().isEmpty()) {
                List<MaximumVelocity> maximumVelocities = getKineticParameters().getMaximumVelocities();
                for (MaximumVelocity maximumVelocity : maximumVelocities) {
                    sb.append("CC         Vmax=" + maximumVelocity.getVelocity() + " "
                                      + maximumVelocity.getUnit() + " " + maximumVelocity.getEnzyme() + ";");
                    sb.append("\n");

                }
            }

        }
        if (getPhDependence() != null) {
            sb.append("\nCC       pH dependence:\nCC         ");
            sb.append(getPhDependence().toString());
            sb.append(";");

        }
        if (getRedoxPotential() != null) {
            sb.append("\nCC       Redox potential:\nCC         ");
            sb.append(getRedoxPotential().toString());
            sb.append(";");

        }
        if (getTemperatureDependence() != null) {
            sb.append("\nCC       Temperature dependence:\n");
            sb.append("CC         " + getTemperatureDependence().toString() + ";");

        }

        // commentsCounter++;

        return sb.toString();
    }

    public static class RedoxPotentialImpl extends FreeTextImpl implements RedoxPotential {

        private RedoxPotentialImpl() {
            super(Collections.emptyList());
        }

        public RedoxPotentialImpl(List<EvidencedValue> texts) {
            super(texts);
        }
    }

    public static class PhDependenceImpl extends FreeTextImpl implements PhDependence {

        private PhDependenceImpl() {
            super(Collections.emptyList());
        }

        public PhDependenceImpl(List<EvidencedValue> texts) {
            super(texts);
        }
    }

    public static class TemperatureDependenceImpl extends FreeTextImpl implements TemperatureDependence {

        private TemperatureDependenceImpl() {
            super(Collections.emptyList());
        }

        public TemperatureDependenceImpl(List<EvidencedValue> texts) {
            super(texts);
        }
    }
}
