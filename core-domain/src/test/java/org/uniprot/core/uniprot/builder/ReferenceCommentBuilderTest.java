package org.uniprot.core.uniprot.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.ReferenceCommentType;

class ReferenceCommentBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        ReferenceComment obj = new ReferenceCommentBuilder().build();
        ReferenceCommentBuilder builder = new ReferenceCommentBuilder().from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        ReferenceComment obj = new ReferenceCommentBuilder().build();
        ReferenceComment obj2 = new ReferenceCommentBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void canSetTypeFromBuilder() {
        ReferenceComment referenceComment =
                new ReferenceCommentBuilder().type(ReferenceCommentType.PLASMID).build();
        assertEquals(ReferenceCommentType.PLASMID, referenceComment.getType());
    }
}
