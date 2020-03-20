package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.impl.InteractantBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractionBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;

class InteractionConverterTest {

    @Test
    void testBinary1() {
        InteractionBuilder builder = new InteractionBuilder();
        Interactant interactant1 =  createInteractant("PROC_12344","EBI-0001", null, null);     
        Interactant interactant2 =  createInteractant(null,"EBI-0001", "P12345-1", "gene1");
 
        Interaction interaction =
                builder.interactantOne(interactant1)
                .interactantTwo(interactant2)
                .numberOfExperiments(3)
                .isOrganismDiffer(false)
                        .build();
        InteractionConverter converter = new InteractionConverter();
        CommentType xmlComment = converter.toXml(interaction);
        Interaction converted = converter.fromXml(xmlComment);
        assertEquals(interaction, converted);
    }

    @Test
    void testBinary2() {
        InteractionBuilder builder = new InteractionBuilder();
        Interactant interactant1 =  createInteractant(null,"EBI-0001", "P12345", null);       
        Interactant interactant2 =  createInteractant("PROC_12344","EBI-0001", "P12346", "gene1");
 
        Interaction interaction =
                builder.interactantOne(interactant1)
                .interactantTwo(interactant2)
                .numberOfExperiments(3)
                .isOrganismDiffer(false)
                        .build();
        InteractionConverter converter = new InteractionConverter();
        CommentType xmlComment = converter.toXml(interaction);
        Interaction converted = converter.fromXml(xmlComment);
        assertEquals(interaction, converted);
    }

    
    private Interactant createInteractant(String chainId, String intActId, String accession, String geneName ) {
  	  InteractantBuilder builder = new InteractantBuilder();
  	  builder.chainId(chainId)
  	  .geneName(geneName)
  	  .intActId(intActId);
  	if(accession !=null)
  		builder.uniProtKBAccession(accession);
  	  return builder.build();
    }
}
