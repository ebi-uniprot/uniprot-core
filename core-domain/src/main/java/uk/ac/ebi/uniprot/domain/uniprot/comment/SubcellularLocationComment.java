package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.List;

public interface SubcellularLocationComment extends Comment{


    public String getMolecule();

    public Note getNote();

    public List<SubcellularLocation> getSubcellularLocations();
}
