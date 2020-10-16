package org.uniprot.core.genecentric.impl;

import org.uniprot.core.Sequence;
import org.uniprot.core.genecentric.Protein;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

import java.util.Objects;

/**
 * @author lgonzales
 * @since 15/10/2020
 */
public class ProteinImpl implements Protein {

    private static final long serialVersionUID = -2231166801909993211L;

    private final UniProtKBEntryType entryType;

    private final UniProtKBAccession accession;

    private final UniProtKBId uniProtkbId;

    private final String proteinName;

    private final Organism organism;

    private final String geneName;

    private final ProteinExistence proteinExistence;

    private final FlagType flagType;

    private final String proteomeId;

    private final Sequence sequence;

    private final int sequenceVersion;

    ProteinImpl(){
        this(null, null, null, null, null, null, null, null, null, null, 0);
    }

    public ProteinImpl(UniProtKBEntryType entryType,
                       UniProtKBAccession accession,
                       UniProtKBId uniProtkbId,
                       String proteinName,
                       Organism organism,
                       String geneName,
                       ProteinExistence proteinExistence,
                       FlagType flagType,
                       String proteomeId,
                       Sequence sequence,
                       int sequenceVersion) {
        this.entryType = entryType;
        this.accession = accession;
        this.uniProtkbId = uniProtkbId;
        this.proteinName = proteinName;
        this.organism = organism;
        this.geneName = geneName;
        this.proteinExistence = proteinExistence;
        this.flagType = flagType;
        this.proteomeId = proteomeId;
        this.sequence = sequence;
        this.sequenceVersion = sequenceVersion;
    }

    public UniProtKBEntryType getEntryType() {
        return entryType;
    }

    public UniProtKBAccession getAccession() {
        return accession;
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

    public String getProteomeId() {
        return proteomeId;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public int getSequenceVersion() {
        return sequenceVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProteinImpl protein = (ProteinImpl) o;
        return getSequenceVersion() == protein.getSequenceVersion() &&
                getEntryType() == protein.getEntryType() &&
                Objects.equals(getAccession(), protein.getAccession()) &&
                Objects.equals(getUniProtkbId(), protein.getUniProtkbId()) &&
                Objects.equals(getProteinName(), protein.getProteinName()) &&
                Objects.equals(getOrganism(), protein.getOrganism()) &&
                Objects.equals(getGeneName(), protein.getGeneName()) &&
                getProteinExistence() == protein.getProteinExistence() &&
                getFlagType() == protein.getFlagType() &&
                Objects.equals(getProteomeId(), protein.getProteomeId()) &&
                Objects.equals(getSequence(), protein.getSequence());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntryType(), getAccession(), getUniProtkbId(), getProteinName(), getOrganism(), getGeneName(), getProteinExistence(), getFlagType(), getProteomeId(), getSequence(), getSequenceVersion());
    }
}
