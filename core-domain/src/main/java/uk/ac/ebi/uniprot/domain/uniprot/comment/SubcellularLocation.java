package uk.ac.ebi.uniprot.domain.uniprot.comment;

public interface SubcellularLocation {

    SubcellularLocationValue getLocation();

    SubcellularLocationValue getTopology();

    SubcellularLocationValue getOrientation();

}
