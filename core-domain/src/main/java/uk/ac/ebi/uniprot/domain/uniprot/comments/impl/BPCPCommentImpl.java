package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comments.BioPhysicoChemicalPropertiesComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PHDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comments.RedoxPotential;
import uk.ac.ebi.uniprot.domain.uniprot.comments.TemperatureDependence;

import java.util.List;
import java.util.Optional;

public class BPCPCommentImpl extends CommentImpl
        implements BioPhysicoChemicalPropertiesComment {
    public static PHDependence createPHDependence(List<EvidencedValue> texts) {
        return new PHDependenceImpl(texts);
    }

    public static RedoxPotential createRedoxPotential(List<EvidencedValue> texts) {
        return new RedoxPotentialImpl(texts);
    }

    public static TemperatureDependence createTemperatureDependence(List<EvidencedValue> texts) {
        return new TemperatureDependenceImpl(texts);
    }

    private final Optional<Absorption> absorption;
    private final Optional<KineticParameters> kineticParameters;
    private final Optional<PHDependence> phDependence;
    private final Optional<RedoxPotential> redoxPotential;
    private final Optional<TemperatureDependence> temperatureDependence;

    public BPCPCommentImpl(Absorption absorption,
        KineticParameters kineticParameters,
        PHDependence phDependence,
        RedoxPotential redoxPotential,
        TemperatureDependence temperatureDependence) {
        super(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        this.absorption = (absorption == null)? Optional.empty():  Optional.of(absorption);
        this.kineticParameters = (kineticParameters == null)? Optional.empty():  Optional.of(kineticParameters);
        this.phDependence = (phDependence == null)? Optional.empty():  Optional.of(phDependence);
        this.redoxPotential = (redoxPotential == null)? Optional.empty():  Optional.of(redoxPotential);
        this.temperatureDependence = (temperatureDependence == null)? Optional.empty():  Optional.of(temperatureDependence);

    }

    @Override
    public Optional<Absorption> getAbsorption() {
        return this.absorption;
    }


    @Override
    public Optional<PHDependence> getPHDependence() {
        return this.phDependence;

    }

    @Override
    public Optional<RedoxPotential> getRedoxPotential() {
        return this.redoxPotential;
    }

    @Override
    public Optional<TemperatureDependence> getTemperatureDependence() {
        return this.temperatureDependence;
    }

    @Override
    public Optional<KineticParameters> getKineticParameters() {
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
        if (getAbsorption().isPresent()) {
        		sb.append(getAbsorption().get());
        }

        if (getKineticParameters().isPresent()) {
            sb.append("\nCC       Kinetic parameters:");

            if (null != getKineticParameters().get().getMichaelisConstants()) {
                List<MichaelisConstant> michaelisConstants =
                        getKineticParameters().get().getMichaelisConstants();
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

            if (null != getKineticParameters().get().getMaximumVelocities()
                    &&
                    !getKineticParameters().get().getMaximumVelocities().isEmpty()) {
                List<MaximumVelocity> maximumVelocities =
                        getKineticParameters().get().getMaximumVelocities();
                for (MaximumVelocity maximumVelocity : maximumVelocities) {
                    sb.append("CC         Vmax=" +
                            maximumVelocity.getVelocity() +
                            " " +
                            maximumVelocity.getVelocityUnit() +
                            " " +
                            maximumVelocity.getEnzyme() +
                            ";");
                    sb.append("\n");

                }
            }

        }
        if (getPHDependence().isPresent()) {
            sb.append("\nCC       pH dependence:\nCC         ");
            sb.append(getPHDependence().get().toString());
            sb.append(";");

        }
        if (getRedoxPotential().isPresent()) {
            sb.append("\nCC       Redox potential:\nCC         ");
            sb.append(getRedoxPotential().get().toString());
            sb.append(";");

        }
        if (getTemperatureDependence().isPresent()) {
            sb.append("\nCC       Temperature dependence:\n");
            sb.append("CC         " +
                   getTemperatureDependence().get().toString() +
                    ";");

        }

        // commentsCounter++;

        return sb.toString();
    }

    static class RedoxPotentialImpl extends FreeTextImpl implements RedoxPotential {

        public RedoxPotentialImpl(List<EvidencedValue> texts) {
            super(texts);
        }
    }

    static class PHDependenceImpl extends FreeTextImpl implements PHDependence {

        public PHDependenceImpl(List<EvidencedValue> texts) {
            super(texts);
        }
    }

    static class TemperatureDependenceImpl extends FreeTextImpl implements TemperatureDependence {

        public TemperatureDependenceImpl(List<EvidencedValue> texts) {
            super(texts);
        }
    }

}
