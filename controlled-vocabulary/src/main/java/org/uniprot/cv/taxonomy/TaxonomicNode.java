package org.uniprot.cv.taxonomy;

/** Represents the data found within a Taxonomic node */
public interface TaxonomicNode {
    int id();

    String scientificName();

    String commonName();

    String synonymName();

    String mnemonic();

    boolean hidden();

    /**
     * Returns a taxonomic node representing the parent of the current node
     *
     * @return a Taxonomic node representing the parent of this one, or null if the node has no
     *     parent
     */
    TaxonomicNode parent();

    /**
     * Indicates whether the current node has a parent or not.
     *
     * <p>It is recommended that this method be used to check for the presence of a parent before
     * calling the {@link #parent()} method, seeing a no parent will return null
     *
     * @return true if the node has a parent false otherwise
     */
    boolean hasParent();
}
