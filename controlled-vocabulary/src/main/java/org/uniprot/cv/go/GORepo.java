package org.uniprot.cv.go;

import java.util.Collection;
import java.util.Set;

import org.uniprot.core.cv.go.GeneOntologyEntry;

/** This class is responsible for providing access to GO terms and their ancestors. */
public interface GORepo {
    /**
     * Find the ancestors of the supplied GO term, accessed by the IS-A relationship
     *
     * @param goId ID of the GO term
     * @return the ancestors
     */
    Set<GeneOntologyEntry> getIsA(String goId);

    /**
     * Find the ancestors of the supplied GO term, accessed by the PART-OF relationship
     *
     * @param goId ID of the GO term
     * @return the ancestors
     */
    Set<GeneOntologyEntry> getPartOf(String goId);

    /**
     * Finds all ancestors of the given GO term, accessible via IS-A and PART-OF relationships
     *
     * @param goId ID of the GO term to fetch
     * @return all ancestors of the given GO term
     */
    Set<GeneOntologyEntry> getAncestors(String goId);

    /**
     * Finds all ancestors of the given GO term, accessible via IS-A and PART-OF relationships
     *
     * @param goId ID of the GO term to fetch
     * @param relationships the relationships over which to navigate (IS-A/PART-OF)
     * @return all ancestors of the given GO term
     */
    Set<GeneOntologyEntry> getAncestors(String goId, Collection<RelationshipType> relationships);

    /**
     * Retrieves the GO term for the associated GO id
     *
     * @param goId the GO id
     * @return the GO term
     */
    GeneOntologyEntry getById(String goId);
}
