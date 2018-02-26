package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;
import java.util.Optional;

public interface SubcellularLocationComment extends Comment{


    public Optional<String> getMolecule();

    public Optional<CommentNote> getNote();

    public List<SubcellularLocation> getSubcellularLocations();
}
