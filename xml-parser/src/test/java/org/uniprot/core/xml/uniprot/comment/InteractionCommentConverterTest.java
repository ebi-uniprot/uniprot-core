package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.uniprotkb.comment.impl.InteractantBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractionBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractionCommentBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;

import java.util.ArrayList;
import java.util.List;

class InteractionCommentConverterTest {

    @Test
    void test() {
        InteractionBuilder builder = new InteractionBuilder();
        Interactant interactant1 = createInteractant("PROC_12344", "EBI-0001", null, null);
        Interactant interactant2 = createInteractant(null, "EBI-0001", "P12345-1", "gene1");

        Interaction interaction1 =
                builder.interactantOne(interactant1)
                        .interactantTwo(interactant2)
                        .numberOfExperiments(3)
                        .isOrganismDiffer(false)
                        .build();

        InteractionBuilder builder2 = new InteractionBuilder();
        Interactant interactant21 = createInteractant(null, "EBI-0001", "P12345", null);
        Interactant interactant22 = createInteractant("PROC_12344", "EBI-0001", "P12346", "gene1");

        Interaction interaction2 =
                builder2.interactantOne(interactant21)
                        .interactantTwo(interactant22)
                        .numberOfExperiments(3)
                        .isOrganismDiffer(false)
                        .build();
        List<Interaction> interactions = new ArrayList<>();
        interactions.add(interaction1);
        interactions.add(interaction2);
        InteractionCommentBuilder commentBuilder = new InteractionCommentBuilder();
        InteractionComment comment = commentBuilder.interactionsSet(interactions).build();

        InteractionCommentConverter converter = new InteractionCommentConverter();
        List<CommentType> xmlComments = converter.toXml(comment);
        InteractionComment converted = converter.fromXml(xmlComments);
        assertEquals(comment, converted);
    }

    private Interactant createInteractant(
            String chainId, String intActId, String accession, String geneName) {
        InteractantBuilder builder = new InteractantBuilder();
        builder.chainId(chainId).geneName(geneName).intActId(intActId);
        if (accession != null) builder.uniProtKBAccession(accession);
        return builder.build();
    }
}
