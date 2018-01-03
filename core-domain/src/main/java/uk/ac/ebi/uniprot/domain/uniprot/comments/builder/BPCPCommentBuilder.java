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

public final class BPCPCommentBuilder implements CommentBuilder<BioPhysicoChemicalPropertiesComment> {
    private Absorption absorption;
    private KineticParameters kineticParameters;
    private PHDependence phDependence;
    private RedoxPotential redoxPotential;
    private TemperatureDependence temperatureDependence;

    public static BPCPCommentBuilder newInstance() {
        return new BPCPCommentBuilder();
    }

    public BioPhysicoChemicalPropertiesComment build() {
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

    public static Absorption createAbsorption(int max, AbsorptionNote note, List<Evidence> evidences) {
        return createAbsorption(max, false, note, evidences);
    }

    public static Absorption createAbsorption(int max, boolean approximate, AbsorptionNote note,
            List<Evidence> evidences) {
        return new AbsorptionImpl(max, approximate, note, evidences);
    }

    public static MaximumVelocity createMaximumVelocity(float velocity, String unit, String enzyme,
            List<Evidence> evidences) {
        return new MaximumVelocityImpl(velocity, unit, enzyme, evidences);
    }

    public static MichaelisConstant createMichaelisConstant(float constant, MichaelisConstantUnit unit,
            String substrate,
            List<Evidence> evidences) {
        return new MichaelisConstantImpl(constant, unit, substrate, evidences);
    }

    public static KineticParameters createKineticParameters(List<MaximumVelocity> velocities,
            List<MichaelisConstant> mConstants,
            KPNote note) {
        return new KineticParametersImpl(velocities, mConstants, note);
    }

}
