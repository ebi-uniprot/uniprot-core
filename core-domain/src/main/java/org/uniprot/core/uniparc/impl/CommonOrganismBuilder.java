package org.uniprot.core.uniparc.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.uniparc.CommonOrganism;

import javax.annotation.Nonnull;

public class CommonOrganismBuilder implements Builder<CommonOrganism> {

    private String topLevel;
    private String commonTaxon;

    @Nonnull
    @Override
    public CommonOrganism build() {
        return new CommonOrganismImpl(topLevel, commonTaxon);
    }

    public CommonOrganismBuilder topLevel(String topLevel) {
        this.topLevel = topLevel;
        return this;
    }


    public CommonOrganismBuilder commonTaxon(String commonTaxon) {
        this.commonTaxon = commonTaxon;
        return this;
    }

    public static @Nonnull CommonOrganismBuilder from(@Nonnull CommonOrganism instance) {
        CommonOrganismBuilder builder = new CommonOrganismBuilder()
                .topLevel(instance.getTopLevel())
                .commonTaxon(instance.getCommonTaxon());
        return builder;
    }
}
