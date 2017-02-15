package uk.ac.ebi.uniprot.domain.uniprot.comments;

/**
 * Created by IntelliJ IDEA. User: twardell Date: 13-Jan-2010 Time: 12:59:14 To change this template use File | Settings
 * | File Templates.
 */
public interface SubcellularLocation {

    /**
     * @return for example <code>Cytoplasm, cytoskeleton</code>
     */

    public SubcellularLocationValue getLocation();

    public boolean hasLocation();

    public SubcellularLocationValue getTopology();

    public boolean hasTopology();

    public SubcellularLocationValue getOrientation();

    public boolean hasOrientation();
}
