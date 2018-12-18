package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CofactorCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CofactorImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

public final class CofactorCommentBuilder implements CommentBuilder<CofactorComment> {
    private String molecule;
    private List<Cofactor> cofactors;
    private Note note;

    public static CofactorCommentBuilder newInstance() {
        return new CofactorCommentBuilder();
    }

    public static Cofactor createCofactor(String name, DBCrossReference<CofactorReferenceType> reference, List<Evidence> evidences) {
        return new CofactorImpl(name, reference, evidences);
    }

    public CofactorComment build() {
        return new CofactorCommentImpl(molecule, cofactors, note);
    }

    public CofactorCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public CofactorCommentBuilder cofactors(List<Cofactor> cofactors) {
        this.cofactors = cofactors;
        return this;
    }

    public CofactorCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }

}
