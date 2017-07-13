package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.api.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AllergenComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.BioPhysicoChemicalPropertiesComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.AllergenCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.BioPhysicoChemicalPropertiesCommentImpl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommentsTest {
    @Test
    public void testComments() {
        Comments comments = new Comments();
        List<Comment> colComments = new ArrayList<>();
        AllergenComment alc = new AllergenCommentImpl();
        colComments.add(alc);

        BioPhysicoChemicalPropertiesComment bcpc = new BioPhysicoChemicalPropertiesCommentImpl();
        colComments.add(bcpc);
        comments.setComments(colComments);

        assertEquals(comments.getAllergenComments().size(), 1);
        assertEquals(comments.getBioPhysicoChemicalPropertiesComments().size(), 1);
        assertEquals(comments.getAllComments().size(), 2);

    }
}
