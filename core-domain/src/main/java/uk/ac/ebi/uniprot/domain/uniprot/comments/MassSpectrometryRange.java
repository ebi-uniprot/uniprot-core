package uk.ac.ebi.uniprot.domain.uniprot.comments;

public interface MassSpectrometryRange  {
    public final String UNKNOWN = "unknown";

    public Integer getStart();

    public boolean isStartUnknown();
    public Integer getEnd();

    public boolean isEndUnknown();

    public boolean hasIsoformId();

    public String getIsoformId();
}
