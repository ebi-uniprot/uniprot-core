package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.GeneNameType;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.impl.UniProtAccessionBuilder;

class ProteinImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Protein obj = new ProteinImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Protein impl =
                new ProteinImpl(
                        new UniProtAccessionBuilder("acc").build(),
                        UniProtEntryType.INACTIVE,
                        30,
                        "gm",
                        GeneNameType.GENE_NAME);
        Protein obj = ProteinBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
