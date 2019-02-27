package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstantUnit;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MichaelisConstantImpl implements MichaelisConstant {
    private static final long serialVersionUID = 7764704734751950354L;
    private double constant;
    private MichaelisConstantUnit unit;
    private String substrate;
    private List<Evidence> evidences;

    private MichaelisConstantImpl() {
        this.evidences = Collections.emptyList();
    }

    public MichaelisConstantImpl(double constant,
                                 MichaelisConstantUnit unit,
                                 String substrate,
                                 List<Evidence> evidences) {
        this.constant = constant;
        this.unit = unit;
        this.substrate = substrate;
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(evidences);
        }
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notEmpty(this.evidences);
    }

    @Override
    public double getConstant() {
        return constant;
    }

    @Override
    public MichaelisConstantUnit getUnit() {
        return unit;
    }

    @Override
    public String getSubstrate() {
        return substrate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MichaelisConstantImpl that = (MichaelisConstantImpl) o;
        return Double.compare(that.constant, constant) == 0 &&
                unit == that.unit &&
                Objects.equals(substrate, that.substrate) &&
                Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constant, unit, substrate, evidences);
    }
}
