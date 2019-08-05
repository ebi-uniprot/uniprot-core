package org.uniprot.core.uniparc.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.InterproGroup;
import org.uniprot.core.uniparc.builder.InterProGroupBuilder;

/**
 *
 * @author jluo
 * @date: 23 May 2019
 *
*/

class InterProGroupBuilderTest {

	@Test
	void testId() {
		InterproGroup domain = new InterProGroupBuilder().id("someId").build();
		assertEquals("someId", domain.getId());
	}

	@Test
	void testName() {
		InterproGroup domain = new InterProGroupBuilder().name("some name").build();
		assertEquals("some name", domain.getName());
	}

	@Test
	void testFrom() {
		InterproGroup domain = new InterProGroupBuilder().name("name1")
				.id("id1").build();
		InterproGroup domain2=	new InterProGroupBuilder().from(domain).build();
		assertEquals(domain, domain2);
		InterproGroup domain3=	new InterProGroupBuilder().from(domain).name("name2").build();
		assertEquals("id1", domain3.getId());
		assertEquals("name2", domain3.getName() );
	}

}

