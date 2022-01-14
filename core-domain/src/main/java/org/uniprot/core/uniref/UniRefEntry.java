package org.uniprot.core.uniref;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

/**
 * @author jluo
 * @date: 9 Aug 2019
 */
public interface UniRefEntry extends Serializable {
    UniRefEntryId getId();

    String getName();

    LocalDate getUpdated();

    UniRefType getEntryType();

    Organism getCommonTaxon();

    String getSeedId();

    List<GeneOntologyEntry> getGoTerms();

    RepresentativeMember getRepresentativeMember();

    List<UniRefMember> getMembers();

    Integer getMemberCount();
}
