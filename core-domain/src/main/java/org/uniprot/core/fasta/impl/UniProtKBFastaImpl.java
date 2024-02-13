package org.uniprot.core.fasta.impl;

import java.util.Objects;

import org.uniprot.core.Sequence;
import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

/**
 * @author lgonzales
 * @since 21/10/2020
 */
public class UniProtKBFastaImpl extends ProteinFastaImpl implements UniProtKBFasta {

    private static final long serialVersionUID = 5452223372142310039L;

    private final UniProtKBEntryType entryType;
    private final UniProtKBId uniProtkbId;
    private final String proteinName;
    private final Organism organism;
    private final String geneName;
    private final ProteinExistence proteinExistence;
    private final FlagType flagType;
    private final Integer sequenceVersion;

    private final String sequenceRange;

    UniProtKBFastaImpl() {
        this(null, null, null, null, null, null, null, null, null, 0, null);
    }

    public UniProtKBFastaImpl(
            UniProtKBEntryType entryType,
            String id,
            UniProtKBId uniProtkbId,
            String proteinName,
            Organism organism,
            String geneName,
            ProteinExistence proteinExistence,
            FlagType flagType,
            Sequence sequence,
            Integer sequenceVersion,
            String sequenceRange) {
        super(id, sequence);
        this.entryType = entryType;
        this.uniProtkbId = uniProtkbId;
        this.proteinName = proteinName;
        this.organism = organism;
        this.geneName = geneName;
        this.proteinExistence = proteinExistence;
        this.flagType = flagType;
        this.sequenceVersion = sequenceVersion;
        this.sequenceRange = sequenceRange;
    }

    public UniProtKBEntryType getEntryType() {
        return entryType;
    }

    public UniProtKBId getUniProtkbId() {
        return uniProtkbId;
    }

    public String getProteinName() {
        return proteinName;
    }

    public Organism getOrganism() {
        return organism;
    }

    public String getGeneName() {
        return geneName;
    }

    public ProteinExistence getProteinExistence() {
        return proteinExistence;
    }

    public FlagType getFlagType() {
        return flagType;
    }

    public Integer getSequenceVersion() {
        return sequenceVersion;
    }

    @Override
    public String getSequenceRange() {
        return sequenceRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UniProtKBFastaImpl that = (UniProtKBFastaImpl) o;
        return getEntryType() == that.getEntryType()
                && Objects.equals(getUniProtkbId(), that.getUniProtkbId())
                && Objects.equals(getProteinName(), that.getProteinName())
                && Objects.equals(getOrganism(), that.getOrganism())
                && Objects.equals(getGeneName(), that.getGeneName())
                && getProteinExistence() == that.getProteinExistence()
                && getFlagType() == that.getFlagType()
                && Objects.equals(getSequenceVersion(), that.getSequenceVersion())
                && Objects.equals(getSequenceRange(), that.getSequenceRange());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getEntryType(),
                getUniProtkbId(),
                getProteinName(),
                getOrganism(),
                getGeneName(),
                getProteinExistence(),
                getFlagType(),
                getSequenceVersion(),
                getSequenceRange());
    }
}
