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

public final class CofactorCommentBuilder {
    private String molecule;
    private List<Cofactor> cofactors;
    private CommentNote note;

    public CofactorComment build() {
        return new CofactorCommentImpl(molecule, cofactors, note);
    }

    public CofactorCommentBuilder setMolecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public CofactorCommentBuilder setCofactors(List<Cofactor> cofactors) {
        this.cofactors = cofactors;
        return this;
    }

    public CofactorCommentBuilder setNote(CommentNote note) {
        this.note = note;
        return this;
    }

    public static CofactorReference createCofactorReference(CofactorReferenceType type, String referenceId) {
        return CofactorImpl.createCofactorReference(type, referenceId);
    }

    public static final class CofactorBuilder {
        private String name;
        private List<Evidence> evidences;
        private CofactorReference reference;

        public Cofactor build() {
            return new CofactorImpl(name, evidences, reference);
        }

        public CofactorBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CofactorBuilder setEvidences(List<Evidence> evidences) {
            this.evidences = evidences;
            return this;
        }

        public CofactorBuilder setCofactorReference(CofactorReference reference) {
            this.reference = reference;
            return this;
        }
    }
}
