package org.uniprot.core.uniprot.taxonomy.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 2020-02-03
 */
class AbstractOrganismNameImplTest {

    @Test
    void defaultConstructor_jsonDeSerialization() {
        TestableAbstractOrganismNameImpl organism = new TestableAbstractOrganismNameImpl();
        assertNull(organism.getCommonName());
    }

    private static class TestableAbstractOrganismNameImpl extends AbstractOrganismNameImpl{

        private static final long serialVersionUID = 7726334016104975291L;
    }
}