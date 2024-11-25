package org.uniprot.core.uniparc.impl;

import org.uniprot.core.uniparc.CommonOrganism;

import java.util.Objects;

public class CommonOrganismImpl implements CommonOrganism {

    private static final long serialVersionUID = 4897886166938610296L;

    private String topLevel;
    private String commonTaxon;

    private Long commonTaxonId;

    CommonOrganismImpl(){
    }

    CommonOrganismImpl(String topLevel, String commonTaxon, Long commonTaxonId) {
        this.topLevel = topLevel;
        this.commonTaxon = commonTaxon;
        this.commonTaxonId = commonTaxonId;
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
    public Long getCommonTaxonId() {
        return this.commonTaxonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonOrganismImpl that = (CommonOrganismImpl) o;
        return Objects.equals(topLevel, that.topLevel) && Objects.equals(commonTaxon, that.commonTaxon)
                && Objects.equals(this.commonTaxonId, that.commonTaxonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLevel, commonTaxon, this.commonTaxonId);
    }
}
