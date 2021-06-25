package org.uniprot.core.uniparc;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum UniParcDatabase implements Database, EnumDisplay {
    EG_BACTERIA(9, "EnsemblBacteria", true, "https://www.ensemblgenomes.org/id/%id"),
    EG_FUNGI(10, "EnsemblFungi", true, "https://www.ensemblgenomes.org/id/%id"),
    EG_METAZOA(11, "EnsemblMetazoa", true, "https://www.ensemblgenomes.org/id/%id"),
    EG_PLANTS(12, "EnsemblPlants", true, "https://www.ensemblgenomes.org/id/%id"),
    EG_PROTISTS(13, "EnsemblProtists", true, "https://www.ensemblgenomes.org/id/%id"),

    EMBL(3, "EMBL", true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBLWGS(4, "EMBLWGS", true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBL_CON(5, "EMBL_CON", true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBL_TPA(6, "EMBL_TPA", false, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBL_TSA(7, "EMBL_TSA", true, "https://www.ebi.ac.uk/ena/browser/view/%id"),

    ENSEMBL_VERTEBRATE(8, "Ensembl", true, "https://www.ensembl.org/id/%id"),
    EPO(14, "EPO", true, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=epo_prt&id=%id"),
    FLYBASE(15, "FlyBase", true, "https://flybase.org/reports/%id.html"),
    H_INV(16, "H-InvDB", false),
    IPI(17, "IPI", false),

    JPO(18, "JPO", true, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=jpo_prt&id=%id"),
    KIPO(19, "KIPO", true, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=kipo_prt&id=%id"),
    PATRIC(20, "PATRIC", true, "https://www.patricbrc.org/view/Feature/%id"),
    PDB(21,
            "PDB",
            true,
            "https://www.ebi.ac.uk/pdbe/entry/pdb/%id"), // need to remove the chain, eg "4q8n_A",
    // just use "4q8n" as id
    PIR(22, "PIR", false),

    PIRARC(23, "PIRARC", false),
    PRF(24, "PRF", false, "http://www.prf.or.jp/cgi-bin/seqget.pl?id=%id"),
    REFSEQ(25, "RefSeq", true, "https://www.ncbi.nlm.nih.gov/protein/%id"),
    REMTREMBL(26, "REMTREMBL", false),
    SEED(27, "SEED", true, "https://pubseed.theseed.org/seedviewer.cgi?page=Annotation&feature=%id"),

    SGD(28, "SGD", true, "https://www.yeastgenome.org/locus/%id"),
    SWISSPROT(1, "UniProtKB/Swiss-Prot", true, "https://www.uniprot.org/uniprot/%id"),
    SWISSPROT_VARSPLIC(2,
            "UniProtKB/Swiss-Prot protein isoforms",
            true,
            "https://www.uniprot.org/uniprot/%id"), // swissprot isoform
    TAIR_ARABIDOPSIS(29,
            "TAIR",
            true,
            "https://www.arabidopsis.org/servlets/TairObject?type=aa_sequence&name=%id"),
    TREMBL(1, "UniProtKB/TrEMBL", true, "https://www.uniprot.org/uniprot/%id"),

    TREMBLNEW(30, "TREMBLNEW", false),
    TREMBL_VARSPLIC(31, "TREMBL_VARSPLIC", false),
    TROME(32, "TROME", true), // no link
    UNIMES(33, "UNIMES", false),
    USPTO(34, "USPTO", true, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=uspto_prt&id=%id"),

    VECTORBASE(35, "VectorBase", false),
    VEGA(36, "VEGA", true, "https://vega.sanger.ac.uk/id/%id"),
    WORMBASE(37, "WormBase", true, "https://wormbase.org/db/seq/protein?name=%id;class=CDS"),
    WORMBASE_PARASITE(38, "WBParaSite", true, "https://parasite.wormbase.org/id/%id");

    private final String displayName;
    private final boolean alive;
    private final String url;
    private final int index;

    UniParcDatabase(int index, String displayName, boolean alive) {
        this(index, displayName, alive, "");
    }

    UniParcDatabase(int index, String displayName, boolean alive, String url) {
        this.index = index;
        this.displayName = displayName;
        this.alive = alive;
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public @Nonnull String getName() {
        return displayName;
    }

    public boolean isAlive() {
        return alive;
    }

    public String getUrl() {
        return url;
    }

    public static @Nonnull UniParcDatabase typeOf(@Nonnull String displayName) {
        return EnumDisplay.typeOf(displayName, UniParcDatabase.class);
    }
}
