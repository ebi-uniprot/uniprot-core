package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstantUnit;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MichaelisConstantImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class MichaelisConstantBuilder implements Builder<MichaelisConstantBuilder, MichaelisConstant> {
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
        this.evidences = nonNullList(evidences);
        return this;
    }

    public MichaelisConstantBuilder addEvidence(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
        return this;
    }

    @Override
    public MichaelisConstant build() {
        return new MichaelisConstantImpl(this);
    }

    @Override
    public MichaelisConstantBuilder from(MichaelisConstant instance) {
        evidences.clear();
        return this
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
