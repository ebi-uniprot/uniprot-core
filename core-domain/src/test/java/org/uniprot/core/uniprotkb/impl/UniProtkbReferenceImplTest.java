package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.impl.ElectronicArticleBuilder;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.ReferenceCommentType;
import org.uniprot.core.uniprotkb.UniProtkbReference;

class UniProtkbReferenceImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtkbReference obj = new UniProtkbReferenceImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtkbReferenceImpl impl =
                new UniProtkbReferenceImpl(
                        new ElectronicArticleBuilder().build(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        UniProtkbReference obj = UniProtkbReferenceBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void canGetReferenceWithType() {
        List<ReferenceComment> referenceComments =
                Arrays.asList(
                        new ReferenceCommentBuilder().type(ReferenceCommentType.PLASMID).build(),
                        new ReferenceCommentBuilder().type(ReferenceCommentType.STRAIN).build());
        UniProtkbReference reference =
                new UniProtkbReferenceBuilder().referenceCommentsSet(referenceComments).build();

        List<ReferenceComment> typedReferenceComments =
                reference.getReferenceCommentsByType(ReferenceCommentType.PLASMID);

        assertEquals(1, typedReferenceComments.size());
    }
}
