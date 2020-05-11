package org.uniprot.core.util.property;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class PropertyTest {
    Property property;

    @BeforeEach
    void setUp() {
        String jsonArray = "[{key:value,arr:[1,2,3]}]";
        List<Property> properties = Property.parseJsonArray(jsonArray);
        property = properties.get(0);
    }

    @Test
    void getString() {
        String valueFirstJsonObject = property.getString("key");
        assertEquals("value", valueFirstJsonObject);
    }

    @Test
    void onlyArrayOfJsonWillBeParsed_SimpleArraysWillBeIgnore() {
        List<Property> arr = property.getProperties("arr");
        assertTrue(arr.isEmpty());
    }

    @Test
    void arrayOfJsonWithInArrayOfJson_willBeParsed() {
        String jsonArray = "[{key:value,arr:[{a:1},{b:2},{c:\"3\"}]}]";
        List<Property> properties = Property.parseJsonArray(jsonArray);
        property = properties.get(0);
        List<Property> arr = property.getProperties("arr");
        assertFalse(arr.isEmpty());
        assertEquals(3, arr.size());
        assertEquals("3", arr.get(2).getString("c"));
        assertTrue(arr.get(2).getProperties("c").isEmpty());
    }

    @Test
    void optString() {
        String defaultString = "key not found";
        String valueFirstJsonObject = property.optString("notAKey", defaultString);
        assertEquals(defaultString, valueFirstJsonObject);
    }
}
