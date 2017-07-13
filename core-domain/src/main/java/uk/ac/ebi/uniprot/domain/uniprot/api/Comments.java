package uk.ac.ebi.uniprot.domain.uniprot.api;

import uk.ac.ebi.uniprot.domain.uniprot.comments.AllergenComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.BioPhysicoChemicalPropertiesComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;

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

    private <T extends Comment> List<T> getComments(Class<?> c) {
        return comments.stream()
                .filter(comment -> c.isInstance(comment))
                .map(comment -> (T) comment)
                .collect(Collectors.toList());
    }

}
