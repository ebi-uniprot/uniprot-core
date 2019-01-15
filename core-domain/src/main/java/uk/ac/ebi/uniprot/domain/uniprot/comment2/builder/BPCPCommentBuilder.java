package uk.ac.ebi.uniprot.domain.uniprot.comment2.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.impl.BPCPCommentImpl;

public final class BPCPCommentBuilder implements CommentBuilder<BPCPCommentBuilder, BPCPComment> {
    private Absorption absorption = null;
    private KineticParameters kineticParameters = null;
    private PhDependence phDependence = null;
    private RedoxPotential redoxPotential = null;
    private TemperatureDependence temperatureDependence = null;

//    public static BPCPCommentBuilder newInstance() {
//        return new BPCPCommentBuilder();
//    }

//    public static PhDependence createPHDependence(List<EvidencedValue> texts) {
//        return new BPCPCommentImpl.PhDependenceImpl(texts);
//    }
//
//    public static RedoxPotential createRedoxPotential(List<EvidencedValue> texts) {
//        return new BPCPCommentImpl.RedoxPotentialImpl(texts);
//    }
//
//    public static TemperatureDependence createTemperatureDependence(List<EvidencedValue> texts) {
//        return new BPCPCommentImpl.TemperatureDependenceImpl(texts);
//    }
//
//    public static Absorption createAbsorption(int max, Note note, List<Evidence> evidences) {
//        return createAbsorption(max, false, note, evidences);
//    }
//
//    public static Absorption createAbsorption(int max, boolean approximate, Note note,
//                                              List<Evidence> evidences) {
//        return new AbsorptionImpl(max, approximate, note, evidences);
//    }
//
//    public static MaximumVelocity createMaximumVelocity(double velocity, String unit, String enzyme,
//                                                        List<Evidence> evidences) {
//        return new MaximumVelocityImpl(velocity, unit, enzyme, evidences);
//    }
//
//    public static MichaelisConstant createMichaelisConstant(double constant, MichaelisConstantUnit unit,
//                                                            String substrate,
//                                                            List<Evidence> evidences) {
//        return new MichaelisConstantImpl(constant, unit, substrate, evidences);
//    }
//
//    public static KineticParameters createKineticParameters(List<MaximumVelocity> velocities,
//                                                            List<MichaelisConstant> mConstants,
//                                                            Note note) {
//        return new KineticParametersImpl(velocities, mConstants, note);
//    }

    public BPCPComment build() {
        return new BPCPCommentImpl(this);
    }

    @Override
    public BPCPCommentBuilder from(BPCPComment instance) {
        return null;
    }

    public BPCPCommentBuilder absorption(Absorption absorption) {
        this.absorption = absorption;
        return this;
    }

    public BPCPCommentBuilder kineticParameters(KineticParameters kineticParameters) {
        this.kineticParameters = kineticParameters;
        return this;
    }

    public BPCPCommentBuilder pHDependence(PhDependence phDependences) {
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

    public Absorption getAbsorption() {
        return absorption;
    }

    public KineticParameters getKineticParameters() {
        return kineticParameters;
    }

    public PhDependence getPhDependence() {
        return phDependence;
    }

    public RedoxPotential getRedoxPotential() {
        return redoxPotential;
    }

    public TemperatureDependence getTemperatureDependence() {
        return temperatureDependence;
    }
}
