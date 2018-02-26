package uk.ac.ebi.uniprot.domain.uniprot.comments;


import java.util.List;
import java.util.Optional;

public interface APIsoform   {

     IsoformName getName();
     List<IsoformSynonym> getSynonyms();
     Optional<APIsoformNote> getNote();
     List<IsoformId> getIds();
     List<String> getSequenceIds();
     IsoformSequenceStatus getIsoformSequenceStatus();

}
