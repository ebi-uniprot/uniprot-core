package org.uniprot.core.antigen.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.Sequence;
import org.uniprot.core.antigen.AntigenEntry;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 06/05/2020
 */
public class AntigenEntryImpl implements AntigenEntry {

    private final UniProtKBAccession primaryAccession;
    private final UniProtKBId uniProtkbId;
    private final Organism organism;
    private final Sequence sequence;
    private final List<AntigenFeature> features;

    AntigenEntryImpl() {
        this(null, null, null, null, null);
    }

    AntigenEntryImpl(
            UniProtKBAccession primaryAccession,
            UniProtKBId uniProtkbId,
            Organism organism,
            Sequence sequence,
            List<AntigenFeature> features) {
        this.primaryAccession = primaryAccession;
        this.uniProtkbId = uniProtkbId;
        this.organism = organism;
        this.sequence = sequence;
        this.features = Utils.unmodifiableList(features);
    }

    @Override
    public UniProtKBAccession getPrimaryAccession() {
        return primaryAccession;
    }

    @Override
    public UniProtKBId getUniProtkbId() {
        return uniProtkbId;
    }

    @Override
    public Organism getOrganism() {
        return organism;
    }

    @Override
    public Sequence getSequence() {
        return sequence;
    }

    @Override
    public List<AntigenFeature> getFeatures() {
        return features;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AntigenEntryImpl that = (AntigenEntryImpl) o;
        return Objects.equals(getPrimaryAccession(), that.getPrimaryAccession())
                && Objects.equals(getUniProtkbId(), that.getUniProtkbId())
                && Objects.equals(getOrganism(), that.getOrganism())
                && Objects.equals(getSequence(), that.getSequence())
                && Objects.equals(getFeatures(), that.getFeatures());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getPrimaryAccession(),
                getUniProtkbId(),
                getOrganism(),
                getSequence(),
                getFeatures());
    }
}
