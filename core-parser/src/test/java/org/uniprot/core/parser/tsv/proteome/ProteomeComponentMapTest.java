package org.uniprot.core.parser.tsv.proteome;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ProteomeEntry;
import org.uniprot.core.proteome.impl.ComponentBuilder;
import org.uniprot.core.proteome.impl.ProteomeEntryBuilder;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class ProteomeComponentMapTest {

    @Test
    void testAttributeValues() {
        List<Component> components = new ArrayList<>();
        Component component1 =
                new ComponentBuilder()
                        .name("someName1")
                        .description("some description")
                        .proteinCount(102)
                        .build();

        Component component2 =
                new ComponentBuilder()
                        .name("someName2")
                        .description("some description 2")
                        .proteinCount(102)
                        .build();

        components.add(component1);
        components.add(component2);
        ProteomeEntry proteome = new ProteomeEntryBuilder().componentsSet(components).build();
        assertEquals(2, proteome.getComponents().size());
        assertThat(proteome.getComponents(), hasItem(component1));

        ProteomeComponentMap componentMap = new ProteomeComponentMap(components);
        Map<String, String> result = componentMap.attributeValues();
        assertEquals(1, result.size());
        assertEquals("someName1; someName2", result.get("components"));
    }

    @Test
    void testContains() {
        List<String> fields = Arrays.asList("upid", "organism");
        assertFalse(ProteomeComponentMap.contains(fields));
        fields = Arrays.asList("upid", "components");
        assertTrue(ProteomeComponentMap.contains(fields));
    }

    @Test
    void testFields() {
        List<String> fields = Arrays.asList("components");
        assertEquals(ProteomeComponentMap.FIELDS, fields);
    }
}
