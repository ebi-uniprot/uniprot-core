package org.uniprot.core.publication;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.ReferenceCommentType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created 11/12/2020
 *
 * @author Edd
 */
public interface UniProtKBMappedReference extends MappedReference {
    Citation getCitation();

    List<ReferenceComment> getReferenceComments();

    List<String> getReferencePositions();

    int getReferenceNumber();

    default List<ReferenceComment> getReferenceCommentsByType(ReferenceCommentType type) {
        return getReferenceComments().stream()
                .filter(val -> val.getType() == type)
                .collect(Collectors.toList());
    }
}
