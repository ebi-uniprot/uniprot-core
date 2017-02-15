package uk.ac.ebi.uniprot.domain.uniprot;

import java.util.Date;
import java.io.Serializable;

/**
 * An EntryAudit of a {@link UniProtEntry UniProtEntry}.
 * <br><br>
 * These values can be found in the DT lines of the flat file on the marked position.
 * In the current format, three values are supported.
 * <pre class="example"><font color="#AAAAAA"> ID   CYC_HUMAN      STANDARD;      PRT;   104 AA.
 * AC   P99999; P00001; Q6NUR2; Q6NX69; Q96BV4;
 * DT   <font color="#000000">21-JUL-1986 (Rel. 01, Created)</font>
 * DT   <font color="#000000">21-JUL-1986 (Rel. 01, Last sequence update)</font>
 * DT   <font color="#000000">01-FEB-2005 (Rel. 46, Last annotation update)</font>
 * DE   Cytochrome c.
 * ...</font></pre>
 * In XML this information is distributed in the entry (created, annotation update)
 * and sequence (sequence update) tags.<br><br> <B>NOTE THAT NO RELEASE NUMBER IS AVAILABLE
 * IN THE XML FORMAT</B><br><br>
 * <pre><font color="#AAAAAA"> ...
 * &lt;entry dataset="Swiss-Prot" created="<font color="#000000">1986-07-21</font>" modified="<font color="#000000">2005-02-01</font>"&gt;
 * &lt;accession&gt;P99999&lt;/accession&gt;
 * ...
 * &lt;sequence length="104" mass="11618" checksum="D47C9B513DF1C5C2" modified="<font color="#000000">1986-07-21</font>"&gt;
 * GDVEKGKKIFIMKCSQCHTVEKGGKHKTGPNLHGLFGRKTGQAPGYSYTA
 * ANKNKGIIWGEDTLMEYLENPKKYIPGTKMIFVGIKKKEERADLIAYLKK
 * ATNE
 * &lt;/sequence&gt;
 * ...
 * &lt;/entry&gt;</font></pre>
 */
public interface EntryAudit extends Serializable {

    /** Use getFirstPublicDate()
     *
     * @return
     */
    @Deprecated
    public Date getCreationDate();

    /** Use setFirstPublicDate
     *
     * @param date
     */
    @Deprecated
    public void setCreationDate(Date date);

    public Date getLastSequenceUpdateDate();
    public void setLastSequenceUpdateDate(Date date);

    public Date getLastAnnotationUpdateDate();
    public void setLastAnnotationUpdateDate(Date date);

    public Date getFirstPublicDate();
    public void setFirstPublicDate(Date date);

    public int getSequenceVersion();
    public void setSequenceVersion(int release);

    public int getEntryVersion();
    public void setEntryVersion(int release);
}
