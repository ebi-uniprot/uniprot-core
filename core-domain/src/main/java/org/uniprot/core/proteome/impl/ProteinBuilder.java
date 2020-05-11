package org.uniprot.core.proteome.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.GeneNameType;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

import javax.annotation.Nonnull;

public class ProteinBuilder implements Builder<Protein> {
    private UniProtKBAccession accession;
    private UniProtKBEntryType entryType;
    private long sequenceLength;
    private String geneName;
    private GeneNameType geneNameType;

    @Override
    public @Nonnull Protein build() {
        return new ProteinImpl(accession, entryType, sequenceLength, geneName, geneNameType);
    }

    public @Nonnull ProteinBuilder accession(String accession) {
        return accession(new UniProtKBAccessionBuilder(accession).build());
    }

    public @Nonnull ProteinBuilder accession(UniProtKBAccession accession) {
        this.accession = accession;
        return this;
    }

    public @Nonnull ProteinBuilder entryType(UniProtKBEntryType entryType) {
        this.entryType = entryType;
        return this;
    }

    public @Nonnull ProteinBuilder sequenceLength(long sequenceLength) {
        this.sequenceLength = sequenceLength;
        return this;
    }

    public @Nonnull ProteinBuilder geneName(String geneName) {
        this.geneName = geneName;
        return this;
    }

    public @Nonnull ProteinBuilder geneNameType(GeneNameType geneNameType) {
        this.geneNameType = geneNameType;
        return this;
    }

    public static @Nonnull ProteinBuilder from(@Nonnull Protein instance) {
        return new ProteinBuilder()
                .accession(instance.getAccession())
                .entryType(instance.getEntryType())
                .sequenceLength(instance.getSequenceLength())
                .geneName(instance.getGeneName())
                .geneNameType(instance.getGeneNameType());
    }
}
