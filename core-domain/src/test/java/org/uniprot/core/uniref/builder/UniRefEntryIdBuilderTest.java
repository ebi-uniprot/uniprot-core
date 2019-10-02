package org.uniprot.core.uniref.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntryId;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
class UniRefEntryIdBuilderTest {

    @Test
    void testUniRefEntryIdBuilderValid() {
        String id = "UniRef50_P03923";
        UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
        assertEquals(id, entryId.getValue());
        assertTrue(entryId.isValidId());

        id = "UniRef90_P03923";
        entryId = new UniRefEntryIdBuilder(id).build();
        assertEquals(id, entryId.getValue());
        assertTrue(entryId.isValidId());

        id = "UniRef100_P03923";
        entryId = new UniRefEntryIdBuilder(id).build();
        assertEquals(id, entryId.getValue());
        assertTrue(entryId.isValidId());
    }

    @Test
    void testUniRefEntryIdBuilderInvalid() {
        String id = "UniRef55_P03923";
        UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
        assertEquals(id, entryId.getValue());
        assertFalse(entryId.isValidId());

        id = "UniRef90_P0392";
        entryId = new UniRefEntryIdBuilder(id).build();
        assertEquals(id, entryId.getValue());
        assertFalse(entryId.isValidId());
    }

    @Test
    void twoDifferentObjects_defaultBuild_equal(){
        String id = "";
        UniRefEntryId ur1 = new UniRefEntryIdBuilder(id).build();
        UniRefEntryId ur2 = new UniRefEntryIdBuilder(id).build();
        assertTrue(ur1.equals(ur2) && ur2.equals(ur1));
        assertEquals(ur1.hashCode(), ur2.hashCode());
    }

    @Test
    void assignedIdCanBeGet(){
        String id = "id101";
        UniRefEntryId ur1 = new UniRefEntryIdBuilder(id).build();
        assertEquals(id, ur1.getValue());
    }

    @Test
    void assignedId_value_toString_allAreSame(){
        String id = "id101";
        UniRefEntryId ur1 = new UniRefEntryIdBuilder(id).build();
        assertEquals(id, ur1.getValue());
        assertEquals(id, ur1.toString());
    }

    @Test
    void assignedId_null_hasValueFalse(){
        UniRefEntryId ur1 = new UniRefEntryIdBuilder(null).build();
        assertFalse(ur1.hasValue());
    }

    @Test
    void assignedId_notNull_hasValueFalse(){
        UniRefEntryId ur1 = new UniRefEntryIdBuilder("id101").build();
        assertTrue(ur1.hasValue());
    }

    @Test
    void assignedId_nulls_areEqual(){
        UniRefEntryId ur1 = new UniRefEntryIdBuilder(null).build();
        UniRefEntryId ur2 = new UniRefEntryIdBuilder(null).build();
        assertEquals(ur1, ur2);
    }

    @Test
    void canCreateCopyFromBuilder_canSetValue(){
        UniRefEntryId ur1 = new UniRefEntryIdBuilder(null).build();
        UniRefEntryId nullObj = new UniRefEntryIdBuilder("101").from(ur1).build();
        assertNull(nullObj.getValue());
        assertNull(nullObj.toString());
    }
}
