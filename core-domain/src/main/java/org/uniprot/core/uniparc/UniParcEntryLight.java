package org.uniprot.core.uniparc;

import org.uniprot.core.Sequence;
import org.uniprot.core.util.Pair;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface UniParcEntryLight extends Serializable {
    UniParcId getUniParcId();

    List<String> getUniParcCrossReferences();
    List<Pair<String, String>> getCommonTaxons();// taxonId and name pair

    Set<String> getUniProtKBAccessions();

    Sequence getSequence();

    List<SequenceFeature> getSequenceFeatures();

    LocalDate getOldestCrossRefCreated();

    LocalDate getMostRecentCrossRefUpdated();

    // lazily loaded fields
    List<Pair<Integer, String>> getOrganisms();// organism id and name pair

    List<String> getProteinNames();

    List<String> getGeneNames();

    List<String> getProteomeIds();
}
