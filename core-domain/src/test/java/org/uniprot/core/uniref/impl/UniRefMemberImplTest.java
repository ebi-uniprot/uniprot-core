package org.uniprot.core.uniref.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefMember;

class UniRefMemberImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniRefMember obj = new UniRefMemberImpl();
        assertNotNull(obj);
    }
}
