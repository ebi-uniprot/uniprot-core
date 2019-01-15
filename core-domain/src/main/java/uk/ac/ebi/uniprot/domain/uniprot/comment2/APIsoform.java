package uk.ac.ebi.uniprot.domain.uniprot.comment2;


import java.util.List;

public interface APIsoform {

    IsoformName getName();

    List<IsoformName> getSynonyms();

    Note getNote();

    List<IsoformId> getIsoformIds();

    List<String> getSequenceIds();

    IsoformSequenceStatus getIsoformSequenceStatus();

}
