package org.uniprot.core.uniparc.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.InterProGroup;

/**
 * @author jluo
 * @date: 23 May 2019
 */
class InterProGroupBuilderTest {

    @Test
    void testId() {
        InterProGroup domain = new InterProGroupBuilder().id("someId").build();
        assertEquals("someId", domain.getId());
    }

    @Test
    void testName() {
        InterProGroup domain = new InterProGroupBuilder().name("some name").build();
        assertEquals("some name", domain.getName());
    }

    @Test
    void testFrom() {
        InterProGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        InterProGroup domain2 = InterProGroupBuilder.from(domain).build();
        assertEquals(domain, domain2);
        InterProGroup domain3 = InterProGroupBuilder.from(domain).name("name2").build();
        assertEquals("id1", domain3.getId());
        assertEquals("name2", domain3.getName());
    }
}
