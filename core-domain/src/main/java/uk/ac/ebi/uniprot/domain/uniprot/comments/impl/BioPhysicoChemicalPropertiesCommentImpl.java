package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

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

public class BioPhysicoChemicalPropertiesCommentImpl extends CommentImpl
        implements BioPhysicoChemicalPropertiesComment {

    private Absorption absorption;
    private PHDependence phDependence;
    private RedoxPotential redoxPotential;
    private TemperatureDependence temperatureDependence;
    private KineticParameters kineticParameters;

    public BioPhysicoChemicalPropertiesCommentImpl() {
        this.setCommentType(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        this.absorption = new AbsorptionImpl();
        this.redoxPotential = new RedoxPotentialImpl();
    }

    @Override
    public Absorption getAbsorption() {
        return this.absorption;
    }

    public void setAbsorption(Absorption absorption) {
        this.absorption = absorption;
    }

    @Override
    public boolean hasAbsorptionProperty() {
        if (this.absorption == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public PHDependence getPHDependence() {
        return this.phDependence;

    }

    public void setPHDepencence(PHDependence phDependence) {
        this.phDependence = phDependence;
    }

    @Override
    public boolean hasPHDependenceProperty() {
        if (this.phDependence == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public RedoxPotential getRedoxPotential() {
        return this.redoxPotential;
    }

    @Override
    public boolean hasRedoxPotentialProperty() {
        if (this.redoxPotential == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setRedoxPotential(RedoxPotential redoxPotential) {
        this.redoxPotential = redoxPotential;
    }

    @Override
    public TemperatureDependence getTemperatureDependence() {
        return this.temperatureDependence;
    }

    @Override
    public boolean hasTemperatureDependenceProperty() {
        if (this.temperatureDependence == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setTemperatureDependence(TemperatureDependence temperatureDependence) {
        this.temperatureDependence = temperatureDependence;
    }

    public void setKineticParameters(KineticParameters kineticParameters) {
        this.kineticParameters = kineticParameters;
    }

    @Override
    public KineticParameters getKineticParameters() {
        return this.kineticParameters;
    }

    @Override
    public boolean hasKineticParametersProperty() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        final BioPhysicoChemicalPropertiesCommentImpl that = (BioPhysicoChemicalPropertiesCommentImpl) o;

        if (absorption != null ? !absorption.equals(that.absorption) : that.absorption != null)
            return false;
        if (kineticParameters != null ? !kineticParameters.equals(that.kineticParameters)
                : that.kineticParameters != null)
            return false;
        if (phDependence != null ? !phDependence.equals(that.phDependence) : that.phDependence != null)
            return false;
        if (redoxPotential != null ? !redoxPotential.equals(that.redoxPotential) : that.redoxPotential != null)
            return false;
        if (temperatureDependence != null ? !temperatureDependence.equals(that.temperatureDependence)
                : that.temperatureDependence != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (absorption != null ? absorption.hashCode() : 0);
        result = 29 * result + (phDependence != null ? phDependence.hashCode() : 0);
        result = 29 * result + (redoxPotential != null ? redoxPotential.hashCode() : 0);
        result = 29 * result + (temperatureDependence != null ? temperatureDependence.hashCode() : 0);
        result = 29 * result + (kineticParameters != null ? kineticParameters.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        // if (commentsCounter>0)
        sb.append("\n");
        BioPhysicoChemicalPropertiesComment bioPCPropComment = this;
        sb.append("CC   -!- ");
        sb.append(bioPCPropComment.getCommentType().toDisplayName());
        sb.append(":");
        if (bioPCPropComment.hasAbsorptionProperty()) {
            sb.append("\nCC       Absorption:\n");
            Absorption absorption = bioPCPropComment.getAbsorption();

            sb.append("CC         Abs(max)=");
            if (absorption.isApproximation()) {
                sb.append("~");
            }
            sb.append(absorption.getMax());

            sb.append(" nm;");

            if (absorption.hasNote()) {
                sb.append("\nCC         Note=").append(absorption.getNote().toString()).append(";");
            }

        }

        if (bioPCPropComment.hasKineticParametersProperty()) {
            sb.append("\nCC       Kinetic parameters:");

            if (null != bioPCPropComment.getKineticParameters().getMichaelisConstants()) {
                List<MichaelisConstant> michaelisConstants =
                        bioPCPropComment.getKineticParameters().getMichaelisConstants();
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

            if (null != bioPCPropComment.getKineticParameters().getMaximumVelocities()
                    &&
                    !bioPCPropComment.getKineticParameters().getMaximumVelocities().isEmpty()) {
                List<MaximumVelocity> maximumVelocities =
                        bioPCPropComment.getKineticParameters().getMaximumVelocities();
                for (MaximumVelocity maximumVelocity : maximumVelocities) {
                    sb.append("CC         Vmax=" +
                            maximumVelocity.getVelocity() +
                            " " +
                            maximumVelocity.getMaxVelocityUnit() +
                            " " +
                            maximumVelocity.getEnzyme() +
                            ";");
                    sb.append("\n");

                }
            }

        }
        if (bioPCPropComment.hasPHDependenceProperty()) {
            sb.append("\nCC       pH dependence:\nCC         ");
            sb.append(bioPCPropComment.getPHDependence().toString());
            sb.append(";");

        }
        if (bioPCPropComment.hasRedoxPotentialProperty()) {
            sb.append("\nCC       Redox potential:\nCC         ");
            sb.append(bioPCPropComment.getRedoxPotential().toString());
            sb.append(";");

        }
        if (bioPCPropComment.hasTemperatureDependenceProperty()) {
            sb.append("\nCC       Temperature dependence:\n");
            sb.append("CC         " +
                    bioPCPropComment.getTemperatureDependence().toString() +
                    ";");

        }

        // commentsCounter++;

        return sb.toString();
    }
}
