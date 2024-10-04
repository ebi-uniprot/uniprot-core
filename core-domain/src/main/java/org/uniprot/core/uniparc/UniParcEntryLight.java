package org.uniprot.core.uniparc;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UniParcEntryLight extends Serializable {
    String getUniParcId();

    int getCrossReferenceCount();

    List<CommonOrganism> getCommonTaxons();

    Set<String> getUniProtKBAccessions();

    Sequence getSequence();

    List<SequenceFeature> getSequenceFeatures();

    LocalDate getOldestCrossRefCreated();

    LocalDate getMostRecentCrossRefUpdated();

    Map<String, Object> getExtraAttributes();

    // lazily loaded fields
    Set<Organism> getOrganisms();// organism id and name pair

    Set<String> getProteinNames();

    Set<String> getGeneNames();

    Set<Proteome> getProteomes();
}
