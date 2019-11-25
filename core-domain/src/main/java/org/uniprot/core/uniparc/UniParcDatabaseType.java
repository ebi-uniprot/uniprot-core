package org.uniprot.core.uniparc;

import javax.annotation.Nonnull;

import org.uniprot.core.DatabaseType;
import org.uniprot.core.util.EnumDisplay;

/**
 * @author jluo
 * @date: 21 May 2019
 */
public enum UniParcDatabaseType implements DatabaseType, EnumDisplay<UniParcDatabaseType> {
    EG_BACTERIA("EnsemblBacteria", true),
    EG_FUNGI("EnsemblFungi", true),
    EG_METAZOA("EnsemblMetazoa", true),
    EG_PLANTS("EnsemblPlants", true),
    EG_PROTISTS("EnsemblProtists", true),

    EMBL("EMBL", true),
    EMBLWGS("EMBLWGS", true),
    EMBL_CON("EMBL_CON", true),
    EMBL_TPA("EMBL_TPA", false),
    EMBL_TSA("EMBL_TSA", true),

    ENSEMBL_VERTEBRATE("Ensembl", true),
    EPO("EPO", true),
    FLYBASE("FlyBase", true),
    H_INV("H-InvDB", true),
    IPI("IPI", false),

    JPO("JPO", true),
    KIPO("KIPO", true),
    PATRIC("PATRIC", true),
    PDB("PDB", true),
    PIR("PIR", false),

    PIRARC("PIRARC", false),
    PRF("PRF", false),
    REFSEQ("RefSeq", true),
    REMTREMBL("REMTREMBL", false),
    SEED("SEED", true),

    SGD("SGD", true),
    SWISSPROT("UniProtKB/Swiss-Prot", true),
    SWISSPROT_VARSPLIC("UniProtKB/Swiss-Prot protein isoforms", true),
    TAIR_ARABIDOPSIS("TAIR", true),
    TREMBL("UniProtKB/TrEMBL", true),

    TREMBLNEW("TREMBLNEW", false),
    TREMBL_VARSPLIC("TREMBL_VARSPLIC", false),
    TROME("TROME", true),
    UNIMES("UNIMES", false),
    USPTO("USPTO", true),

    VECTORBASE("VectorBase", true),
    VEGA("VEGA", true),
    WORMBASE("WormBase", true),
    WORMBASE_PARASITE("WBParaSite", true);
    private final String displayName;
    private final boolean alive;

    UniParcDatabaseType(String displayName, boolean alive) {
        this.displayName = displayName;
        this.alive = alive;
    }

    @Override
    public @Nonnull String toDisplayName() {
        return displayName;
    }

    @Override
    public @Nonnull String getName() {
        return displayName;
    }

    public @Nonnull boolean isAlive() {
        return alive;
    }

    public static @Nonnull UniParcDatabaseType typeOf(String value) {
        for (UniParcDatabaseType type : UniParcDatabaseType.values()) {
            if (type.toDisplayName().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("The UniParcDatabaseType: " + value + " doesn't exist");
    }
}
