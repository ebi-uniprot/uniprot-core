package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstantUnit;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MichaelisConstantImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class MichaelisConstantBuilder implements Builder2<MichaelisConstantBuilder, MichaelisConstant> {
    private double constant;
    private MichaelisConstantUnit unit;
    private String substrate;
    private List<Evidence> evidences = new ArrayList<>();

    public MichaelisConstantBuilder constant(double constant) {
        this.constant = constant;
        return this;
    }

    public MichaelisConstantBuilder unit(MichaelisConstantUnit unit) {
        this.unit = unit;
        return this;
    }

    public MichaelisConstantBuilder substrate(String substrate) {
        this.substrate = substrate;
        return this;
    }

    public MichaelisConstantBuilder evidences(List<Evidence> evidences) {
        this.evidences.addAll(evidences);
        return this;
    }

    public MichaelisConstantBuilder addEvidence(Evidence evidence) {
        this.evidences.add(evidence);
        return this;
    }

    @Override
    public MichaelisConstant build() {
        return new MichaelisConstantImpl(this);
    }

    @Override
    public MichaelisConstantBuilder from(MichaelisConstant instance) {
        return new MichaelisConstantBuilder()
                .evidences(instance.getEvidences())
                .constant(instance.getConstant())
                .substrate(instance.getSubstrate())
                .unit(instance.getUnit());
    }

    public double getConstant() {
        return constant;
    }

    public MichaelisConstantUnit getUnit() {
        return unit;
    }

    public String getSubstrate() {
        return substrate;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }
}
