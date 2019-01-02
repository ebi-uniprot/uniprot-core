package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.impl.FreeTextImpl;

import java.util.Collections;
import java.util.List;

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

    public static PhDependence createPHDependence(List<EvidencedValue> texts) {
        return new PhDependenceImpl(texts);
    }

    public static RedoxPotential createRedoxPotential(List<EvidencedValue> texts) {
        return new RedoxPotentialImpl(texts);
    }

    public static TemperatureDependence createTemperatureDependence(List<EvidencedValue> texts) {
        return new TemperatureDependenceImpl(texts);
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
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((absorption == null) ? 0 : absorption.hashCode());
        result = prime * result + ((kineticParameters == null) ? 0 : kineticParameters.hashCode());
        result = prime * result + ((phDependence == null) ? 0 : phDependence.hashCode());
        result = prime * result + ((redoxPotential == null) ? 0 : redoxPotential.hashCode());
        result = prime * result + ((temperatureDependence == null) ? 0 : temperatureDependence.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        BPCPCommentImpl other = (BPCPCommentImpl) obj;
        if (absorption == null) {
            if (other.absorption != null)
                return false;
        } else if (!absorption.equals(other.absorption))
            return false;
        if (kineticParameters == null) {
            if (other.kineticParameters != null)
                return false;
        } else if (!kineticParameters.equals(other.kineticParameters))
            return false;
        if (phDependence == null) {
            if (other.phDependence != null)
                return false;
        } else if (!phDependence.equals(other.phDependence))
            return false;
        if (redoxPotential == null) {
            if (other.redoxPotential != null)
                return false;
        } else if (!redoxPotential.equals(other.redoxPotential))
            return false;
        if (temperatureDependence == null) {
            if (other.temperatureDependence != null)
                return false;
        } else if (!temperatureDependence.equals(other.temperatureDependence))
            return false;
        return true;
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
