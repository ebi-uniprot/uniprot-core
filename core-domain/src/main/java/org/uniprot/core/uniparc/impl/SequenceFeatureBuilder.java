package org.uniprot.core.uniparc.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniparc.InterProGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SequenceFeatureLocation;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.util.Utils;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class SequenceFeatureBuilder implements Builder<SequenceFeature> {
    private InterProGroup interproGroup;
    private SignatureDbType dbType;
    private String dbId;
    private List<SequenceFeatureLocation> locations = new ArrayList<>();

    @Override
    public @Nonnull SequenceFeature build() {
        return new SequenceFeatureImpl(interproGroup, dbType, dbId, locations);
    }

    public @Nonnull SequenceFeatureBuilder interproGroup(InterProGroup interproGroup) {
        this.interproGroup = interproGroup;
        return this;
    }

    public @Nonnull SequenceFeatureBuilder signatureDbType(SignatureDbType dbType) {
        this.dbType = dbType;
        return this;
    }

    public @Nonnull SequenceFeatureBuilder signatureDbId(String dbId) {
        this.dbId = dbId;
        return this;
    }

    public @Nonnull SequenceFeatureBuilder locationsSet(List<SequenceFeatureLocation> locations) {
        this.locations = Utils.modifiableList(locations);
        return this;
    }

    public @Nonnull SequenceFeatureBuilder locationsAdd(SequenceFeatureLocation location) {
        Utils.addOrIgnoreNull(location, locations);
        return this;
    }

    public static @Nonnull SequenceFeatureBuilder from(@Nonnull SequenceFeature instance) {
        return new SequenceFeatureBuilder()
                .interproGroup(instance.getInterProDomain())
                .signatureDbType(instance.getSignatureDbType())
                .signatureDbId(instance.getSignatureDbId())
                .locationsSet(instance.getLocations());
    }
}
