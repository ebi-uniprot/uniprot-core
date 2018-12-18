package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SubcellularLocationCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SubcellularLocationImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

public final class SubcellularLocationCommentBuilder implements CommentBuilder<SubcellularLocationComment> {
    private String molecule;
    private List<SubcellularLocation> subcellularLocations;
    private Note note;

    public static SubcellularLocationCommentBuilder newInstance() {
        return new SubcellularLocationCommentBuilder();
    }

    public static SubcellularLocationValue createSubcellularLocationValue(String value, List<Evidence> evidences) {
        return SubcellularLocationImpl.createSubcellularLocationValue(value, evidences);
    }

    public static SubcellularLocation createSubcellularLocation(SubcellularLocationValue location,
                                                                SubcellularLocationValue topology,
                                                                SubcellularLocationValue orientation) {
        return new SubcellularLocationImpl(location, topology, orientation);
    }

    public SubcellularLocationComment build() {
        return new SubcellularLocationCommentImpl(molecule,
                                                  subcellularLocations, note);
    }

    public SubcellularLocationCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public SubcellularLocationCommentBuilder subcellularLocations(List<SubcellularLocation> subcellularLocations) {
        this.subcellularLocations = subcellularLocations;
        return this;
    }

    public SubcellularLocationCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }
}
