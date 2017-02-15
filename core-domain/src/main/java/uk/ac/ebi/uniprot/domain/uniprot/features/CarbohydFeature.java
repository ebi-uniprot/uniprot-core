package uk.ac.ebi.uniprot.domain.uniprot.features;

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
public interface CarbohydFeature extends Feature, HasFeatureDescription, HasFeatureId {

    /**<pre class="example"><font color="#AAAAAA">
     * FT   CARBOHYD    143    143       <font color="#000000">N-linked</font> (GlcNAc...) (Potential).
     * </font></pre>
     * @return {@link CarbohydLinkType CarbohydLinkType} .
     */
    public CarbohydLinkType getCarbohydLinkType();

    public void setCarbohydLinkType(CarbohydLinkType carbohydLinkType);

    /**
     * <pre class="example"><font color="#AAAAAA">
     * FT   CARBOHYD    143    143      N-linked  <font color="#000000">(GlcNAc...)</font> (Potential).
     * </font></pre>
     *
     * @return {@link LinkedSugar LinkedSugar} .
     */
    public LinkedSugar getLinkedSugar();

    public void setLinkedSugar(LinkedSugar sugar);

}