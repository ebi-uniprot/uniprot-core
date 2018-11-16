package uk.ac.ebi.uniprot.domain.uniprot.factory;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.APCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.BPCPCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.MassSpectrometryCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.RnaEditingCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SequenceCautionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SubcellularLocationCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.WebResourceCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.NoteImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CommentsImpl;

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
