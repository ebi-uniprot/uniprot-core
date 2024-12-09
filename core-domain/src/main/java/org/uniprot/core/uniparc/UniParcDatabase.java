package org.uniprot.core.uniparc;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum UniParcDatabase implements Database, EnumDisplay {
    EG_BACTERIA(900, "EnsemblBacteria", true, true, "https://www.ensemblgenomes.org/id/%id"),
    EG_FUNGI(1000, "EnsemblFungi", true, true, "https://www.ensemblgenomes.org/id/%id"),
    EG_METAZOA(1100, "EnsemblMetazoa", true, true, "https://www.ensemblgenomes.org/id/%id"),
    EG_PLANTS(1200, "EnsemblPlants", true, true, "https://www.ensemblgenomes.org/id/%id"),
    EG_PROTISTS(1300, "EnsemblProtists", true, true, "https://www.ensemblgenomes.org/id/%id"),

    EMBL(300, "EMBL", true, true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBL_CON(400, "EMBL_CON", true, true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBL_TPA(500, "EMBL_TPA", false, true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBL_TSA(600, "EMBL_TSA", true, true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBLWGS(700, "EMBLWGS", true, true, "https://www.ebi.ac.uk/ena/browser/view/%id"),

    ENSEMBL_VERTEBRATE(800, "Ensembl", true, true, "https://www.ensembl.org/id/%id"),
    ENSEMBL_RAPID(1350, "EnsemblRapid", true, true, "https://rapid.ensembl.org/id/%id"),

    EPO(1400, "EPO", true, false, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=epo_prt&id=%id"),
    FLYBASE(1500, "FlyBase", true,false, "https://flybase.org/reports/%id.html"),
    FUSION_GDB(
            1550,
            "FusionGDB",
            true,
            false,
            "https://compbio.uth.edu/FusionGDB2/gene_search_result.cgi?type=quick_search&quick_search=%id"),

    H_INV(1600, "H-InvDB",false, false),
    IPI(1700, "IPI",false, false),

    JPO(1800, "JPO", true,false, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=jpo_prt&id=%id"),
    KIPO(1900, "KIPO", true,false, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=kipo_prt&id=%id"),
    PATRIC(2000, "PATRIC", true,false, "https://www.patricbrc.org/view/Feature/%id"),
    PDB(
            2100,
            "PDB",
            true,
            false,
            "https://www.ebi.ac.uk/pdbe/entry/pdb/%id"), // need to remove the chain, eg "4q8n_A",
    // just use "4q8n" as id
    PIR(2200, "PIR",false, false),

    PIRARC(2300, "PIRARC",false, false),
    PRF(2400, "PRF",false, false, "http://www.prf.or.jp/cgi-bin/seqget.pl?id=%id"),
    REFSEQ(2500, "RefSeq", true, true,"https://www.ncbi.nlm.nih.gov/protein/%id"),
    REMTREMBL(2600, "REMTREMBL",false, false),
    SEED(
            2700,
            "SEED",
            true,
            false,
            "https://pubseed.theseed.org/seedviewer.cgi?page=Annotation&feature=%id"),

    SGD(2800, "SGD", true, false,"https://www.yeastgenome.org/locus/%id"),
    SWISSPROT(100, "UniProtKB/Swiss-Prot", true, false,"https://www.uniprot.org/uniprot/%id"),
    SWISSPROT_VARSPLIC(
            200,
            "UniProtKB/Swiss-Prot protein isoforms",
            true,
            false,
            "https://www.uniprot.org/uniprot/%id"), // swissprot isoform
    TAIR_ARABIDOPSIS(
            2900,
            "TAIR",
            true,
            false,
            "https://www.arabidopsis.org/servlets/TairObject?type=aa_sequence&name=%id"),
    TREMBL(100, "UniProtKB/TrEMBL", true, false, "https://www.uniprot.org/uniprot/%id"),

    TREMBLNEW(3000, "TREMBLNEW", false, false),
    TREMBL_VARSPLIC(3100, "TREMBL_VARSPLIC", false, false),
    TROME(3200, "TROME", true, false), // no link
    UNIMES(3300, "UNIMES", false, false),
    USPTO(3400, "USPTO", true,  false, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=uspto_prt&id=%id"),

    VECTORBASE(3500, "VectorBase", false, false),
    VEGA(3600, "VEGA", true, false,  "https://vega.sanger.ac.uk/id/%id"),
    WORMBASE_PARASITE(3700, "WBParaSite", true, true, "https://parasite.wormbase.org/id/%id"),
    WORMBASE(3800, "WormBase", true, true,"https://wormbase.org/db/seq/protein?name=%id;class=CDS");

    private final String displayName;
    private final boolean alive;
    private final String url;
    private final int index;
    private final boolean source;

    UniParcDatabase(int index, String displayName, boolean alive, boolean source) {
        this(index, displayName, alive, source, "");
    }

    UniParcDatabase(int index, String displayName, boolean alive, boolean source,String url) {
        this.index = index;
        this.displayName = displayName;
        this.alive = alive;
        this.url = url;
        this.source = source;
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

    public boolean isSource() {
        return source;
    }

    public static @Nonnull UniParcDatabase typeOf(@Nonnull String displayName) {
        return EnumDisplay.typeOf(displayName, UniParcDatabase.class);
    }
}
