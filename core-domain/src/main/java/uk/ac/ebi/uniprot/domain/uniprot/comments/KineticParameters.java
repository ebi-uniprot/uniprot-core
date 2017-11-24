package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

public interface KineticParameters {
    public List<MaximumVelocity> getMaximumVelocities();

    public List<MichaelisConstant> getMichaelisConstants();

    public KPNote getNote();

    public boolean hasNote();

}
