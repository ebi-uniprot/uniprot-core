package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Interactor;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/**
 *
 * @author jluo
 * @date: 18 Mar 2020
 *
*/

class InteractorBuilderTest {

	 @Test
	    void testNewInstance() {
	        InteractorBuilder builder1 = new InteractorBuilder();
	        InteractorBuilder builder2 = new InteractorBuilder();
	        assertNotNull(builder1);
	        assertNotNull(builder2);
	        assertFalse(builder1 == builder2);
	    }
  @Test
  void testFrom(){
	  Interactor obj = new InteractorBuilder().build();
      InteractorBuilder builder = InteractorBuilder.from(obj);
      assertNotNull(builder);
  }

@Test
void testGeneName(){
	InteractorBuilder builder = new InteractorBuilder();
	Interactor obj = builder.geneName("gene1").build();
	assertEquals("gene1", obj.getGeneName());
	assertNull(obj.getUniProtkbAccession());
	assertNull(obj.getChainId());
	assertNull(obj.getIntActId());
}

@Test
void testUniProtAccessionUniProtKBAccession(){
	InteractorBuilder builder = new InteractorBuilder();
	Interactor obj = builder.uniProtAccession( new UniProtKBAccessionBuilder("P12345").build()).build();
	assertNull(obj.getGeneName());
	assertNotNull(obj.getUniProtkbAccession());
	assertEquals("P12345", obj.getUniProtkbAccession().getValue());
	assertNull(obj.getChainId());
	assertNull(obj.getIntActId());
}

@Test
void testUniProtAccessionString(){
	InteractorBuilder builder = new InteractorBuilder();
	Interactor obj = builder.uniProtAccession("P12345").build();
	assertNull(obj.getGeneName());
	assertNotNull(obj.getUniProtkbAccession());
	assertEquals("P12345", obj.getUniProtkbAccession().getValue());
	assertNull(obj.getChainId());
	assertNull(obj.getIntActId());
}

@Test
void testChainId(){
	InteractorBuilder builder = new InteractorBuilder();
	Interactor obj = builder.chainId("P_1234").build();
	assertEquals("P_1234", obj.getChainId());
	assertNull(obj.getUniProtkbAccession());
	assertNull(obj.getGeneName());
	assertNull(obj.getIntActId());
}

@Test
void testIntActId(){
	InteractorBuilder builder = new InteractorBuilder();
	Interactor obj = builder.intActId("EBI-1036653").build();
	assertEquals("EBI-1036653", obj.getIntActId());
	assertNull(obj.getUniProtkbAccession());
	assertNull(obj.getChainId());
	assertNull(obj.getGeneName());
}

}

