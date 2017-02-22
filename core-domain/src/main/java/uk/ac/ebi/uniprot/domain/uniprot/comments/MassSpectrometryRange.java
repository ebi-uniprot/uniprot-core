package uk.ac.ebi.uniprot.domain.uniprot.comments;

public interface MassSpectrometryRange  {
    public final String UNKNOWN = "unknown";

    public int getStart();

    public boolean isStartUnknown();
    public int getEnd();

    public boolean isEndUnknown();

    public boolean hasIsoformId();

    public String getIsoformId();
}
