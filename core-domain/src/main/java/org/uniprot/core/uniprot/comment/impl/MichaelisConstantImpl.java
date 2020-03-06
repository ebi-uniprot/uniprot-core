package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.comment.MichaelisConstant;
import org.uniprot.core.uniprot.comment.MichaelisConstantUnit;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class MichaelisConstantImpl implements MichaelisConstant {
    private static final long serialVersionUID = 7764704734751950354L;
    private double constant;
    private MichaelisConstantUnit unit;
    private String substrate;
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    MichaelisConstantImpl() {
        this.evidences = Collections.emptyList();
    }

    MichaelisConstantImpl(
            double constant,
            MichaelisConstantUnit unit,
            String substrate,
            List<Evidence> evidences) {
        this.constant = constant;
        this.unit = unit;
        this.substrate = substrate;
        this.evidences = Utils.unmodifiableList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullNotEmpty(this.evidences);
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
        return Double.compare(that.constant, constant) == 0
                && unit == that.unit
                && Objects.equals(substrate, that.substrate)
                && Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constant, unit, substrate, evidences);
    }
}
