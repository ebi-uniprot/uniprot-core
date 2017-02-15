package uk.ac.ebi.kraken.interfaces.uniprot.internalsection;

import java.util.List;

public interface InternalSection  {
    
    public List<InternalLine> getInternalLines();
    public List<SourceLine> getSourceLines();
}
