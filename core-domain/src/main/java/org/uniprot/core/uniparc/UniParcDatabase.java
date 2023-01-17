package org.uniprot.core.uniparc;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum UniParcDatabase implements Database, EnumDisplay {
    EG_BACTERIA(900, "EnsemblBacteria", true, "https://www.ensemblgenomes.org/id/%id"),
    EG_FUNGI(1000, "EnsemblFungi", true, "https://www.ensemblgenomes.org/id/%id"),
    EG_METAZOA(1100, "EnsemblMetazoa", true, "https://www.ensemblgenomes.org/id/%id"),
    EG_PLANTS(1200, "EnsemblPlants", true, "https://www.ensemblgenomes.org/id/%id"),
    EG_PROTISTS(1300, "EnsemblProtists", true, "https://www.ensemblgenomes.org/id/%id"),

    EMBL(300, "EMBL", true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBL_CON(400, "EMBL_CON", true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBL_TPA(500, "EMBL_TPA", false, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBL_TSA(600, "EMBL_TSA", true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBLWGS(700, "EMBLWGS", true, "https://www.ebi.ac.uk/ena/browser/view/%id"),

    ENSEMBL_VERTEBRATE(800, "Ensembl", true, "https://www.ensembl.org/id/%id"),
    ENSEMBL_RAPID(1350, "EnsemblRapid", true, "https://rapid.ensembl.org/id/%id"),

    EPO(1400, "EPO", true, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=epo_prt&id=%id"),
    FLYBASE(1500, "FlyBase", true, "https://flybase.org/reports/%id.html"),
    FUSION_GDB(1550, "FusionGDB", true, "https://compbio.uth.edu/FusionGDB2/gene_search_result.cgi?type=quick_search&quick_search=%id"),

    H_INV(1600, "H-InvDB", false),
    IPI(1700, "IPI", false),

    JPO(1800, "JPO", true, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=jpo_prt&id=%id"),
    KIPO(1900, "KIPO", true, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=kipo_prt&id=%id"),
    PATRIC(2000, "PATRIC", true, "https://www.patricbrc.org/view/Feature/%id"),
    PDB(
            2100,
            "PDB",
            true,
            "https://www.ebi.ac.uk/pdbe/entry/pdb/%id"), // need to remove the chain, eg "4q8n_A",
    // just use "4q8n" as id
    PIR(2200, "PIR", false),

    PIRARC(2300, "PIRARC", false),
    PRF(2400, "PRF", false, "http://www.prf.or.jp/cgi-bin/seqget.pl?id=%id"),
    REFSEQ(2500, "RefSeq", true, "https://www.ncbi.nlm.nih.gov/protein/%id"),
    REMTREMBL(2600, "REMTREMBL", false),
    SEED(
            2700,
            "SEED",
            true,
            "https://pubseed.theseed.org/seedviewer.cgi?page=Annotation&feature=%id"),

    SGD(2800, "SGD", true, "https://www.yeastgenome.org/locus/%id"),
    SWISSPROT(100, "UniProtKB/Swiss-Prot", true, "https://www.uniprot.org/uniprot/%id"),
    SWISSPROT_VARSPLIC(
            200,
            "UniProtKB/Swiss-Prot protein isoforms",
            true,
            "https://www.uniprot.org/uniprot/%id"), // swissprot isoform
    TAIR_ARABIDOPSIS(
            2900,
            "TAIR",
            true,
            "https://www.arabidopsis.org/servlets/TairObject?type=aa_sequence&name=%id"),
    TREMBL(100, "UniProtKB/TrEMBL", true, "https://www.uniprot.org/uniprot/%id"),

    TREMBLNEW(3000, "TREMBLNEW", false),
    TREMBL_VARSPLIC(3100, "TREMBL_VARSPLIC", false),
    TROME(3200, "TROME", true), // no link
    UNIMES(3300, "UNIMES", false),
    USPTO(3400, "USPTO", true, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=uspto_prt&id=%id"),

    VECTORBASE(3500, "VectorBase", false),
    VEGA(3600, "VEGA", true, "https://vega.sanger.ac.uk/id/%id"),
    WORMBASE_PARASITE(3700, "WBParaSite", true, "https://parasite.wormbase.org/id/%id"),
    WORMBASE(3800, "WormBase", true, "https://wormbase.org/db/seq/protein?name=%id;class=CDS");

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
