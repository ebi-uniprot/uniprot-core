package org.uniprot.core.flatfile.tool.ca;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author jluo
 * @date: 05-Oct-2020
 */
class CatalyticActivityMappingExceptionTest {

    @Test
    void testCatalyticActivityMappingExceptionString() {
        String message = "test message";
        CatalyticActivityMappingException ex = new CatalyticActivityMappingException(message);
        assertEquals(message, ex.getMessage());
    }

    @Test
    void testCatalyticActivityMappingExceptionStringThrowable() {
        String message1 = "test1";
        String message2 = "test2";
        RuntimeException ee = new RuntimeException(message1);
        CatalyticActivityMappingException ex = new CatalyticActivityMappingException(message2, ee);
        assertEquals(message2, ex.getLocalizedMessage());
        assertEquals(ee, ex.getCause());
    }
}
