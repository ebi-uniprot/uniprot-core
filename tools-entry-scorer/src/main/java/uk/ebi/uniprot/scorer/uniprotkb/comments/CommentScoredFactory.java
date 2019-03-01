package uk.ebi.uniprot.scorer.uniprotkb.comments;


import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;

import java.util.List;

public class CommentScoredFactory {
    public static CommentScored create(Comment t) {
        return create(t, null);

    }

    public static CommentScored create(Comment t, List<EvidenceType> evidenceTypes) {
        CommentScored commentScored = null;
        switch (t.getCommentType()) {
            case ALTERNATIVE_PRODUCTS:
                commentScored = new AlternativeProductsCommentScored(
                        (AlternativeProductsComment) t, evidenceTypes);
                break;
            case BIOPHYSICOCHEMICAL_PROPERTIES:
                commentScored = new BioPhysicoChemicalPropertiesCommentScored(
                        (BPCPComment) t, evidenceTypes);
                break;
            case CAUTION:
                commentScored = new CautionCommentScored((FreeTextComment) t, evidenceTypes);
                break;
            case COFACTOR:
                commentScored = new CofactorCommentScored(
                        (CofactorComment) t, evidenceTypes);
                break;
            case DISEASE:
                commentScored = new DiseaseCommentScored(
                        (DiseaseComment) t, evidenceTypes);
                break;
            case INTERACTION:
                commentScored = new InteractionCommentScored((InteractionComment) t, evidenceTypes);
                break;
            case MASS_SPECTROMETRY:
                commentScored = new MassSpectrometryCommentScored(
                        (MassSpectrometryComment) t, evidenceTypes);
                break;
            case MISCELLANEOUS:
                commentScored = new MiscellaneousCommentScored(
                        (FreeTextComment) t, evidenceTypes);
                break;
            case RNA_EDITING:
                commentScored = new RnaEditingCommentScored((RnaEditingComment) t, evidenceTypes);
                break;
            case SEQUENCE_CAUTION:
                commentScored = new SequenceCautionCommentScored(
                        (SequenceCautionComment) t, evidenceTypes);
                break;
            case SIMILARITY:
                commentScored = new SimilarityCommentScored((FreeTextComment) t, evidenceTypes);
                break;
            case SUBCELLULAR_LOCATION:
                commentScored = new SubcellularLocationCommentScored(
                        (SubcellularLocationComment) t, evidenceTypes);
                break;
            case WEBRESOURCE:
                commentScored = new WebResourceCommentScored((WebResourceComment) t, evidenceTypes);
                break;
            case CATALYTIC_ACTIVITY:
                commentScored = new CatalyticActivityCommentScored((CatalyticActivityComment) t, evidenceTypes);
                break;
            default:
                commentScored = new DefaultTextOnlyCommentScored(
                        (FreeTextComment) t, evidenceTypes);
                break;
        }
        return commentScored;
    }
}
