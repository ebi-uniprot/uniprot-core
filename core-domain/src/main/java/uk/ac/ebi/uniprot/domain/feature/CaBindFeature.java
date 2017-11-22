package uk.ac.ebi.uniprot.domain.feature;

/**
 * Encapsulates the CaBindFeature of a {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
 * <br><br>
 * These values can be found in the FT BINDING line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA">
 * FT   FT   DOMAIN      131    162       EF-hand 4.
 * FT   <font color="#000000">CA_BIND      33     44       1 (Potential).</font>
 * FT   LIPID         1      1       N-myristoyl glycine (By similarity).
 * SQ   SEQUENCE   162 AA;  18508 MW;  FA1AD895E0EC4E66 CRC64;
 * ...</font></pre>
 */
public interface CaBindFeature extends Feature {
}
