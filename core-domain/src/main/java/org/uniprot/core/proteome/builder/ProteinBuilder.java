package org.uniprot.core.proteome.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.GeneNameType;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.proteome.impl.ProteinImpl;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;

public class ProteinBuilder implements Builder<ProteinBuilder, Protein> {
    private UniProtAccession accession;
    private UniProtEntryType entryType;
    private long sequenceLength;
    private String geneName;
    private GeneNameType geneNameType;

    public static @Nonnull ProteinBuilder newInstance() {
        return new ProteinBuilder();
    }

    @Override
    public @Nonnull Protein build() {
        return new ProteinImpl(accession, entryType, sequenceLength, geneName, geneNameType);
    }

    public @Nonnull ProteinBuilder accession(String accession) {
        return accession(new UniProtAccessionBuilder(accession).build());
    }

    public @Nonnull ProteinBuilder accession(UniProtAccession accession) {
        this.accession = accession;
        return this;
    }

    public @Nonnull ProteinBuilder entryType(UniProtEntryType entryType) {
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
