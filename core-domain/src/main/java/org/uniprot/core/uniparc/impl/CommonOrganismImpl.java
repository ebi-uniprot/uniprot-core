package org.uniprot.core.uniparc.impl;

import org.uniprot.core.uniparc.CommonOrganism;

import java.util.Objects;

public class CommonOrganismImpl implements CommonOrganism {

    private static final long serialVersionUID = 4897886166938610296L;

    private String topLevel;
    private String commonTaxon;

    CommonOrganismImpl(){
    }

    CommonOrganismImpl(String topLevel, String commonTaxon) {
        this.topLevel = topLevel;
        this.commonTaxon = commonTaxon;
    }

    @Override
    public String getTopLevel() {
        return topLevel;
    }

    @Override
    public String getCommonTaxon() {
        return commonTaxon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonOrganismImpl that = (CommonOrganismImpl) o;
        return Objects.equals(topLevel, that.topLevel) && Objects.equals(commonTaxon, that.commonTaxon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLevel, commonTaxon);
    }
}
