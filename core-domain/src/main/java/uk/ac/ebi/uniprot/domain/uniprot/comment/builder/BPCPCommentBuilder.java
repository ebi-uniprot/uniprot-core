package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comment.BPCPComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstantUnit;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PHDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RedoxPotential;
import uk.ac.ebi.uniprot.domain.uniprot.comment.TemperatureDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.AbsorptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.BPCPCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.KineticParametersImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MaximumVelocityImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MichaelisConstantImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

public final class BPCPCommentBuilder implements CommentBuilder<BPCPComment> {
    private Absorption absorption =null;
    private KineticParameters kineticParameters =null;
    private PHDependence phDependence =null;
    private RedoxPotential redoxPotential =null;
    private TemperatureDependence temperatureDependence =null;

    public static BPCPCommentBuilder newInstance() {
        return new BPCPCommentBuilder();
    }

    public BPCPComment build() {
        return new BPCPCommentImpl(absorption,
                kineticParameters,
                phDependence,
                redoxPotential,
                temperatureDependence);
    }

    public BPCPCommentBuilder absorption(Absorption absorption) {
        this.absorption = absorption;
        return this;
    }

    public BPCPCommentBuilder kineticParameters(KineticParameters kineticParameters) {
        this.kineticParameters = kineticParameters;
        return this;
    }

    public BPCPCommentBuilder pHDependence(PHDependence phDependences) {
        this.phDependence = phDependences;
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

    public static PHDependence createPHDependence(List<EvidencedValue> texts) {
        return BPCPCommentImpl.createPHDependence(texts);
    }

    public static RedoxPotential createRedoxPotential(List<EvidencedValue> texts) {
        return BPCPCommentImpl.createRedoxPotential(texts);
    }

    public static TemperatureDependence createTemperatureDependence(List<EvidencedValue> texts) {
        return BPCPCommentImpl.createTemperatureDependence(texts);
    }


    public static Absorption createAbsorption(int max, Note note, List<Evidence> evidences) {
        return createAbsorption(max, false, note, evidences);
    }

    public static Absorption createAbsorption(int max, boolean approximate, Note note,
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
            Note note) {
        return new KineticParametersImpl(velocities, mConstants, note);
    }

}
