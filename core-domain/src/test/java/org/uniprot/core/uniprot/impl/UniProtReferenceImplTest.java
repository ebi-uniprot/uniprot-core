package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.builder.ElectronicArticleBuilder;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.ReferenceCommentType;
import org.uniprot.core.uniprot.UniProtReference;
import org.uniprot.core.uniprot.builder.ReferenceCommentBuilder;
import org.uniprot.core.uniprot.builder.UniProtReferenceBuilder;

class UniProtReferenceImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtReference obj = new UniProtReferenceImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtReferenceImpl impl =
                new UniProtReferenceImpl(
                        new ElectronicArticleBuilder().build(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        UniProtReference obj = UniProtReferenceBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void canGetReferenceWithType() {
        List<ReferenceComment> referenceComments =
                Arrays.asList(
                        new ReferenceCommentBuilder().type(ReferenceCommentType.PLASMID).build(),
                        new ReferenceCommentBuilder().type(ReferenceCommentType.STRAIN).build());
        UniProtReference reference =
                new UniProtReferenceBuilder().referenceCommentsSet(referenceComments).build();

        List<ReferenceComment> typedReferenceComments =
                reference.getReferenceCommentsByType(ReferenceCommentType.PLASMID);

        assertEquals(1, typedReferenceComments.size());
    }
}
