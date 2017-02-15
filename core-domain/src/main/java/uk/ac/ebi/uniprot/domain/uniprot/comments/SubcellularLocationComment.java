package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

public interface SubcellularLocationComment extends Comment{


    public SubcellularMolecule getSubcellularMolecule();

    public Note getSubcellularLocationNote();

    public List<SubcellularLocation> getSubcellularLocations();
}
