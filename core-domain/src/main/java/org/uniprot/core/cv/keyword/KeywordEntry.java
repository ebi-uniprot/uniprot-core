package org.uniprot.core.cv.keyword;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.uniprot.core.Statistics;

/**
 * Graphical representation of keywords according to old uniprot website Reverse of flat text file
 *
 * <p>e-g flat file hierarchy start with category and end on actual keyword. NOTE: This is opposite.
 * Category will be child of keyword
 */
public interface KeywordEntry extends Serializable {
    KeywordEntryKeyword getKeyword();

    String getDefinition();

    List<String> getSynonyms();

    List<KeywordGeneOntology> getGeneOntologies();

    /**
     * Keyword's immediate children e-g KW-0411 will have one parent KW-0001 Category will always
     * have at least one parent
     *
     * @return list of keywords which will always keywords (never a category)
     */
    Set<KeywordEntry> getParents();

    List<String> getSites();

    KeywordEntryKeyword getCategory();

    /**
     * Keyword's immediate children e-g KW-0411 will have two children (KW-0479 and KW-0408)
     * Category will have no children
     *
     * @return list of keywords which can also be category
     */
    List<KeywordEntry> getChildren();

    String getAccession();

    Statistics getStatistics();
}
