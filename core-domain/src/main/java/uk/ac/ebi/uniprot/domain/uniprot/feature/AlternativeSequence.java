package uk.ac.ebi.uniprot.domain.uniprot.feature;

import java.io.Serializable;
import java.util.List;

/**
 * Indicates that a {@link uk.ac.ebi.Feature.interfaces.uniprot.oldfeatures.Feature Feature} in an UniProtEntry can have an alternative sequence ({@link uk.ac.ebi.kraken.interfaces.uniprot.oldfeatures.SubSequence SubSequence}).
 * <br><br>
 * These values can be found in the FT line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA">   KW   Neurotransmitter degradation; Polymorphism; Serine esterase; Synapse.
 * FT   DISULFID     45     72       By similarity.
 * FT   VARIANT      97     97       <font color="#000000">G -> S</font> (in strain Barriol, strain Espro,
 * FT                                strain Padova and strain Praias).
 * FT                                /FTId=VSP_004117.
 * SQ   SEQUENCE   132 AA;  14543 MW;  2F40C4B3FC2468E8 CRC64;
 * ...</font></pre>
 * In XML:
 * <pre> <font color="#AAAAAA">...
 * &lt;feature type="disulfide bond" status="by similarity"&gt;
 *     &lt;location&gt;
 *       &lt;begin position="227"/&gt;
 *       &lt;end position="283"/&gt;
 *     &lt;/location&gt;
 *   &lt;/feature&gt;
 * &lt;feature type="sequence variant" description="(in strain Barriol, strain Espro, strain Padova and strain Praias)" id="VAR_004117"&gt;
 *     <font color="#000000">&lt;original&gt;G&lt;/original&gt;
 *     &lt;variation&gt;S&lt;/variation&gt;</font>
 *     &lt;location&gt;
 *       &lt;position position="97"/&gt;
 *     &lt;/location&gt;
 *   &lt;/feature&gt;
 *  &lt;sequence length="365" mass="40846" checksum="8667AFF3F06C4932" modified="1993-04-01"&gt;
 * ...
 * </font></pre>
 */
public interface AlternativeSequence extends Serializable {

	String getOriginalSequence();
	List<String> getAlternativeSequences();
	//public SequenceReport getReport();

}
