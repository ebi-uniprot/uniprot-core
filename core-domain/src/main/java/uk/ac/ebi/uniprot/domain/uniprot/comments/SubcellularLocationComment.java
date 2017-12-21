package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

public interface SubcellularLocationComment extends Comment{


    public String getSubcellularMolecule();

    public CommentNote getSubcellularLocationNote();

    public List<SubcellularLocation> getSubcellularLocations();
}
