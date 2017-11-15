package uk.ac.ebi.uniprot.domain.uniprot.evidences;

public enum EvidenceType {

    //automated import
	AGD("AGD"),
	CGD("CGD"),
	EMBL("EMBL"),
    Ensembl("Ensembl"),
    FlyBase("FlyBase"),
    GENPEPT("Genpept"),
    HGNC("HGNC"),
    HSSP("HSSP"),
    MGI("MGI"),
    PDB("PDB"),
    PIR("PIR"),
    PROTCHANGE("ProtChange"),
    PROTIMP("ProtImp"),
    REFSEQ("RefSeq"),
    RGD("RGD"),
    SGD("SGD"),
    TAIR("TAIR"),
    TrEMBL("TrEMBL"),
    UNIPROT("UniProt"),
    WormBase("WormBase"),
    WormPep("WormPep"),
    Xenbase("Xenbase"),
    ZFIN("ZFIN"),
    EnsemblPlants("EnsemblPlants"),
    EnsemblFungi("EnsemblFungi"),
    EnsemblMetazoa("EnsemblMetazoa"),
    EnsemblProtists("EnsemblProtists"),
    EnsemblBacteria("EnsemblBacteria"),
    Proteomics("Proteomics"),
    PROTEOMES ("Proteomes"),
    WBParaSite("WBParaSite"),
    PomBase("PomBase"),
    VectorBase("VectorBase"),

    //Opinion, Experimental, Judgment
    CURATOR("Curator"),
	EXPERIMENTAL("Experimental"),
    OPINION("Opinion"),
    SIMILARITY("Similarity"),
    SEQUENCE_ANALAYSIS("Sequence analysis"),
    IMPORT("Import"),

    //Automated Annotation
    SAM("SAM"),
    PIRSR("PIRSR"),
    PIRNR("PIRNR"),
    RULEBASE("RuleBase"),
    SAAS("SAAS"),
    UniRule("UniRule"),
    HAMAP_RULE("HAMAP-Rule"),
    PROSITE_PRORULE("PROSITE-ProRule"),
    PeptideAtlas("PeptideAtlas"),
    MaxQB("MaxQB"),
    PROSITE("PROSITE"),
    //Reference
    PUBMED("PubMed"),
    UNIPROTKB("UniProtKB"),
    REFERENCE("Reference"),
    SMART("SMART"),
    PFAM("Pfam"),
    EPD("EPD"),
    ARAPORT("Araport"),
    NOT_SPECIFIED("");

	private String value;

	private EvidenceType(String type) {
		this.value = type;
	}

	public String getValue() {
		return value.toString();
	}

	public static EvidenceType typeOf(String value) {
		for (EvidenceType featureType : EvidenceType.values()) {
			if (featureType.getValue().equals(value)) {
				return featureType;
			}
		}
		throw new IllegalArgumentException("the evidence with the description " + value + " doesn't exist");
	}
}
