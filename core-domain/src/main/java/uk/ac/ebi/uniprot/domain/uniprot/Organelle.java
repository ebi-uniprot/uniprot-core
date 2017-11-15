package uk.ac.ebi.uniprot.domain.uniprot;

/**
 * Encapsulates a single gene coding of the {@link UniProtEntry UniProtEntry}.
 * The gene coding for a protein originates from the mitochondria, the chloroplast, the cyanelle, the nucleomorph or a plasmid.
 * <br><br>
* These values can be found in the OG line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA"> ...
* OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and
* OG   Plasmid IncFII R1-19 (R1 drd-19).
 * ...</font></pre>
 * In XML:
 * <pre class="example"><font color="#AAAAAA"> ...
 * &lt;geneLocation type="chloroplast"/&gt;
 * ...
 * </font></pre>
 */
public interface Organelle extends EvidencedValue  {
     GeneEncodingType getType();
     boolean hasValue();

}
