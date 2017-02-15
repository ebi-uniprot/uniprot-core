package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.common.Value;

/**
 *
 * Encapsulates a single taxon in the taxonomy annotation of the {@link UniProtEntry UniProtEntry}.
 * <br><br>
 * These values can be found in the OC line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA"> ...
 * OS   Serratia marcescens.
 * OC   <font color="#000000">Bacteria</font>; <font color="#000000">Proteobacteria</font>; <font color="#000000">Gammaproteobacteria</font>; <font color="#000000">Enterobacteriales</font>;
 * OC   <font color="#000000">Enterobacteriaceae</font>; <font color="#000000">Serratia</font>.
 * OX   NCBI_TaxID=615;
 * ...</font></pre>
 * In XML:
 * <pre class="example"><font color="#AAAAAA"> ...
 * &lt;organism key="2"&gt;
 * &lt;name type="scientific"&gt;Serratia marcescens&lt;/name&gt;
 * &lt;dbReference type="NCBI Taxonomy" id="615" key="3"/&gt;
 * &lt;lineage&gt;
 * &lt;taxon&gt;<font color="#000000">Bacteria</font>&lt;/taxon&gt;
 * &lt;taxon&gt;<font color="#000000">Proteobacteria</font>&lt;/taxon&gt;
 * &lt;taxon&gt;<font color="#000000">Gammaproteobacteria</font>&lt;/taxon&gt;
 * &lt;taxon&gt;<font color="#000000">Enterobacteriales</font>&lt;/taxon&gt;
 * &lt;taxon&gt;<font color="#000000">Enterobacteriaceae</font>&lt;/taxon&gt;
 * &lt;taxon&gt;<font color="#000000">Serratia</font>&lt;/taxon&gt;
 * &lt;/lineage&gt;
 * &lt;/organism&gt;
 * ...
 * </font></pre>
 */

public interface NcbiTaxon extends Value {
}
