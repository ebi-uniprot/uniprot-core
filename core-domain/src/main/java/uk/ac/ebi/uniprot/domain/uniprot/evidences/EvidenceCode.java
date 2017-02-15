package uk.ac.ebi.uniprot.domain.uniprot.evidences;

/**
 * The EvidenceCode of the {@link uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence Evidence}.
 * <br><br>
 * List of evidence codes used by UniProt. Evidence codes are ontology codes that help explain the provenance
 * of the evidence.
 * <br><br>
 * UniProt supports a subset of available ECO codes @see <a href="http://obofoundry.org/cgi-bin/detail.cgi?id=evidence_code">ECO</a>
 * <br><br>
 * This value can be found in XML:
 *
 * <pre><font color="#AAAAAA"> ...
 * &lt;keyword id="KW-0614" evidence="2">Plasmid&lt;/keyword>
 * &lt;evidence key="1" type="<font color="#000000">ECO:0000313</font>">
 *      &lt;dbReference type="TrEMBL" id="AAB51527.1"/>
 *  &lt;/source>
 * &lt;/evidence>
 *  &lt;evidence key="2" type="<font color="#000000">ECO:0000028</font>">
 *      &lt;dbReference type="PIR" id="S68266"/>
 *  &lt;/source>
 * &lt;/evidence>
 * &lt;sequence length="67" mass="8012" checksum="57098438650D14B1" modified="1997-07-01">
 * ...</font></pre>

 * @author Ricardo Antunes
 */
public enum EvidenceCode {
    ECO_0000269("ECO:0000269","experimental evidence"),
    ECO_0000303("ECO:0000303","non-traceable author statement"),
    ECO_0000305("ECO:0000305","inference from background scientific knowledge"),
    ECO_0000250("ECO:0000250","sequence similarity"),
    ECO_0000312("ECO:0000312","imported information used in manual assertion"),
    ECO_0000255("ECO:0000255","curated automatic assertion"),
    ECO_0000244("ECO:0000244","curated literature reference"),
    ECO_0000257("ECO:0000257","motif similarity"),
 
    ECO_0000256("ECO:0000256","automatic assertion"),
    ECO_0000313("ECO:0000313","imported information used in automatic assertion"),
    ECO_0000213("ECO:0000213","literature reference"),
    ECO_0000259("ECO:0000259","InterPro signature evidence used in automatic assertion"),
   
    
    NOT_SPECIFIED("NOT_SPECIFIED","NOT_SPECIFIED");

    private String code;
    private String displayName;

    private EvidenceCode(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCodeValue() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EvidenceCode typeOf(String ecoCode) {
        for(EvidenceCode evidenceCode : EvidenceCode.values()) {
            if(evidenceCode.getCodeValue().equalsIgnoreCase(ecoCode))
                return evidenceCode;
        }

        throw new IllegalArgumentException(
                String.format("The EvidenceCode with ECO code: %s does not exist", ecoCode));
    }
}
