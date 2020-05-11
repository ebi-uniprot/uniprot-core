package org.uniprot.core.uniref;

import org.uniprot.core.cv.go.GeneOntologyEntry;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author jluo
 * @date: 9 Aug 2019
 */
public interface UniRefEntry extends Serializable {
    UniRefEntryId getId();

    String getName();

    LocalDate getUpdated();

    UniRefType getEntryType();

    long getCommonTaxonId();

    String getCommonTaxon();

    List<GeneOntologyEntry> getGoTerms();

    RepresentativeMember getRepresentativeMember();

    List<UniRefMember> getMembers();

    int getMemberCount();
}
