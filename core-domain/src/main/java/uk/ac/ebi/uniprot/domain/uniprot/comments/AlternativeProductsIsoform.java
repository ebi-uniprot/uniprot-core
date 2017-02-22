package uk.ac.ebi.uniprot.domain.uniprot.comments;


import java.util.List;
import java.io.Serializable;

public interface AlternativeProductsIsoform  extends   Serializable  {

    public IsoformName getName();
    public boolean hasName();
    public List<IsoformSynonym> getSynonyms();
    public Note getNote();
    public boolean hasNote();
    public List<IsoformId> getIds();
    public List<String> getSequenceIds();
    public IsoformSequenceStatus getIsoformSequenceStatus();

}
