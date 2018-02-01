package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorReference;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.CofactorCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.CofactorImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.List;

public final class CofactorCommentBuilder implements CommentBuilder<CofactorComment> {
    private String molecule;
    private List<Cofactor> cofactors;
    private CommentNote note;

    public static CofactorCommentBuilder newInstance(){
        return new CofactorCommentBuilder();
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

    public CofactorCommentBuilder note(CommentNote note) {
        this.note = note;
        return this;
    }

    public static CofactorReference createCofactorReference(CofactorReferenceType type, String referenceId) {
        return CofactorImpl.createCofactorReference(type, referenceId);
    }

    public static Cofactor createCofactor(String name, List<Evidence> evidences, CofactorReference reference) {
        return new CofactorImpl(name, evidences, reference);
    }

}
