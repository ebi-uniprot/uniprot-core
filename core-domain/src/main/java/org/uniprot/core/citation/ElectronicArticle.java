package org.uniprot.core.citation;

/**
 * The RL line for an electronic publication includes an '(er)' prefix. The format is indicated below:
 * <p>
 * RL   (er) Free text.
 * Examples:
 * <p>
 * RL   (er) Plant Gene Register PGR98-023.
 * RL   (er) Worm Breeder's Gazette 15(3):34(1998).
 */
public interface ElectronicArticle extends Citation {

    Locator getLocator();

    Journal getJournal();

    boolean hasLocator();

    boolean hasJournal();
}
