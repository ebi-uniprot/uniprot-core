package uk.ac.ebi.uniprot.domain.feature;

/**
 * Encapsulates the ActSiteFeature of a {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
 * <br><br>
 * These values can be found in the FT ACT_SITE line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA">   KW   Oxidoreductase.
 * FT   NP_BIND      11     35       NAD (By similarity).
 * FT   <font color="#000000">ACT_SITE    157    157       Proton acceptor (By similarity).</font>
 * FT   BINDING     144    144       Substrate (By similarity).
 * SQ   SEQUENCE   261 AA;  27265 MW;  B87C83F45A88B7A9 CRC64;
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
public interface ActSiteFeature extends Feature {


}
