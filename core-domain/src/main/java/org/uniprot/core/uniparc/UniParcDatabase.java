package org.uniprot.core.uniparc;

import javax.annotation.Nonnull;

import org.uniprot.core.Database;
import org.uniprot.core.util.EnumDisplay;

public enum UniParcDatabase implements Database, EnumDisplay {
    EG_BACTERIA("EnsemblBacteria", true, "https://www.ensemblgenomes.org/id/%id"),
    EG_FUNGI("EnsemblFungi", true, "https://www.ensemblgenomes.org/id/%id"),
    EG_METAZOA("EnsemblMetazoa", true, "https://www.ensemblgenomes.org/id/%id"),
    EG_PLANTS("EnsemblPlants", true, "https://www.ensemblgenomes.org/id/%id"),
    EG_PROTISTS("EnsemblProtists", true, "https://www.ensemblgenomes.org/id/%id"),

    EMBL("EMBL", true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBLWGS("EMBLWGS", true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBL_CON("EMBL_CON", true, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBL_TPA("EMBL_TPA", false, "https://www.ebi.ac.uk/ena/browser/view/%id"),
    EMBL_TSA("EMBL_TSA", true, "https://www.ebi.ac.uk/ena/browser/view/%id"),

    ENSEMBL_VERTEBRATE("Ensembl", true, "https://www.ensembl.org/id/%id"),
    EPO("EPO", true, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=epo_prt&id=%id"),
    FLYBASE("FlyBase", true, "https://flybase.org/reports/%id.html"),
    H_INV("H-InvDB", false),
    IPI("IPI", false),

    JPO("JPO", true, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=jpo_prt&id=%id"),
    KIPO("KIPO", true, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=kipo_prt&id=%id"),
    PATRIC("PATRIC", true, "https://www.patricbrc.org/view/Feature/%id"),
    PDB(
            "PDB",
            true,
            "https://www.ebi.ac.uk/pdbe/entry/pdb/%id"), // need to remove the chain, eg "4q8n_A",
                                                         // just use "4q8n" as id
    PIR("PIR", false),

    PIRARC("PIRARC", false),
    PRF("PRF", false, "http://www.prf.or.jp/cgi-bin/seqget.pl?id=%id"),
    REFSEQ("RefSeq", true, "https://www.ncbi.nlm.nih.gov/protein/%id"),
    REMTREMBL("REMTREMBL", false),
    SEED("SEED", true, "https://pubseed.theseed.org/seedviewer.cgi?page=Annotation&feature=%id"),

    SGD("SGD", true, "https://www.yeastgenome.org/locus/%id"),
    SWISSPROT("UniProtKB/Swiss-Prot", true, "https://www.uniprot.org/uniprot/%id"),
    SWISSPROT_VARSPLIC(
            "UniProtKB/Swiss-Prot protein isoforms",
            true,
            "https://www.uniprot.org/uniprot/%id"), // swissprot isoform
    TAIR_ARABIDOPSIS(
            "TAIR",
            true,
            "https://www.arabidopsis.org/servlets/TairObject?type=aa_sequence&name=%id"),
    TREMBL("UniProtKB/TrEMBL", true, "https://www.uniprot.org/uniprot/%id"),

    TREMBLNEW("TREMBLNEW", false),
    TREMBL_VARSPLIC("TREMBL_VARSPLIC", false),
    TROME("TROME", true), // no link
    UNIMES("UNIMES", false),
    USPTO("USPTO", true, "https://www.ebi.ac.uk/Tools/dbfetch/dbfetch?db=uspto_prt&id=%id"),

    VECTORBASE("VectorBase", false),
    VEGA("VEGA", true, "https://vega.sanger.ac.uk/id/%id"),
    WORMBASE("WormBase", true, "https://wormbase.org/db/seq/protein?name=%id;class=CDS"),
    WORMBASE_PARASITE("WBParaSite", true, "https://parasite.wormbase.org/id/%id");

    private final String displayName;
    private final boolean alive;
    private final String url;

    UniParcDatabase(String displayName, boolean alive) {
        this(displayName, alive, "");
    }

    UniParcDatabase(String displayName, boolean alive, String url) {
        this.displayName = displayName;
        this.alive = alive;
        this.url = url;
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
