package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.APCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.BPCPCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.CommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.DiseaseBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.DiseaseCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.InteractionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.MassSpectrometryCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.RnaEditingCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.SequenceCautionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.SubcellularLocationCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.builder.WebResourceCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.CommentNoteImpl;

import java.util.List;

public enum CommentFactory {
    INSTANCE;

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
    public CommentNote createCommentNote(List<EvidencedValue> texts) {
        return new CommentNoteImpl(texts);
    }
}
