package uk.ac.ebi.uniprot.domain.uniprot.comment;


import java.util.List;

public interface APIsoform {

    IsoformName getName();

    List<IsoformName> getSynonyms();

    Note getNote();

    List<IsoformId> getIsoformIds();

    List<String> getSequenceIds();

    IsoformSequenceStatus getIsoformSequenceStatus();

    boolean hasName();

    boolean hasSynonyms();

    boolean hasNote();

    boolean hasIsoformIds();

    boolean hasSequenceIds();

    boolean hasIsoformSequenceStatus();

}
