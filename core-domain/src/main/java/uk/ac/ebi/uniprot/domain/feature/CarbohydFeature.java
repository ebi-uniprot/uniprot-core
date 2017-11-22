package uk.ac.ebi.uniprot.domain.feature;

/**
 * Encapsulates the CarbohydFeature of a {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.
 * <br><br>
 * These values can be found in the FT CARBOHYD line of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA">
 * FT   TOPO_DOM     26    371       Lumenal (Potential).
 * <p/>
 * FT   <font color="#000000">CARBOHYD    143    143       N-linked (GlcNAc...) (Potential).</font>
 * FT   VARIANT     344    344       G -> W (in strain BLG2/Msf).
 * SQ   SEQUENCE   371 AA;  41236 MW;  323A7FFA56B723B3 CRC64;
 * ...</font></pre>
 */
public interface CarbohydFeature extends Feature, HasFeatureId {

    public CarbohydLinkType getCarbohydLinkType();

    public LinkedSugar getLinkedSugar();


}