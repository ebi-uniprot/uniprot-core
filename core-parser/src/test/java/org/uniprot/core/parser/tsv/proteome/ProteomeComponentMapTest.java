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
import org.uniprot.core.proteome.builder.ComponentBuilder;
import org.uniprot.core.proteome.builder.ProteomeEntryBuilder;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class ProteomeComponentMapTest {

    @Test
    void testAttributeValues() {
        List<Component> components = new ArrayList<>();
        Component component1 =
                ComponentBuilder.newInstance()
                        .name("someName1")
                        .description("some description")
                        .proteinCount(102)
                        .build();

        Component component2 =
                ComponentBuilder.newInstance()
                        .name("someName2")
                        .description("some description 2")
                        .proteinCount(102)
                        .build();

        components.add(component1);
        components.add(component2);
        ProteomeEntry proteome = ProteomeEntryBuilder.newInstance().components(components).build();
        assertEquals(2, proteome.getComponents().size());
        assertThat(proteome.getComponents(), hasItem(component1));

        ProteomeComponentMap componentMap = new ProteomeComponentMap(components);
        Map<String, String> result = componentMap.attributeValues();
        assertEquals(1, result.size());
        assertEquals("someName1; someName2", result.get("proteome_components"));
    }

    @Test
    void testContains() {
        List<String> fields = Arrays.asList("upid", "organism");
        assertFalse(ProteomeComponentMap.contains(fields));
        fields = Arrays.asList("upid", "proteome_components");
        assertTrue(ProteomeComponentMap.contains(fields));
    }

    @Test
    void testFields() {
        List<String> fields = Arrays.asList("proteome_components");
        assertEquals(ProteomeComponentMap.FIELDS, fields);
    }
}
