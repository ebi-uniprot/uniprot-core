package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

public class LocationObject {
    private LocationValue subcellularLocation;
    private LocationValue topology;
    private LocationValue orientation;

    public LocationValue getSubcellularLocation() {
        return subcellularLocation;
    }

    public void setSubcellularLocation(LocationValue subcellularLocation) {
        this.subcellularLocation = subcellularLocation;
    }

    public LocationValue getTopology() {
        return topology;
    }

    public void setTopology(LocationValue topology) {
        this.topology = topology;
    }

    public LocationValue getOrientation() {
        return orientation;
    }

    public void setOrientation(LocationValue orientation) {
        this.orientation = orientation;
    }
}
