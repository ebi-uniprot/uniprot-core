package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.impl.ElectronicArticleBuilder;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.ReferenceCommentType;
import org.uniprot.core.uniprotkb.UniProtKBReference;

class UniProtKBReferenceImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtKBReference obj = new UniProtKBReferenceImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtKBReferenceImpl impl =
                new UniProtKBReferenceImpl(
                        new ElectronicArticleBuilder().build(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        UniProtKBReference obj = UniProtKBReferenceBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void canGetReferenceWithType() {
        List<ReferenceComment> referenceComments =
                Arrays.asList(
                        new ReferenceCommentBuilder().type(ReferenceCommentType.PLASMID).build(),
                        new ReferenceCommentBuilder().type(ReferenceCommentType.STRAIN).build());
        UniProtKBReference reference =
                new UniProtKBReferenceBuilder().referenceCommentsSet(referenceComments).build();

        List<ReferenceComment> typedReferenceComments =
                reference.getReferenceCommentsByType(ReferenceCommentType.PLASMID);

        assertEquals(1, typedReferenceComments.size());
    }
}
