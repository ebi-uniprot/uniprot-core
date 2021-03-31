package org.uniprot.core.publication.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.UniProtKBMappedReference;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.ReferenceCommentType;
import org.uniprot.core.uniprotkb.impl.ReferenceCommentBuilder;

/**
 * Other tests covered by {@link AbstractMappedReferenceBuilderTest}.
 *
 * <p>Created 08/12/2020
 *
 * @author Edd
 */
class UniProtKBMappedReferenceBuilderTest {
    @Test
    void checkEmptyReference() {
        UniProtKBMappedReference reference = new UniProtKBMappedReferenceBuilder().build();

        assertThat(reference.getReferenceComments(), is(empty()));
        assertThat(reference.getReferencePositions(), is(empty()));
    }

    @Test
    void canAddReferenceComment() {
        ReferenceComment comment =
                new ReferenceCommentBuilder().type(ReferenceCommentType.PLASMID).build();
        UniProtKBMappedReference reference =
                new UniProtKBMappedReferenceBuilder().referenceCommentsAdd(comment).build();

        assertThat(reference.getReferenceComments(), hasSize(1));
        assertThat(reference.getReferenceCommentsByType(ReferenceCommentType.PLASMID), hasSize(1));
    }

    @Test
    void canSetReferenceComments() {
        List<ReferenceComment> comments = new ArrayList<>();
        comments.add(new ReferenceCommentBuilder().type(ReferenceCommentType.PLASMID).build());
        comments.add(new ReferenceCommentBuilder().type(ReferenceCommentType.STRAIN).build());

        UniProtKBMappedReference reference =
                new UniProtKBMappedReferenceBuilder().referenceCommentsSet(comments).build();

        assertThat(reference.getReferenceComments(), hasSize(2));
        assertThat(reference.getReferenceCommentsByType(ReferenceCommentType.PLASMID), hasSize(1));
        assertThat(reference.getReferenceCommentsByType(ReferenceCommentType.STRAIN), hasSize(1));
    }

    @Test
    void canAddReferencePosition() {
        String position = "1";
        UniProtKBMappedReference reference =
                new UniProtKBMappedReferenceBuilder().referencePositionsAdd(position).build();

        assertThat(reference.getReferencePositions(), hasSize(1));
        assertThat(reference.getReferencePositions().get(0), is(position));
    }

    @Test
    void canSetReferencePositions() {
        List<String> positions = new ArrayList<>();
        String position1 = "1";
        positions.add(position1);
        String position2 = "2";
        positions.add(position2);

        UniProtKBMappedReference reference =
                new UniProtKBMappedReferenceBuilder().referencePositionsSet(positions).build();

        assertThat(reference.getReferencePositions(), contains(position1, position2));
    }

    @Test
    void canSetReferenceNumber() {
        int refNumber = 100;
        UniProtKBMappedReference reference =
                new UniProtKBMappedReferenceBuilder().referenceNumber(refNumber).build();
        assertThat(reference.getReferenceNumber(), is(refNumber));
    }

    @Test
    void canCreateViaFrom() {
        ReferenceComment comment =
                new ReferenceCommentBuilder().type(ReferenceCommentType.PLASMID).build();
        UniProtKBMappedReference reference =
                new UniProtKBMappedReferenceBuilder().referenceCommentsAdd(comment).build();

        UniProtKBMappedReferenceBuilder builder = UniProtKBMappedReferenceBuilder.from(reference);

        assertThat(
                builder.build().getReferenceComments().get(0).getType(),
                is(ReferenceCommentType.PLASMID));
    }
}
