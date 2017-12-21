package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AbsorptionNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.BioPhysicoChemicalPropertiesComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.KPNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MichaelisConstantUnit;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PHDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comments.RedoxPotential;
import uk.ac.ebi.uniprot.domain.uniprot.comments.TemperatureDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.AbsorptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.BPCPCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.KineticParametersImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.MaximumVelocityImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.MichaelisConstantImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.List;

public final class BPCPCommentBuilder {
    private Absorption absorption;
    private KineticParameters kineticParameters;
    private PHDependence phDependence;
    private RedoxPotential redoxPotential;
    private TemperatureDependence temperatureDependence;

    BioPhysicoChemicalPropertiesComment build() {
        return new BPCPCommentImpl(absorption,
                kineticParameters,
                phDependence,
                redoxPotential,
                temperatureDependence);
    }

    public BPCPCommentBuilder setAbsorption(Absorption absorption) {
        this.absorption = absorption;
        return this;
    }

    public BPCPCommentBuilder setKineticParameters(KineticParameters kineticParameters) {
        this.kineticParameters = kineticParameters;
        return this;
    }

    public BPCPCommentBuilder setPHDependence(PHDependence phDependences) {
        this.phDependence = phDependences;
        return this;
    }

    public BPCPCommentBuilder setRedoxPotential(RedoxPotential redoxPotential) {
        this.redoxPotential = redoxPotential;
        return this;
    }

    public BPCPCommentBuilder setTemperatureDependence(TemperatureDependence temperatureDependence) {
        this.temperatureDependence = temperatureDependence;
        return this;
    }

    public static PHDependence createPHDependence(List<EvidencedValue> texts) {
        return BPCPCommentImpl.createPHDependence(texts);
    }

    public static RedoxPotential createRedoxPotential(List<EvidencedValue> texts) {
        return BPCPCommentImpl.createRedoxPotential(texts);
    }

    public static TemperatureDependence createTemperatureDependence(List<EvidencedValue> texts) {
        return BPCPCommentImpl.createTemperatureDependence(texts);
    }

    public static AbsorptionNote createAbsorptionNote(List<EvidencedValue> texts) {
        return AbsorptionImpl.createAbsorptionNote(texts);
    }

    public static KPNote createKPNote(List<EvidencedValue> texts) {
        return KineticParametersImpl.createKPNote(texts);
    }

    public static final class AbsorptionBuilder {
        private int max;
        private boolean approximate;
        private AbsorptionNote note;
        private List<Evidence> evidences;

        public Absorption build() {
            return new AbsorptionImpl(max, approximate, note, evidences);
        }

        public AbsorptionBuilder setMax(int max) {
            this.max = max;
            return this;
        }

        public AbsorptionBuilder setApproximate(boolean approximate) {
            this.approximate = approximate;
            return this;
        }

        public AbsorptionBuilder setNote(AbsorptionNote note) {
            this.note = note;
            return this;
        }

        public AbsorptionBuilder setMax(List<Evidence> evidences) {
            this.evidences = evidences;
            return this;
        }
    }

    public static final class MaximumVelocityBuilder {
        private float velocity;
        private String unit;
        private String enzyme;
        private List<Evidence> evidences;

        public MaximumVelocity build() {
            return new MaximumVelocityImpl(velocity, unit, enzyme, evidences);
        }

        public MaximumVelocityBuilder setVelocity(float velocity) {
            this.velocity = velocity;
            return this;
        }

        public MaximumVelocityBuilder setUnit(String unit) {
            this.unit = unit;
            return this;
        }

        public MaximumVelocityBuilder setEnzyme(String enzyme) {
            this.enzyme = enzyme;
            return this;
        }

        public MaximumVelocityBuilder setEvidence(List<Evidence> evidences) {
            this.evidences = evidences;
            return this;
        }
    }

    public static final class MichaelisConstantBuilder {
        private float constant;
        private MichaelisConstantUnit unit;
        private String substrate;
        private List<Evidence> evidences;

        public MichaelisConstant build() {
            return new MichaelisConstantImpl(constant, unit, substrate, evidences);
        }

        public MichaelisConstantBuilder setVelocity(float constant) {
            this.constant = constant;
            return this;
        }

        public MichaelisConstantBuilder setUnit(MichaelisConstantUnit unit) {
            this.unit = unit;
            return this;
        }

        public MichaelisConstantBuilder setSubstrate(String substrate) {
            this.substrate = substrate;
            return this;
        }

        public MichaelisConstantBuilder setEvidence(List<Evidence> evidences) {
            this.evidences = evidences;
            return this;
        }
    }

    public static final class KineticParametersBuilder {
        private List<MaximumVelocity> velocities;
        private List<MichaelisConstant> mConstants;
        private KPNote note;

        public KineticParameters build() {
            return new KineticParametersImpl(velocities, mConstants,
                    note);
        }

        public KineticParametersBuilder setMaximumVelocities(List<MaximumVelocity> velocities) {
            this.velocities = velocities;
            return this;
        }

        public KineticParametersBuilder setMichaelisConstants(List<MichaelisConstant> mConstants) {
            this.mConstants = mConstants;
            return this;
        }

        public KineticParametersBuilder setNote(KPNote note) {
            this.note = note;
            return this;
        }
    }
}
