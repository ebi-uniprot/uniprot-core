package uk.ac.ebi.uniprot.domain.uniprot.evidences;
/** EvidenceType.java
 * 
 * Date: 17-Jan-2006
 */

/**
 * The EvidenceType of the {@link uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence Evidence}.
 * <br><br>
 * <p/>
 * This value can be found in the **EV lines of the flat file on the marked position.
 * <p/>
 * <pre class="example"><font color="#AAAAAA"> ...
 * **   #################    INTERNAL SECTION    ############
 * **EV EP2; <font color="#000000">TrEMBL</font>; -; AAB51527.1; 02-APR-2004.
 * **EV EI3; <font color="#000000">PIR</font>; -; S68266; 14-JUL-2005.
 * SQ   SEQUENCE   67 AA;  8012 MW;  57098438650D14B1 CRC64;
 * ...</font></pre>
 * <p/>
 * In XML:
 * <pre><font color="#AAAAAA"> ...
 * * &lt;keyword id="KW-0614" evidence="2">Plasmid&lt;/keyword>
 * &lt;evidence key="1" type="ECO:0000313">
 *      &lt;dbReference type="<font color="#000000">TrEMBL</font>" id="AAB51527.1"/>
 *  &lt;/source>
 * &lt;/evidence>
 *  &lt;evidence key="2" type="ECO:0000028">
 *      &lt;dbReference type="<font color="#000000">PIR</font>" id="S68266"/>
 *  &lt;/source>
 * &lt;/evidence>
 * &lt;sequence length="67" mass="8012" checksum="57098438650D14B1" modified="1997-07-01">
 * ...</font></pre>
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 */
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
    
    
    /*************************************** Deprecated constants *************************************/
    @Deprecated // replaced with HAMAP_RULE
    HAMAP("HAMAP"),
    @Deprecated // replaced with AGD
	AGD_ADD("AGD_ADD"),
    @Deprecated // replaced with CGD
    CGD_ADD("CGD_ADD"),
    @Deprecated
    COMMON_KNOWLEDGE("Common knowledge"),
    @Deprecated
    DECLEAN("DEClean"),
    @Deprecated
	ARATHFIX("ARATHfix"),
    @Deprecated // replaced with Ensembl
    ENSEMBL_ADD("ENSEMBL_ADD"),
    @Deprecated // replaced with FlyBase
	FLYBASE_ADD("FLYBASE_ADD"),
	@Deprecated // replaced with HGNC
    GENEW_ADD("GENEW_ADD"),
    @Deprecated //Deleted
    GENPEPTFIX("GenpeptFix"),
    @Deprecated
	HAMAPFIX("HAMAPfix"),
    @Deprecated  // replaced with HSSP
	HSSP_ADD("HSSP_ADD"),
    @Deprecated
    MERGE("Merge"),
    @Deprecated
    LITERATURE("Literature"),
    @Deprecated // replaced with MGI
	MGD_ADD("MGD_ADD"),
    @Deprecated // replaced with RGD
	RGD_ADD("RGD_ADD"),
    @Deprecated
	REFFIX("RefFix"),
    @Deprecated // replaced with SGD
	SGD_ADD("SGD_ADD"),
	@Deprecated // replaced with SAAS
    SPEARMINT("Spearmint"),
    @Deprecated  // replaced with TAIR
	TAIR_ADD("TAIR_ADD"),
    @Deprecated // replaced with WormBase
	WORMBASE_ADD("WORMBASE_ADD"),
    @Deprecated // replaced with WorkPep
	WORMPEP_ADD("WORMPEP_ADD"),
    @Deprecated // replaced with Xenbase
    XENBASE_ADD("XENBASE_ADD"),
    @Deprecated
	YEASTFIX("YEASTfix"),
    @Deprecated  // replaced with ZFIN
	ZFIN_ADD("ZFIN_ADD"),

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
