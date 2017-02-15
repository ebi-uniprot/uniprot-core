package uk.ac.ebi.uniprot.domain.uniprot.internalsection;

import java.util.List;

public interface InternalSection  {
    
    public List<InternalLine> getInternalLines();
    public List<SourceLine> getSourceLines();
}
