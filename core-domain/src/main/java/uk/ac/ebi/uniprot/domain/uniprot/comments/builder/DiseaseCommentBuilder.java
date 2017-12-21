package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseDescription;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseId;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseReference;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.DiseaseCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.DiseaseImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.List;

public final class DiseaseCommentBuilder {
    private Disease disease;
    private CommentNote note;

    public DiseaseComment build() {
        return new DiseaseCommentImpl(disease, note);
    }

    public DiseaseCommentBuilder setDisease(Disease disease) {
        this.disease = disease;
        return this;
    }

    public DiseaseCommentBuilder setNote(CommentNote note) {
        this.note = note;
        return this;
    }

    public static DiseaseId createDiseaseId(String val) {
        return DiseaseImpl.createDiseaseId(val);
    }

    public static DiseaseReference createDiseaseReference(DiseaseReferenceType referenceType, String referenceId) {
        return DiseaseImpl.createDiseaseReference(referenceType, referenceId);
    }

    public static DiseaseDescription createDiseaseDescription(String val, List<Evidence> evidences) {
        return DiseaseImpl.createDiseaseDescription(val, evidences);
    }

    public static final class DiseaseBuilder {
        private DiseaseId diseaseId;
        private String acronym;
        private DiseaseDescription description;
        private DiseaseReference reference;

        public Disease build() {
            return new DiseaseImpl(diseaseId, acronym, description,
                    reference);
        }

        public DiseaseBuilder setDiseaseId(DiseaseId diseaseId) {
            this.diseaseId = diseaseId;
            return this;
        }

        public DiseaseBuilder setAcronym(String acronym) {
            this.acronym = acronym;
            return this;
        }

        public DiseaseBuilder setDescription(DiseaseDescription description) {
            this.description = description;
            return this;
        }

        public DiseaseBuilder setReference(DiseaseReference reference) {
            this.reference = reference;
            return this;
        }
    }
}
