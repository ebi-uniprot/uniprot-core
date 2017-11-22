package uk.ac.ebi.uniprot.domain.uniprot.xdb;


import uk.ac.ebi.uniprot.domain.xdb.DatabaseName;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A list of all Cross reference databases in UniProt.
 * 
 * @author jieluo
 *
 */
public enum DatabaseType  implements DatabaseName{

    EMBL("EMBL", 4),
    PIR("PIR"), // default is 2
    REFSEQ("RefSeq"),
    UNIGENE("UniGene"),
    PDB("PDB", 4),
    HSSP("HSSP"),
    SMR("SMR"),
    DISPROT("DisProt"),
    DIP("DIP"),
    INTACT("IntAct"),
    MEROPS("MEROPS"),
    PEROXIBASE("PeroxiBase"),
    PPTASEDB("PptaseDB"),
    REBASE("REBASE"),
    TRANSFAC("TRANSFAC"),
    GLYCOSUITEDB("GlycoSuiteDB"),
    PHOSSITE("PhosSite"),
    SWISS2DPAGE("SWISS-2DPAGE"),
    AARHUSGHENT("Aarhus/Ghent-2DPAGE"),
    ANU2DPAGE("ANU-2DPAGE"),
    COMPLUYEAST2DPAGE("COMPLUYEAST-2DPAGE"),
    CORNEA2DPAGE("Cornea-2DPAGE"),
    DOSACCOBS2DPAGE("DOSAC-COBS-2DPAGE"),
    ECO2DBASE("ECO2DBASE"),
    HSC2DPAGE("HSC-2DPAGE"),
    OGP("OGP"),
    PHCI2DPAGE("PHCI-2DPAGE"),
    PMMA2DPAGE("PMMA-2DPAGE"),
    RATHEART2DPAGE("Rat-heart-2DPAGE"),
    REPRODUCTION2DPAGE("REPRODUCTION-2DPAGE"),
    SIENA2DPAGE("Siena-2DPAGE"),
    PEPTIDEATLAS("PeptideAtlas"),
    ENSEMBL("Ensembl", 3),
    GENEID("GeneId", "GeneID"),
    GENOMEREVIEWS("GenomeReviews"),
    KEGG("KEGG"),
    TIGR("TIGR"),
    AGD("AGD"),
    BURULIST("BuruList"),
    CYGD("CYGD"),
    DICTYBASE("dictyBase"),
    ECHOBASE("EchoBASE"),
    ECOGENE("EcoGene"),
    EUHCVDB("euHCVdb"),
    FLYBASE("FlyBase"),
    GENEDBSPOMBE("GeneDB_Spombe"),
    GENEFARM("GeneFarm"),
    GRAMENE("Gramene", 3),
    HINVDB("H-InvDB"),
    HGNC("HGNC"),
    HIV("HIV"),
    HPA("HPA"),
    LEGIOLIST("LegioList"),
    LEPROMA("Leproma"),
    LISTILIST("ListiList"),
    MAIZEGDB("MaizeGDB"),
    MIM("MIM"),
    MGI("MGI"),
    MYPULIST("MypuList"),
    ORPHANET("Orphanet"),
    PHARMGKB("PharmGKB"),
    PHOTOLIST("PhotoList"),
    PSEUDOCAP("PseudoCAP"),
    RGD("RGD"),
    SAGALIST("SagaList"),
    SGD("SGD"),
    STYGENE("StyGene"),
    SUBTILIST("SubtiList"),
    TAIR("TAIR"),
    TUBERCULIST("TubercuList"),
    WORMBASE("WormBase", 4),
    WORMPEP("WormPep"),
    ZFIN("ZFIN"),
    BIOCYC("BioCyc"),
    REACTOME("Reactome"),
    DRUGBANK("DrugBank"),
    LINKHUB("LinkHub"),
    CLEANEX("CleanEx"),
    GERMONLINE("GermOnline"),
    GO("Go", "GO", 3),
    HAMAP("HAMAP", 3),
    INTERPRO("InterPro"),
    GENE3D("Gene3D", 3),
    PANTHER("PANTHER", 3),
    PFAM("Pfam", 3),
    PIRSF("PIRSF", 3),
    PRINTS("PRINTS"),
    PRODOM("ProDom", 3),
    SMART("SMART", 3),
    TIGRFAMS("TIGRFAMs", 3),
    PROSITE("PROSITE", 3),
    MAIZE2DPAGE("Maize-2DPAGE"),
    RZPDPROTEXP("RZPD-ProtExp"),
    UNIPEP("UniPep"),
    PDBSUM("PDBsum"),
    GCRDB("Gcrdb", "GCRDb"),
    CARBBANK("CarbBank"),
    GENEW("Genew"),
    GK("GK"),
    MAIZEDB("MaizeDB"),
    MENDEL("Mendel"),
    MGD("MGD"),
    YEPD("YEPD"),
    VECTORBASE("VectorBase", 3),
    WORLD2DPAGE("World-2DPAGE"),
    PHOSPHOSITEPLUS("PhosphoSitePlus"),
    TWODBASEECOLI("2DBase-Ecoli"),
    PROMEX("ProMEX"),
    NMPDR("NMPDR"),
    CGD("CGD"),
    HOGENOM("HOGENOM"),
    HOVERGEN("HOVERGEN"),
    BINDINGDB("BindingDB"),
    NEXTBIO("NextBio"),
    XENBASE("Xenbase"),
    GENECARDS("GeneCards"),
    PRIDE("PRIDE"),
    BRENDA("BRENDA"),
    BGEE("Bgee"),
    IPI("IPI"),
    TCDB("Tcdb", "TCDB"),
    PATHWAYINTERACTIONDB("Pathway_Interaction_DB"),
    CAZY("CAZy"),
    OMA("OMA"),
    PMAPCUTDB("PMAP-CutDB"),
    UCSC("Ucsc", "UCSC"),
    CTD("Ctd", "CTD"),
    STRINGXREF("String", "STRING"),

