package org.uniprot.core.uniprot;

import org.uniprot.core.Value;

/**
 * <h3>About This Data Structure</h3>
 * <p>
 * Encapsulates the UniProtKB ID of
 * a {@link UniProtEntry UniProtEntry}.
 * </p>
 * <h3>The Semantics of this Data Structure</h3>
 * <p>
 * The entry name of the sequence. This name is a useful
 * means of identifying a sequence, but it is not a stable identifier as is the accession number.
 * </p>
 * <h3>An Example in the UniProtKB Flat File</h3>
 * <p>
 * This value can be found in the ID line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA"> ID   <font color="#000000">CYC_HUMAN</font>      STANDARD;      PRT;   104 AA.
 * AC   P99999; P00001; Q6NUR2; Q6NX69; Q96BV4;
 * DT   21-JUL-1986 (Rel. 01, Created)
 * DT   21-JUL-1986 (Rel. 01, Last sequence update)
 * DT   01-FEB-2005 (Rel. 46, Last annotation update)
 * DE   Cytochrome c.
 * ...</font></pre>
 * In XML:
 * <pre><font color="#AAAAAA">&lt;entry dataset="Swiss-Prot" created="1986-07-21" modified="2005-02-01"&gt;
 * &lt;accession&gt;P99999&lt;/accession&gt;
 * &lt;accession&gt;P00001&lt;/accession&gt;
 * &lt;accession&gt;Q6NUR2&lt;/accession&gt;
 * &lt;accession&gt;Q6NX69&lt;/accession&gt;
 * &lt;accession&gt;Q96BV4&lt;/accession&gt;
 * &lt;name&gt;<font color="#000000">CYC_HUMAN</font>&lt;/name&gt;
 * &lt;protein&gt;
 * &lt;name&gt;Cytochrome c&lt;/name&gt;
 * &lt;/protein&gt;
 * ...
 * &lt;/entry&gt;</font></pre>
 * <h3>How to work with this Interface</h3>
 * <p>
 * The standard way of retrieving this data type
 * <p>
 * The standard way of setting this data type
 * <div class="codeexample"> {@link UniProtEntry UniProtEntry} entry = getEntryFromParserOrAPI();
 * entry.setUniProtId(DefaultUniProtFactory.getInstance().buildUniProtId("CYC_HUMAN"));
 * UniProtId id = entry.getUniProtId();</div>
 * </p>
 */
public interface UniProtId extends Value {

}
