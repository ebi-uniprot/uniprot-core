package org.uniprot.core.publication.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.UniProtKBMappedReference;

class UniProtKBMappedReferenceImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtKBMappedReference obj = new UniProtKBMappedReferenceImpl();
        assertNotNull(obj);
    }

    @Test
    void twoObjectsAreTheSame() {
        String pubMedId = "1";
        assertThat(
                new UniProtKBMappedReferenceBuilder().citationId(pubMedId).build(),
                is(new UniProtKBMappedReferenceBuilder().citationId(pubMedId).build()));
    }
}
