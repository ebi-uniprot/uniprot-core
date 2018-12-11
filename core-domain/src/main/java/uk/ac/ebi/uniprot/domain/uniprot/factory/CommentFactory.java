package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CommentsImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.NoteImpl;

import java.util.List;

public enum CommentFactory {
    INSTANCE;

    public Comments createComments(List<Comment> comments){
        return new CommentsImpl(comments);
    }
    public <T extends Comment> T createComment(CommentBuilder<T> builder){
        return builder.build();
    }
    public APCommentBuilder createAPCommentBuilder() {
        return APCommentBuilder.newInstance();
    }

    public BPCPCommentBuilder createBPCPCommentBuilder() {
        return BPCPCommentBuilder.newInstance();
    }

    public CofactorCommentBuilder createCofactorCommentBuilder() {
        return CofactorCommentBuilder.newInstance();
    }

    public FreeTextCommentBuilder createFreeTextCommentBuilder() {
        return FreeTextCommentBuilder.newInstance();
    }

    public MassSpectrometryCommentBuilder createMassSpectrometryCommentBuilder() {
        return MassSpectrometryCommentBuilder.newInstance();
    }

    public RnaEditingCommentBuilder createRnaEditingCommentBuilder() {
        return RnaEditingCommentBuilder.newInstance();
    }

    public SequenceCautionCommentBuilder createSequenceCautionCommentBuilder() {
        return SequenceCautionCommentBuilder.newInstance();
    }

    public SubcellularLocationCommentBuilder createSubcellularLocationCommentBuilder() {
        return SubcellularLocationCommentBuilder.newInstance();
    }

    public WebResourceCommentBuilder createWebResourceCommentBuilder() {
        return WebResourceCommentBuilder.newInstance();
    }

    public DiseaseCommentBuilder createDiseaseCommentBuilder() {
        return DiseaseCommentBuilder.newInstance();
    }
    public DiseaseBuilder createDiseaseBuilder() {
        return DiseaseBuilder.newInstance();
    }

    public InteractionBuilder createInteractionBuilder() {
        return InteractionBuilder.newInstance();
    }


    public InteractionCommentBuilder createInteractionCommentBuilder() {
        return InteractionCommentBuilder.newInstance();
    }
    public Note createNote(List<EvidencedValue> texts) {
        return new NoteImpl(texts);
    }
}
