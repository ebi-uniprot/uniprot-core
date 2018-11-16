package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.List;
import java.util.Optional;

public interface SubcellularLocationComment extends Comment{


    public Optional<String> getMolecule();

    public Optional<Note> getNote();

    public List<SubcellularLocation> getSubcellularLocations();
}
