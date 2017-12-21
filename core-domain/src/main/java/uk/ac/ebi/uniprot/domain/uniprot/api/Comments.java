package uk.ac.ebi.uniprot.domain.uniprot.api;

import uk.ac.ebi.uniprot.domain.uniprot.comments.AllergenComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.BioPhysicoChemicalPropertiesComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.BiotechnologyComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DevelopmentalStageComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DisruptionPhenotypeComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DomainComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.EnzymeRegulationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.FunctionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.InductionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MiscellaneousComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PathwayComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PharmaceuticalComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PolymorphismComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PtmComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SimilarityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SubunitComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.TissueSpecificityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.ToxicDoseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.WebResourceComment;

import java.util.List;
import java.util.stream.Collectors;

public class Comments {

    private List<Comment> comments;

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getAllComments() {
        return comments;
    }

    public List<AllergenComment> getAllergenComments() {
        return getComments(AllergenComment.class);
    }

    public List<AlternativeProductsComment> getAlternativeProductsComments() {
        return getComments(AlternativeProductsComment.class);
    }

    public List<BioPhysicoChemicalPropertiesComment> getBioPhysicoChemicalPropertiesComments() {
        return getComments(BioPhysicoChemicalPropertiesComment.class);
    }

    public List<BiotechnologyComment> getBiotechnologyComments() {
        return getComments(BiotechnologyComment.class);
    }

    public List<CatalyticActivityComment> getCatalyticActivityComments() {
        return getComments(CatalyticActivityComment.class);
    }

    public List<CautionComment> getCautionComments() {
        return getComments(CautionComment.class);
    }

    public List<CofactorComment> getCofactorComments() {
        return getComments(CofactorComment.class);
    }

    public List<DevelopmentalStageComment> getDevelopmentalStageComments() {
        return getComments(DevelopmentalStageComment.class);
    }

    public List<DiseaseComment> getDiseaseComments() {
        return getComments(DiseaseComment.class);
    }

    public List<DisruptionPhenotypeComment> getDisruptionPhenotypeComments() {
        return getComments(DisruptionPhenotypeComment.class);
    }

    public List<DomainComment> getDomainComments() {
        return getComments(DisruptionPhenotypeComment.class);
    }

    public List<EnzymeRegulationComment> getEnzymeRegulationComments() {
        return getComments(EnzymeRegulationComment.class);
    }

    public List<FunctionComment> getFunctionComments() {
        return getComments(FunctionComment.class);
    }

    public List<InductionComment> getInductionComments() {
        return getComments(InductionComment.class);
    }

    public List<InteractionComment> getInteractionComments() {
        return getComments(InteractionComment.class);
    }

    public List<MassSpectrometryComment> getMassSpectrometryComments() {
        return getComments(MassSpectrometryComment.class);
    }

    public List<MiscellaneousComment> getMiscellaneousComments() {
        return getComments(MiscellaneousComment.class);
    }

    public List<PathwayComment> getPathwayComments() {
        return getComments(PathwayComment.class);
    }

    public List<PharmaceuticalComment> getPharmaceuticalComments() {
        return getComments(PharmaceuticalComment.class);
    }

    public List<PolymorphismComment> getPolymorphismComments() {
        return getComments(PolymorphismComment.class);
    }

    public List<PtmComment> getPtmComments() {
        return getComments(PtmComment.class);
    }

    public List<RnaEditingComment> getRnaEditingComments() {
        return getComments(RnaEditingComment.class);
    }

    public List<SequenceCautionComment> getSequenceCautionComments() {
        return getComments(SequenceCautionComment.class);
    }

    public List<SimilarityComment> getSimilarityComments() {
        return getComments(SimilarityComment.class);
    }

    public List<SubcellularLocationComment> getSubcellularLocationComments() {
        return getComments(SubcellularLocationComment.class);
    }

    public List<SubunitComment> getSubunitComments() {
        return getComments(SubunitComment.class);
    }

    public List<FreeTextComment> getTextOnlyComments() {
        return getComments(FreeTextComment.class);
    }

    public List<TissueSpecificityComment> getTissueSpecificityComments() {
        return getComments(TissueSpecificityComment.class);
    }

    public List<ToxicDoseComment> getToxicDoseComments() {
        return getComments(ToxicDoseComment.class);
    }

    public List<WebResourceComment> getWebResourceComments() {
        return getComments(WebResourceComment.class);
    }

    private <T extends Comment> List<T> getComments(Class<?> c) {
        return comments.stream()
                .filter(comment -> c.isInstance(comment))
                .map(comment -> (T) comment)
                .collect(Collectors.toList());
    }

    // need to be able to add comments
}
