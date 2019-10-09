package org.uniprot.core.uniref.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.RepresentativeMember;

class RepresentativeMemberImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        RepresentativeMember obj = new RepresentativeMemberImpl();
        assertNotNull(obj);
    }
}
