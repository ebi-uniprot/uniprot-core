package org.uniprot.core.uniref.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefMemberIdType;

import java.util.Collections;

class RepresentativeMemberImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        RepresentativeMember obj = new RepresentativeMemberImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        RepresentativeMember impl =
                new RepresentativeMemberImpl(
                        UniRefMemberIdType.UNIPARC,
                        "memId",
                        "orgName",
                        56L,
                        56,
                        "proName",
                        Collections.emptyList(),
                        new UniRefEntryIdImpl("50"),
                        new UniRefEntryIdImpl("90"),
                        new UniRefEntryIdImpl("100"),
                        new UniParcIdBuilder("id").build(),
                        null,
                        false,
                        null);
        RepresentativeMember obj = RepresentativeMemberBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
