package org.uniprot.core.fasta.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 21/10/2020
 */
public abstract class AbstractUniProtKBFastaBuilder<
                B extends AbstractUniProtKBFastaBuilder<B, T>, T extends UniProtKBFasta>
        extends AbstractProteinFastaBuilder<B, T> {

    protected UniProtKBEntryType entryType;

    protected UniProtKBId uniProtkbId;

    protected String proteinName;

    protected Organism organism;

    protected String geneName;

    protected ProteinExistence proteinExistence;

    protected FlagType flagType;

    protected Integer sequenceVersion;

    public @Nonnull B entryType(UniProtKBEntryType entryType) {
        this.entryType = entryType;
        return getThis();
    }

    public @Nonnull B uniProtkbId(UniProtKBId uniProtkbId) {
        this.uniProtkbId = uniProtkbId;
        return getThis();
    }

    public @Nonnull B uniProtkbId(String uniProtkbId) {
        this.uniProtkbId = new UniProtKBIdBuilder(Utils.emptyOrString(uniProtkbId)).build();
        return getThis();
    }

    public @Nonnull B proteinName(String proteinName) {
        this.proteinName = proteinName;
        return getThis();
    }

    public @Nonnull B organism(Organism organism) {
        this.organism = organism;
        return getThis();
    }

    public @Nonnull B geneName(String geneName) {
        this.geneName = geneName;
        return getThis();
    }

    public @Nonnull B proteinExistence(ProteinExistence proteinExistence) {
        this.proteinExistence = proteinExistence;
        return getThis();
    }

    public @Nonnull B flagType(FlagType flagType) {
        this.flagType = flagType;
        return getThis();
    }

    public @Nonnull B sequenceVersion(Integer sequenceVersion) {
        this.sequenceVersion = sequenceVersion;
        return getThis();
    }

    protected abstract B getThis();

    protected static <B extends AbstractUniProtKBFastaBuilder<B, T>, T extends UniProtKBFasta>
            B from(@Nonnull B builder, @Nonnull T instance) {
        return AbstractProteinFastaBuilder.from(builder, instance)
                .entryType(instance.getEntryType())
                .uniProtkbId(instance.getUniProtkbId())
                .proteinName(instance.getProteinName())
                .organism(instance.getOrganism())
                .geneName(instance.getGeneName())
                .proteinExistence(instance.getProteinExistence())
                .flagType(instance.getFlagType())
                .sequenceVersion(instance.getSequenceVersion());
    }
}
