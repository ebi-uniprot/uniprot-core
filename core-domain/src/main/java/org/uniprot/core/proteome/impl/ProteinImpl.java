package org.uniprot.core.proteome.impl;

import java.util.Objects;

import org.uniprot.core.proteome.GeneNameType;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;

public class ProteinImpl implements Protein {

    private static final long serialVersionUID = 1L;
    private UniProtKBAccession accession;
    private UniProtKBEntryType entryType;
    private long sequenceLength;
    private String geneName;
    private GeneNameType geneNameType;

    // no arg constructor for JSON deserialization
    ProteinImpl() {}

    ProteinImpl(
            UniProtKBAccession accession,
            UniProtKBEntryType entryType,
            long sequenceLength,
            String geneName,
            GeneNameType geneNameType) {
        this.accession = accession;
        this.entryType = entryType;
        this.sequenceLength = sequenceLength;
        this.geneName = geneName;
        this.geneNameType = geneNameType;
    }

    @Override
    public UniProtKBAccession getAccession() {
        return accession;
    }

    @Override
    public UniProtKBEntryType getEntryType() {
        return entryType;
    }

    @Override
    public long getSequenceLength() {
        return sequenceLength;
    }

    @Override
    public String getGeneName() {
        return geneName;
    }

    @Override
    public GeneNameType getGeneNameType() {
        return geneNameType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accession, entryType, geneName, geneNameType, sequenceLength);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ProteinImpl other = (ProteinImpl) obj;
        return Objects.equals(accession, other.accession)
                && Objects.equals(entryType, other.entryType)
                && Objects.equals(geneName, other.geneName)
                && Objects.equals(geneNameType, other.geneNameType)
                && Objects.equals(sequenceLength, other.sequenceLength);
    }
}
