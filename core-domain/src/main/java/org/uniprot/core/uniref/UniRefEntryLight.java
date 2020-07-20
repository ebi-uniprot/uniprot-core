package org.uniprot.core.uniref;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.uniprot.core.cv.go.GeneOntologyEntry;

/**
 * Represents a "light-weight" {@link UniRefEntry}, containing less detailed information when
 * compared to {@link UniRefEntry}, but enough to capture the main characteristics of a UniRef
 * entry.
 *
 * <p>Created 29/06/2020
 *
 * @author Edd
 */
public interface UniRefEntryLight extends Serializable {
    UniRefEntryId getId();

    String getName();

    LocalDate getUpdated();

    UniRefType getEntryType();

    long getCommonTaxonId();

    String getCommonTaxon();

    List<GeneOntologyEntry> getGoTerms();

    List<String> getMembers();

    int getMemberCount();

    LinkedHashSet<String> getOrganisms();

    LinkedHashSet<Long> getOrganismIds();

    int getOrganismCount();

    String getSequence();

    int getSequenceLength();

    String getRepresentativeId();

    String getSeedId();

    String getRepresentativeProteinName();

    Set<UniRefMemberIdType> getMemberIdTypes();
}