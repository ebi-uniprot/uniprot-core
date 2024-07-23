package org.uniprot.core.xml.uniprot.comment;

import java.io.Serializable;

/**
 * @author jluo
 * @date: 19-Nov-2020 Create This map is used by SLCCommentConverter, use enum is not ideal.
 *     However, CommentConverter is called by entry converter, each time, when we need to convert an
 *     entry, we create entry converter. We don't want to the map each time when we create a
 *     converter. Maybe there is a better solution.
 */
public interface SubcellLocationNameMap extends Serializable {

    String getLocationName(String name);
    String getName(String name);
}
