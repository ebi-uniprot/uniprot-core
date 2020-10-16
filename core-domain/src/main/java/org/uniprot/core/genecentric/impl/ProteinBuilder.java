package org.uniprot.core.genecentric.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.Sequence;
import org.uniprot.core.genecentric.Protein;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

import javax.annotation.Nonnull;

/**
 * @author lgonzales
 * @since 15/10/2020
 */
public class ProteinBuilder implements Builder<Protein> {

    private UniProtKBEntryType entryType;

    private UniProtKBAccession accession;

    private UniProtKBId uniProtkbId;

    private String proteinName;

    private Organism organism;

    private String geneName;

    private ProteinExistence proteinExistence;

    private FlagType flagType;

    private String proteomeId;

    private Sequence sequence;

    private int sequenceVersion;

    public @Nonnull ProteinBuilder entryType(UniProtKBEntryType entryType) {
        this.entryType = entryType;
        return this;
    }

    public @Nonnull ProteinBuilder accession(UniProtKBAccession accession) {
        this.accession = accession;
        return this;
    }

    public @Nonnull ProteinBuilder accession(String accession) {
        this.accession =
                new UniProtKBAccessionBuilder(Utils.emptyOrString(accession)).build();
        return this;
    }

    public @Nonnull ProteinBuilder uniProtkbId(UniProtKBId uniProtkbId) {
        this.uniProtkbId = uniProtkbId;
        return this;
    }

    public @Nonnull ProteinBuilder uniProtkbId(String uniProtkbId) {
        this.uniProtkbId = new UniProtKBIdBuilder(Utils.emptyOrString(uniProtkbId)).build();
        return this;
    }

    public @Nonnull ProteinBuilder proteinName(String proteinName) {
        this.proteinName = proteinName;
        return this;
    }

    public @Nonnull ProteinBuilder organism(Organism organism) {
        this.organism = organism;
        return this;
    }

    public @Nonnull ProteinBuilder geneName(String geneName){
        this.geneName = geneName;
        return this;
    }

    public @Nonnull ProteinBuilder proteinExistence(ProteinExistence proteinExistence){
        this.proteinExistence = proteinExistence;
        return this;
    }

    public @Nonnull ProteinBuilder flagType(FlagType flagType){
        this.flagType = flagType;
        return this;
    }

    public @Nonnull ProteinBuilder proteomeId(String proteomeId){
        this.proteomeId = proteomeId;
        return this;
    }

    public @Nonnull ProteinBuilder sequence(String sequence){
        this.sequence = new SequenceBuilder(Utils.emptyOrString(sequence)).build();
        return this;
    }

    public @Nonnull ProteinBuilder sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    public @Nonnull ProteinBuilder sequenceVersion(int sequenceVersion){
        this.sequenceVersion = sequenceVersion;
        return this;
    }

    public static @Nonnull ProteinBuilder from(@Nonnull Protein instance) {
        return new ProteinBuilder()
                .entryType(instance.getEntryType())
                .accession(instance.getAccession())
                .uniProtkbId(instance.getUniProtkbId())
                .proteinName(instance.getProteinName())
                .organism(instance.getOrganism())
                .geneName(instance.getGeneName())
                .proteinExistence(instance.getProteinExistence())
                .flagType(instance.getFlagType())
                .proteomeId(instance.getProteomeId())
                .sequence(instance.getSequence())
                .sequenceVersion(instance.getSequenceVersion());
    }

    @Nonnull
    @Override
    public Protein build() {
        return new ProteinImpl(entryType, accession, uniProtkbId, proteinName, organism,
                geneName, proteinExistence, flagType, proteomeId, sequence, sequenceVersion);
    }
}
