package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;


import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AllergenComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.BiotechnologyComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DevelopmentalStageComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DisruptionPhenotypeComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DomainComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.EnzymeRegulationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.FunctionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.InductionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MiscellaneousComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PathwayComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PharmaceuticalComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PolymorphismComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PtmComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SimilarityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SubunitComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.TissueSpecificityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.ToxicDoseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.FreeTextCommentImpl;

import java.util.List;

public  class FreeTextCommentBuilder<T extends FreeTextComment> implements CommentBuilder<T> {
    
    public static FreeTextCommentBuilder<? extends FreeTextComment> newInstance(){
        return new FreeTextCommentBuilder<>();
    }
    private CommentType commentType;
    private List<EvidencedValue> texts;
    @Override
    public T build() {
        return buildFreeTextComment(commentType, texts);
    }
    
    public FreeTextCommentBuilder<T> commentType(CommentType commentType){
        this.commentType  = commentType;
        return this;
    }
    public FreeTextCommentBuilder<T> texts(List<EvidencedValue> texts){
        this.texts  = texts;
        return this;
    }
    
    public static <S extends FreeTextComment> S buildFreeTextComment(CommentType commentType,
            List<EvidencedValue> texts){
        FreeTextComment comment;
    
        switch (commentType) {
            case ALLERGEN:
                comment= new AllergenCommentImpl(texts);
                break;
            case BIOTECHNOLOGY:
                comment= new BiotechnologyCommentImpl(texts);
                break;
            case CATALYTIC_ACTIVITY:
                comment= new CatalyticActivityCommentImpl(texts);
                break;
            case CAUTION:
                comment= new CautionCommentImpl(texts);
                break;
            case DEVELOPMENTAL_STAGE:
                comment= new DevelopmentalStageCommentImpl(texts);
                break;
            case DISRUPTION_PHENOTYPE:
                comment= new DisruptionPhenotypeCommentImpl(texts);
                break;
            case DOMAIN:
                comment= new DomainCommentImpl(texts);
                break;
            case ENZYME_REGULATION:
                comment= new EnzymeRegulationCommentImpl(texts);
                break;
            case FUNCTION:
                comment= new FunctionCommentImpl(texts);
                break;
            case INDUCTION:
                comment= new InductionCommentImpl(texts);
                break;
            case MISCELLANEOUS:
                comment= new MiscellaneousCommentImpl(texts);
                break;
            case PATHWAY:
                comment= new PathwayCommentImpl(texts);
                break;              
            case PHARMACEUTICAL:
                comment= new PharmaceuticalCommentImpl(texts);
                break;
            case POLYMORPHISM:
                comment= new PolymorphismCommentImpl(texts);
                break;
            case PTM:
                comment= new PtmCommentImpl(texts);
                break;
            case SIMILARITY:
                comment= new SimilarityCommentImpl(texts);
                break;
            case SUBUNIT:
                comment= new SubunitCommentImpl(texts);
                break;
            case TISSUE_SPECIFICITY:
                comment= new TissueSpecificityCommentImpl(texts);
                break;

            case TOXIC_DOSE:
                comment= new ToxicDoseCommentImpl(texts);
                break;

            default:
                throw new IllegalArgumentException(commentType + " is not free text comment");
        }
        return (S) comment;
                
    }
   static class AllergenCommentImpl extends FreeTextCommentImpl implements AllergenComment {

        public AllergenCommentImpl( List<EvidencedValue> texts) {
            super(CommentType.ALLERGEN, texts);
        }

    }
   static class BiotechnologyCommentImpl extends FreeTextCommentImpl implements BiotechnologyComment {
       public BiotechnologyCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.BIOTECHNOLOGY, texts);
       }

   }
   static class CatalyticActivityCommentImpl extends FreeTextCommentImpl implements CatalyticActivityComment {
       public CatalyticActivityCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.CATALYTIC_ACTIVITY, texts);
       }
   }
   static class CautionCommentImpl extends FreeTextCommentImpl implements CautionComment {

       public CautionCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.CAUTION, texts);
       }
   }
   static class DevelopmentalStageCommentImpl extends FreeTextCommentImpl implements DevelopmentalStageComment {

       public DevelopmentalStageCommentImpl(List<EvidencedValue> texts) {
           super(CommentType.DEVELOPMENTAL_STAGE, texts);
       }
   }
   static class DisruptionPhenotypeCommentImpl extends FreeTextCommentImpl implements DisruptionPhenotypeComment {
       public DisruptionPhenotypeCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.DISRUPTION_PHENOTYPE, texts);
       }
   }
   static class DomainCommentImpl extends FreeTextCommentImpl implements DomainComment {

       public DomainCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.DOMAIN, texts);
       }
   }
   static class EnzymeRegulationCommentImpl extends FreeTextCommentImpl implements EnzymeRegulationComment {

       public EnzymeRegulationCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.ENZYME_REGULATION, texts);
       }
   }
   static class FunctionCommentImpl extends FreeTextCommentImpl implements FunctionComment {

       public FunctionCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.FUNCTION, texts);
       }
   }
   static class InductionCommentImpl extends FreeTextCommentImpl implements InductionComment {

       public InductionCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.INDUCTION, texts);
       }
   }
   static class MiscellaneousCommentImpl extends FreeTextCommentImpl implements MiscellaneousComment {

       public MiscellaneousCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.MISCELLANEOUS, texts);
       }

   }
   static class PathwayCommentImpl extends FreeTextCommentImpl implements PathwayComment {

       public PathwayCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.PATHWAY, texts);
       }
   }
   static class PharmaceuticalCommentImpl extends FreeTextCommentImpl implements PharmaceuticalComment {

       public PharmaceuticalCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.PHARMACEUTICAL, texts);
       }
   }
   static class PolymorphismCommentImpl extends FreeTextCommentImpl implements PolymorphismComment {

       public PolymorphismCommentImpl(List<EvidencedValue> texts) {
           super(CommentType.POLYMORPHISM, texts);
       }
   }
   static class PtmCommentImpl extends FreeTextCommentImpl implements PtmComment {

       public PtmCommentImpl(List<EvidencedValue> texts) {
           super(CommentType.PTM, texts);
       }
   }
   static class SimilarityCommentImpl extends FreeTextCommentImpl implements SimilarityComment {

       public SimilarityCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.SIMILARITY, texts);
       }
   }
   static class SubunitCommentImpl extends FreeTextCommentImpl implements SubunitComment {

       public SubunitCommentImpl( List<EvidencedValue> texts) {
           super(CommentType.SUBUNIT, texts);
       }
   }
   static class TissueSpecificityCommentImpl extends FreeTextCommentImpl implements TissueSpecificityComment {

       public TissueSpecificityCommentImpl(List<EvidencedValue> texts) {
           super(CommentType.TISSUE_SPECIFICITY, texts);
       }
   }
   static class ToxicDoseCommentImpl extends FreeTextCommentImpl implements ToxicDoseComment {

       public ToxicDoseCommentImpl(List<EvidencedValue> texts) {
           super(CommentType.TOXIC_DOSE, texts);
       }
   }

}
