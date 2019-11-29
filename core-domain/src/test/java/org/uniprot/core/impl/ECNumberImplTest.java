package org.uniprot.core.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ECNumber;

import static org.junit.jupiter.api.Assertions.*;

class ECNumberImplTest {
    @Test
    void testIsValid() {
        String ec = "4.6.1.2";
        verify(ec, true);
        verify("4.1.99.-", true);
        verify("1.14.14.115", true);

        verify("2.3.-.-", true);
        verify("3.1.13.-", true);
        verify("4.2.2.n2", true);
        verify("4n.2.2.n2", false);
        verify("4.2.2", false);
    }

    private void verify(String ec, boolean valid) {
        ECNumberImpl ecnumber = new ECNumberImpl(ec);
        assertEquals(valid, ecnumber.isValid());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ECNumber obj = new ECNumberImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        ECNumber impl = new ECNumberImpl("ec");
        ECNumber obj = new ECNumberImpl("ec");

        assertTrue(impl.hasValue());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalNullObject() {
        ECNumber impl = new ECNumberImpl(null);
        ECNumber obj = new ECNumberImpl(null);
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
