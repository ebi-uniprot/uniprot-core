package uk.ac.ebi.uniprot.domain.feature;

/**
 * Encapsulates the Variant Feature of a {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
 * <br><br>
 * VARIANT - Authors report that sequence variants exist.
 * <br><br>
 * These values can be found in the FT VARIANT line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA">   KW   Neurotransmitter degradation; Polymorphism; Serine esterase; Synapse.
 * FT   DISULFID     45     72       By similarity.
 * FT   <font color="#000000">VARIANT      97     97       G -> S (in strain Barriol, strain Espro,</font>
 * FT   <font color="#000000">                             strain Padova and strain Praias).</font>
 * FT   <font color="#000000">                             /FTId=VSP_004117.</font>
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
 * <font color="#000000">&lt;feature type="sequence variant" description="(in strain Barriol, strain Espro, strain Padova and strain Praias)" id="VAR_004117"&gt;
 *     &lt;original&gt;G&lt;/original&gt;
 *     &lt;variation&gt;S&lt;/variation&gt;
 *     &lt;location&gt;
 *       &lt;position position="97"/&gt;
 *     &lt;/location&gt;
 *   &lt;/feature&gt;</font>
 *  &lt;sequence length="365" mass="40846" checksum="8667AFF3F06C4932" modified="1993-04-01"&gt;
 * ...
 * </font></pre>
 */
public interface VariantFeature extends Feature, HasFeatureId, HasAlternativeSequence {
}
