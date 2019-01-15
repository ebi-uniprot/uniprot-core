package uk.ac.ebi.uniprot.domain.uniprot.comment2.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.impl.CofactorCommentImpl;

import java.util.ArrayList;
import java.util.List;

public final class CofactorCommentBuilder implements CommentBuilder<CofactorCommentBuilder, CofactorComment> {
    private String molecule;
    private List<Cofactor> cofactors = new ArrayList<>();
    private Note note;

//    public static CofactorCommentBuilder newInstance() {
//        return new CofactorCommentBuilder();
//    }
//
//    public static Cofactor createCofactor(String name, DBCrossReference<CofactorReferenceType> reference, List<Evidence> evidences) {
//        return new CofactorImpl(name, reference, evidences);
//    }
    public CofactorComment build() {
        return new CofactorCommentImpl(this);
    }

    @Override
    public CofactorCommentBuilder from(CofactorComment instance) {
        return new CofactorCommentBuilder()
                .cofactors(instance.getCofactors())
                .molecule(instance.getMolecule())
                .note(instance.getNote());
    }

    public CofactorCommentBuilder molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public CofactorCommentBuilder cofactors(List<Cofactor> cofactors) {
        this.cofactors.addAll(cofactors);
        return this;
    }

    public CofactorCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public String getMolecule() {
        return molecule;
    }

    public List<Cofactor> getCofactors() {
        return cofactors;
    }

    public Note getNote() {
        return note;
    }
}
