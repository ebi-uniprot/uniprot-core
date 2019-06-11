package uk.ac.ebi.uniprot.domain.proteome.builder;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.proteome.Component;
import uk.ac.ebi.uniprot.domain.proteome.ComponentType;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeXReferenceType;

class ComponentBuilderTest {

	@Test
	void testNameAndDescriptionAndProteinCount() {
		Component component = ComponentBuilder.newInstance()
				.name("someName").description("some description")
				.proteinCount(102)
				.build();
		assertEquals("someName", component.getName());
		assertEquals("some description", component.getDescription());
		assertEquals(102, component.getProteinCount());
	}
	@Test
	void testNameAndDescriptionAndProteinCountAndType() {
		Component component = ComponentBuilder.newInstance()
				.name("someName").description("some description")
				.proteinCount(102)
				.type(ComponentType.PRIMARY)
				.build();
		assertEquals("someName", component.getName());
		assertEquals("some description", component.getDescription());
		assertEquals(102, component.getProteinCount());
		assertEquals(ComponentType.PRIMARY, component.getType());
	}
	
	@Test
	void testAddXref() {
		DBCrossReference<ProteomeXReferenceType> xref1 =
				new DBCrossReferenceBuilder<ProteomeXReferenceType>()
				.databaseType(ProteomeXReferenceType.GENOME_ACCESSION)
				.id("ACA121")
				.build();
		DBCrossReference<ProteomeXReferenceType> xref2 =
				new DBCrossReferenceBuilder<ProteomeXReferenceType>()
				.databaseType(ProteomeXReferenceType.GENOME_ANNOTATION)
				.id("ADFDA121")
				.build();
		Component component =
		 ComponentBuilder.newInstance().addDbXReference(xref1)
		 .addDbXReference(xref2)
		 .build();
		assertEquals(2, component.getDbXReferences().size());
		assertThat(component.getDbXReferences(), hasItem(xref1));
		
				
	}
	@Test
	void testXrefs() {
		List<DBCrossReference<ProteomeXReferenceType>> xrefs =new ArrayList<>();
		DBCrossReference<ProteomeXReferenceType> xref1 =
				new DBCrossReferenceBuilder<ProteomeXReferenceType>()
				.databaseType(ProteomeXReferenceType.GENOME_ACCESSION)
				.id("ACA121")
				.build();
		DBCrossReference<ProteomeXReferenceType> xref2 =
				new DBCrossReferenceBuilder<ProteomeXReferenceType>()
				.databaseType(ProteomeXReferenceType.GENOME_ANNOTATION)
				.id("ADFDA121")
				.build();
		xrefs.add(xref1);
		xrefs.add(xref2);
		Component component =
		 ComponentBuilder.newInstance().dbXReferences(xrefs)
		 .build();
		assertEquals(2, component.getDbXReferences().size());
		assertThat(component.getDbXReferences(), hasItem(xref2));
		
				
	}
	
}
