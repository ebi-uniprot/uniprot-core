package uk.ac.ebi.uniprot.domain.uniprot.evidences;


import java.time.LocalDate;

// **EV EI4; PIR; -; T37459; 14-JUL-2005.
// **EV EI3; ProtImp; -; -; 14-NOV-2005.
// **EV EA3; Rulebase; SPTR; RU000478V2.80; 16-JUN-2005.
// **EV EA4; SAAS; TREMBL; SAAS011545; 13-JUL-2005.
// **EV EP2; TrEMBL; -; EAN00137.1; 02-SEP-2005.

// more in $PRO1/automated_annotation/data/out.txt
// grep "^..EV .*HSSP" $DAT/sptr/trembl.txl

/**
 * The Evidence annotation of the {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
 * <br><br>
 * These values can be found in the **EV lines of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA"> ...
 * DR   EMBL; U93082; AAB51527.1; -.{EI1}
 * KW   Plasmid{EP2}.
 * **
 * **   #################    INTERNAL SECTION    ############
 * <font color="#000000">**EV EI1; EMBL; -; AAB51527.1; 02-APR-2004.</font>
 * <font color="#000000">**EV EP2; TrEMBL; -; AAB51527.1; 02-APR-2004.</font>
 * SQ   SEQUENCE   67 AA;  8012 MW;  57098438650D14B1 CRC64;
 * ...</font></pre>
 * In XML:
 * <pre><font color="#AAAAAA"> ...
 * &lt;keyword id="KW-0614" evidence="2">Plasmid&lt;/keyword>
 * <font color="#000000">&lt;evidence key="1" type="ECO:0000313">
 *      &lt;dbReference type="EMBL" id="AAB51527.1"/>
 *  &lt;/source>
 * &lt;/evidence></font>
 * <font color="#000000"> &lt;evidence key="<font color="#000000">2</font>" type="ECO:0000028">
 *      &lt;dbReference type="TrEMBL" id="AAB51527.1"/>
 *  &lt;/source>
 * &lt;/evidence></font>
 * &lt;sequence length="67" mass="8012" checksum="57098438650D14B1" modified="1997-07-01">
 * ...</font></pre>
 */
public interface Evidence extends Comparable<Evidence> {
	 LocalDate getDate();
	 EvidenceType getType();
	 EvidenceCode getEvidenceCode();
	 String getAttribute();
	 String getValue();
}
