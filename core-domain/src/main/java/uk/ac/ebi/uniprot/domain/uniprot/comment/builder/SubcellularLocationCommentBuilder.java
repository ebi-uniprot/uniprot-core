package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SubcellularLocationCommentImpl;

import java.util.ArrayList;
import java.util.List;

public final class SubcellularLocationCommentBuilder implements CommentBuilder<SubcellularLocationCommentBuilder, SubcellularLocationComment> {
    private String molecule;
    private Note note;
    private List<SubcellularLocation> subcellularLocations = new ArrayList<>();

    public SubcellularLocationComment build() {
        return new SubcellularLocationCommentImpl(this);
    }

    @Override
    public SubcellularLocationCommentBuilder from(SubcellularLocationComment instance) {
        return new SubcellularLocationCommentBuilder()
                .molecule(instance.getMolecule())
                .subcellularLocations(instance.getSubcellularLocations())
                .note(instance.getNote());
    }

    public SubcellularLocationCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public SubcellularLocationCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public SubcellularLocationCommentBuilder subcellularLocations(List<SubcellularLocation> subcellularLocations) {
        this.subcellularLocations.addAll(subcellularLocations);
        return this;
    }

    public SubcellularLocationCommentBuilder addSubcellularLocation(SubcellularLocation subcellularLocation) {
        this.subcellularLocations.add(subcellularLocation);
        return this;
    }

    public String getMolecule() {
        return molecule;
    }

    public Note getNote() {
        return note;
    }

    public List<SubcellularLocation> getSubcellularLocations() {
        return subcellularLocations;
    }
}
