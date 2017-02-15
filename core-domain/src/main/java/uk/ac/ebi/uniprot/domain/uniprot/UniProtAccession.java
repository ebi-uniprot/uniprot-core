package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.common.Value;


/**
 * @author jieluo
 * One UniProtAccession of a {@link UniProtEntry UniProtEntry}.
 * <br><br>
 * This value can be found in the AC line of the flat file on the marked position.
 * Note that the first accession number in the list is the primary accession number,
 * whereas the rest are secondary accession numbers.
 * <pre class="example"><font color="#AAAAAA"> ID   CYC_HUMAN      STANDARD;      PRT;   104 AA.
 * AC   <font color="#000000">P99999</font>; <font color="#000000">P00001</font>; <font color="#000000">Q6NUR2</font>; <font color="#000000">Q6NX69</font>; <font color="#000000">Q96BV4</font>;
 * DT   21-JUL-1986 (Rel. 01, Created)
 * DT   21-JUL-1986 (Rel. 01, Last sequence update)
 * DT   01-FEB-2005 (Rel. 46, Last annotation update)
 * DE   Cytochrome c.
 * ...</font></pre>
 * In XML:
 * <pre><font color="#AAAAAA"> ...
 * &lt;entry dataset="Swiss-Prot" created="1986-07-21" modified="2005-02-01"&gt;
 * &lt;accession&gt;<font color="#000000">P99999</font>&lt;/accession&gt;
 * &lt;accession&gt;<font color="#000000">P00001</font>&lt;/accession&gt;
 * &lt;accession&gt;<font color="#000000">Q6NUR2</font>&lt;/accession&gt;
 * &lt;accession&gt;<font color="#000000">Q6NX69</font>&lt;/accession&gt;
 * &lt;accession&gt;<font color="#000000">Q96BV4</font>&lt;/accession&gt;
 * &lt;name&gt;CYC_HUMAN&lt;/name&gt;
 * &lt;protein&gt;
 * &lt;name&gt;Cytochrome c&lt;/name&gt;
 * &lt;/protein&gt;
 * ...
 * &lt;/entry&gt;</font></pre>
 */

public interface UniProtAccession extends Value {
    boolean isValidAccession();
}
