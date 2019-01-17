package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstantUnit;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.MichaelisConstantBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MichaelisConstantImpl implements MichaelisConstant {
    private double constant;
    private MichaelisConstantUnit unit;
    private String substrate;
    private List<Evidence> evidences;

    private MichaelisConstantImpl() {
        this.evidences = Collections.emptyList();
    }

    public MichaelisConstantImpl(MichaelisConstantBuilder builder) {
        this.constant = builder.getConstant();
        this.unit = builder.getUnit();
        this.substrate = builder.getSubstrate();
        if ((builder.getEvidences() == null) || builder.getEvidences().isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(builder.getEvidences());
        }
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
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
