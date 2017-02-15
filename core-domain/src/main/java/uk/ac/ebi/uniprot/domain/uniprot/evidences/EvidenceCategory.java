package uk.ac.ebi.uniprot.domain.uniprot.evidences;
/** EvidenceCategory.java
 * 
 * Date: 17-Jan-2006
 */

/**
 * The EvidenceCategory of the {@link uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence Evidence}.
 * <br><br>
 * <p/>
 * This value can be found in the **EV lines of the flat file on the marked position.
 * <p/>
 * <pre class="example"><font color="#AAAAAA"> ...
 * **   #################    INTERNAL SECTION    ############
 * **EV E<font color="#000000">P</font>2; TrEMBL; -; AAB51527.1; 02-APR-2004.
 * **EV E<font color="#000000">I</font>3; PIR; -; S68266; 14-JUL-2005.
 * SQ   SEQUENCE   67 AA;  8012 MW;  57098438650D14B1 CRC64;
 * ...</font></pre>
 * <p/>
 * There are four different categories can be found in an entry. It's coded in the entry as following:
 * <p/>
 * <b>A</b> - indicates 'automatic annotation' {@link #AA}. This tag is to be used for systems like Rulebase, SPEARMINT etc.
 * Other programs should not use this tag even if they are annotating automatically.
 * <br>
 * <b>C</b> Curation {@link #CURATION}.
 * <br>
 * <b>I</b> indicates 'import from an external source of data' {@link #IMPORT}, and should be used for all programs not in category 'A' that need to consult data sources other than the TrEMBL entry itself.
 * <br>
 * <b>P</b> is to be used for other programs {@link #PROGRAMME}.
 * <p/>
 * <p/>
 * In XML:
 *  XML does not represent the Evidence category, this can be deduced from the {@link EvidenceCode EvidenceCode}
 *
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 */
public enum EvidenceCategory {

	AA,
	CURATION,
	IMPORT,
	PROGRAMME,
	NOT_SPECIFIED;

    public static EvidenceCategory typeOf(char value) {
        switch (value) {
            case 'A': return AA;
            case 'C': return CURATION;
            case 'I': return IMPORT;
            case 'P': return PROGRAMME;
        }
        throw new IllegalArgumentException(value+" is not a valid evidence type");
    }

    public String getValue(){
        switch (this){
            case AA: return "A";
            case CURATION: return "C";
            case IMPORT: return "I";
            case PROGRAMME: return "P";
            default:
                throw new IllegalArgumentException();
        }
       
    }
	
}
