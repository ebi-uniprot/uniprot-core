package uk.ac.ebi.uniprot.domain.uniprot.comments;


import java.util.List;

public interface APIsoform   {

    public IsoformName getName();
    public boolean hasName();
    public List<IsoformSynonym> getSynonyms();
    public CommentNote getNote();
    public boolean hasNote();
    public List<IsoformId> getIds();
    public List<String> getSequenceIds();
    public IsoformSequenceStatus getIsoformSequenceStatus();

}
