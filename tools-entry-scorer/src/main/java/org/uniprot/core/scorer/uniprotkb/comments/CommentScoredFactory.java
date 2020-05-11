package org.uniprot.core.scorer.uniprotkb.comments;

import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;

import java.util.List;

public class CommentScoredFactory {
    public static CommentScored create(Comment t) {
        return create(t, null);
    }

    public static CommentScored create(Comment t, List<EvidenceDatabase> evidenceDatabases) {
        CommentScored commentScored = null;
        switch (t.getCommentType()) {
            case ALTERNATIVE_PRODUCTS:
                commentScored =
                        new AlternativeProductsCommentScored(
                                (AlternativeProductsComment) t, evidenceDatabases);
                break;
            case BIOPHYSICOCHEMICAL_PROPERTIES:
                commentScored =
                        new BioPhysicoChemicalPropertiesCommentScored(
                                (BPCPComment) t, evidenceDatabases);
                break;
            case CAUTION:
                commentScored = new CautionCommentScored((FreeTextComment) t, evidenceDatabases);
                break;
            case COFACTOR:
                commentScored = new CofactorCommentScored((CofactorComment) t, evidenceDatabases);
                break;
            case DISEASE:
                commentScored = new DiseaseCommentScored((DiseaseComment) t, evidenceDatabases);
                break;
            case INTERACTION:
                commentScored =
                        new InteractionCommentScored((InteractionComment) t, evidenceDatabases);
                break;
            case MASS_SPECTROMETRY:
                commentScored =
                        new MassSpectrometryCommentScored(
                                (MassSpectrometryComment) t, evidenceDatabases);
                break;
            case MISCELLANEOUS:
                commentScored =
                        new MiscellaneousCommentScored((FreeTextComment) t, evidenceDatabases);
                break;
            case RNA_EDITING:
                commentScored =
                        new RnaEditingCommentScored((RnaEditingComment) t, evidenceDatabases);
                break;
            case SEQUENCE_CAUTION:
                commentScored =
                        new SequenceCautionCommentScored(
                                (SequenceCautionComment) t, evidenceDatabases);
                break;
            case SIMILARITY:
                commentScored = new SimilarityCommentScored((FreeTextComment) t, evidenceDatabases);
                break;
            case SUBCELLULAR_LOCATION:
                commentScored =
                        new SubcellularLocationCommentScored(
                                (SubcellularLocationComment) t, evidenceDatabases);
                break;
            case WEBRESOURCE:
                commentScored =
                        new WebResourceCommentScored((WebResourceComment) t, evidenceDatabases);
                break;
            case CATALYTIC_ACTIVITY:
                commentScored =
                        new CatalyticActivityCommentScored(
                                (CatalyticActivityComment) t, evidenceDatabases);
                break;
            default:
                commentScored =
                        new DefaultTextOnlyCommentScored((FreeTextComment) t, evidenceDatabases);
                break;
        }
        return commentScored;
    }
}
