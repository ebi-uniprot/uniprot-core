package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.comment.impl.InteractantBuilder;
import org.uniprot.core.xml.jaxb.uniprot.InteractantType;

/**
 * @author jluo
 * @date: 19 Mar 2020
 */
class InteractantConverterTest {
  private InteractantConverter converter = new InteractantConverter();

  @Test
  void onlyChainId() {
	  Interactant interactant = createInteractant("PROC_12344","EBI-0001", null, null);
	  InteractantType interactantType = converter.toXml(interactant);
	  verifyInteractType(interactantType, "PROC_12344","EBI-0001", null, null);
	  Interactant converted = converter.fromXml(interactantType);
	  assertEquals(interactant, converted);
  }
  @Test
  void onlyAccession() {
	  Interactant interactant = createInteractant(null,"EBI-0001", "P12345", null);
	  InteractantType interactantType = converter.toXml(interactant);
	  verifyInteractType(interactantType, "P12345","EBI-0001", null, null);
	  Interactant converted = converter.fromXml(interactantType);
	  assertEquals(interactant, converted);
  }
  
  @Test
  void onlyAccessionGene() {
	  Interactant interactant = createInteractant(null,"EBI-0001", "P12345-1", "gene1");
	  InteractantType interactantType = converter.toXml(interactant);
	  verifyInteractType(interactantType, "P12345-1","EBI-0001", null, "gene1");
	  Interactant converted = converter.fromXml(interactantType);
	  assertEquals(interactant, converted);
  }
  @Test
  void chainAccessionGene() {
	  Interactant interactant = createInteractant("PROC_12344","EBI-0001", "P12345", "gene1");
	  InteractantType interactantType = converter.toXml(interactant);
	  verifyInteractType(interactantType, "PROC_12344", "EBI-0001",  "P12345", "gene1");
	  Interactant converted = converter.fromXml(interactantType);
	  assertEquals(interactant, converted);
  }
  private void verifyInteractType(InteractantType interactantType, String id, String intActId, String xrefId, String label) {
	  assertEquals(id, interactantType.getId());
	  assertEquals(intActId, interactantType.getIntactId());
	  if(xrefId !=null) {
		  assertNotNull(interactantType.getDbReference());
		  assertEquals(xrefId, interactantType.getDbReference().getId());
	  }else {
		  assertNull(interactantType.getDbReference());
	  }
	  assertEquals(label, interactantType.getLabel());
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
