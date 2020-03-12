package org.uniprot.core.proteome.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.GeneNameType;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.uniprotkb.UniProtkbAccession;
import org.uniprot.core.uniprotkb.UniProtkbEntryType;
import org.uniprot.core.uniprotkb.impl.UniProtkbAccessionBuilder;

public class ProteinBuilder implements Builder<Protein> {
    private UniProtkbAccession accession;
    private UniProtkbEntryType entryType;
    private long sequenceLength;
    private String geneName;
    private GeneNameType geneNameType;

    @Override
    public @Nonnull Protein build() {
        return new ProteinImpl(accession, entryType, sequenceLength, geneName, geneNameType);
    }

    public @Nonnull ProteinBuilder accession(String accession) {
        return accession(new UniProtkbAccessionBuilder(accession).build());
    }

    public @Nonnull ProteinBuilder accession(UniProtkbAccession accession) {
        this.accession = accession;
        return this;
    }

    public @Nonnull ProteinBuilder entryType(UniProtkbEntryType entryType) {
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
