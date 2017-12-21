package uk.ac.ebi.uniprot.domain.uniprot.comments;

public interface SubcellularLocation {

    public SubcellularLocationValue getLocation();

    public boolean hasLocation();

    public SubcellularLocationValue getTopology();

    public boolean hasTopology();

    public SubcellularLocationValue getOrientation();

    public boolean hasOrientation();
}
