package org.uniprot.core.cv.keyword;

import java.io.Serializable;

public interface KeywordId extends Serializable {
    /**
     * Unique can be use as an identifier
     * @return Unique name of keyword e-g Iron
     */
    String getName();

    /**
     * Uniprot accession as an id to identify keyword uniquely
     * @return Uniprot accession e-g KW-0408
     */
    String getId();
}
