package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/**
 * @author jluo
 * @date: 18 Mar 2020
 */
class InteractorImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Interactant obj = new InteractantImpl();
        assertNotNull(obj);
    }

    @Test
    void completeObjHasMethods() {
        Interactant obj =
                new InteractantImpl(
                        new UniProtKBAccessionBuilder("P12345").build(),
                        "gene1",
                        "P_1234",
                        "EBI-1223708");

        assertTrue(obj.hasChainId());
        assertTrue(obj.hasGeneName());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Interactant obj =
                new InteractantImpl(
                        new UniProtKBAccessionBuilder("P12345").build(),
                        "gene1",
                        "P_1234",
                        "EBI-1223708");
        Interactant objBuilderFrom = InteractantBuilder.from(obj).build();
        assertEquals(obj, objBuilderFrom);

        assertEquals(obj.hashCode(), objBuilderFrom.hashCode());
    }
}
