package org.uniprot.core.uniprot.comment.builder;

import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseComment;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.impl.DiseaseCommentImpl;

public class DiseaseCommentBuilder
        implements CommentBuilder<DiseaseCommentBuilder, DiseaseComment> {
	String molecule;
    private Disease disease;
    private Note note;

    @Override
    public DiseaseComment build() {
        return new DiseaseCommentImpl(molecule, disease, note);
    }

    @Override
    public DiseaseCommentBuilder from(DiseaseComment instance) {
        return this.disease(instance.getDisease()).note(instance.getNote())
        		.molecule(instance.getMolecule());
    }

    public DiseaseCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }
    
    public DiseaseCommentBuilder disease(Disease disease) {
        this.disease = disease;
        return this;
    }

    public DiseaseCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }
}
