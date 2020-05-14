package org.uniprot.core.antigen.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Sequence;
import org.uniprot.core.antigen.AntigenEntry;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 06/05/2020
 */
public class AntigenEntryBuilder implements Builder<AntigenEntry> {

    private UniProtKBAccession primaryAccession;
    private UniProtKBId uniProtkbId;
    private Organism organism;
    private Sequence sequence;
    private List<AntigenFeature> features = new ArrayList<>();

    public @Nonnull AntigenEntryBuilder primaryAccession(UniProtKBAccession primaryAccession) {
        this.primaryAccession = primaryAccession;
        return this;
    }

    public @Nonnull AntigenEntryBuilder primaryAccession(String primaryAccession) {
        this.primaryAccession =
                new UniProtKBAccessionBuilder(Utils.emptyOrString(primaryAccession)).build();
        return this;
    }

    public @Nonnull AntigenEntryBuilder uniProtkbId(UniProtKBId uniProtkbId) {
        this.uniProtkbId = uniProtkbId;
        return this;
    }

    public @Nonnull AntigenEntryBuilder uniProtkbId(String uniProtkbId) {
        this.uniProtkbId = new UniProtKBIdBuilder(Utils.emptyOrString(uniProtkbId)).build();
        return this;
    }

    public @Nonnull AntigenEntryBuilder organism(Organism organism) {
        this.organism = organism;
        return this;
    }

    public @Nonnull AntigenEntryBuilder sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    public @Nonnull AntigenEntryBuilder featuresAdd(AntigenFeature feature) {
        Utils.addOrIgnoreNull(feature, this.features);
        return this;
    }

    public @Nonnull AntigenEntryBuilder featuresSet(List<AntigenFeature> features) {
        this.features = Utils.modifiableList(features);
        return this;
    }

    public static @Nonnull AntigenEntryBuilder from(@Nonnull AntigenEntry instance) {
        return new AntigenEntryBuilder()
                .primaryAccession(instance.getPrimaryAccession())
                .uniProtkbId(instance.getUniProtkbId())
                .organism(instance.getOrganism())
                .sequence(instance.getSequence())
                .featuresSet(instance.getFeatures());
    }

    @Nonnull
    @Override
    public AntigenEntry build() {
        return new AntigenEntryImpl(primaryAccession, uniProtkbId, organism, sequence, features);
    }
}