    PHYLOMEDB("PhylomeDB"),
    ORTHODB("OrthoDB"),
    EGGNOG("eggNOG"),
    INPARANOID("InParanoid"),
    ARACHNOSERVER("ArachnoServer"),
    EUPATHDB("EuPathDB"),
    PROTCLUSTDB("ProtClustDB"),
    SUPFAM("SUPFAM", 3),
    GENOLIST("GenoList"),
    MINT("MINT"),
    CONOSERVER("ConoServer"),
    UCD2DPAGE("UCD-2DPAGE"),
    ENSEMBLBACTERIA("EnsemblBacteria", 3),
    ENSEMBLFUNGI("EnsemblFungi", 3),
    ENSEMBLMETAZOA("EnsemblMetazoa", 3),
    ENSEMBLPLANTS("EnsemblPlants", 3),
    ENSEMBLPROTISTS("EnsemblProtists", 3),
    PROTEINMODELPORTAL("ProteinModelPortal"),
    ALLERGOME("Allergome"),
    NEXTPROT("neXtProt"),
    GENETREE("GeneTree"),
    KO("KO"),
    DMDM("DMDM"),
    PATRIC("PATRIC"),
    POMBASE("PomBase"),
    DNASU("DNASU"),
    EVOLUTIONARYTRACE("EvolutionaryTrace"),
    UNIPATHWAY("UniPathway"),
    GENOMERNAI("GenomeRNAi"),
    CHEMBL("ChEMBL"),
    PAXDB("PaxDb"),
    MYCOCLAP("mycoCLAP"),
    SABIORK("SABIO-RK"),
    CHITARS("ChiTaRS"),
    SIGNALINK("SignaLink"),
    UNICARBKB("UniCarbKB"),
    GENEWIKI("GeneWiki"),
    PRO("PRO"),
    GUIDETOPHARMACOLOGY("GuidetoPHARMACOLOGY"),
    TREEFAM("TreeFam"),
    BIOGRID("BioGrid"),
    MAXQB("MaxQB"),
    GENEREVIEWS("GeneReviews"),
    CCDS("CCDS"),
    EXPRESSIONATLAS("ExpressionAtlas"),
    PROTEOMES("Proteomes"),
    MOONPROT("MoonProt"),
    DEPOD("DEPOD"),
    WBPARASITE("WBParaSite", 3),
    BIOMUTA("BioMuta"),
    ESTHER("ESTHER"),
    GENEVISIBLE("Genevisible"),
    SWISSPALM("SwissPalm"),

    EPD("EPD"),
    CTDP("TopDownProteomics"),
    SIGNOR("SIGNOR"),
    CDD("CDD", 3),

    SWISSLIPIDS("SwissLipids"),
    MALACARDS("MalaCards"),
    IPTMNET("iPTMnet"),
    COLLECTF("CollecTF"),
    GENEDB("GeneDB"),
    OPENTARGETS("OpenTargets"),
    DISGENET("DisGeNET"),
    SFLD("SFLD", 3),
    ARAPORT("Araport"),
    IMGT_GENE_DB("IMGT_GENE-DB"),
    ELM("ELM"),
    CORUM("CORUM"),
    UNKNOWN("UNKNOWN");
    private String name;
    private String displayName;
    private int numberOfAttribute;
    private static Map<String, DatabaseType> mapName2Type;
    private static Map<String, DatabaseType> mapName2TypeWithUpperCase;
    static {
        if (mapName2Type == null) {
            mapName2Type = new ConcurrentHashMap<>();
            mapName2TypeWithUpperCase = new ConcurrentHashMap<>();
            for (DatabaseType type : values()) {
                mapName2Type.put(type.getName(), type);
                mapName2TypeWithUpperCase.put(type.getName().toUpperCase(), type);
            }
        }
    };

    DatabaseType(String name) {
        this(name, 2);
    }

    DatabaseType(String name, String displayName) {
        this(name, displayName, 2);
    }

    DatabaseType(String name, int num) {
        this(name, name, num);
    }

    DatabaseType(String name, String displayName, int num) {
        this.name = name;
        this.displayName = displayName;
        numberOfAttribute = num;
    }

    public int getNumberOfAttribute() {
        return this.numberOfAttribute;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return this.displayName + "; ";
    }

    public static DatabaseType getDatabaseType(String databaseName) {

        DatabaseType type = mapName2Type.get(databaseName);
        if (type != null)
            return type;
        type = mapName2TypeWithUpperCase.get(databaseName.toUpperCase());
        if (type == null) {
            // System.err.println(databaseName + " has not dbtype set");
            return DatabaseType.UNKNOWN;
        } else
            return type;
    }

  

}
