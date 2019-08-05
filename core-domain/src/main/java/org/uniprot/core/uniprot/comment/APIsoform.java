package org.uniprot.core.uniprot.comment;


import java.io.Serializable;
import java.util.List;

public interface APIsoform extends Serializable {

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
