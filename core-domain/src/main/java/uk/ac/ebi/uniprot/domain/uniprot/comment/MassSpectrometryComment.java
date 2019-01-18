package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.List;

public interface MassSpectrometryComment extends Comment {

    public Double getMolWeight();

    public Double getMolWeightError();

    public String getNote();

    public List<MassSpectrometryRange> getRanges();

    public MassSpectrometryMethod getMethod();

    public List<Evidence> getEvidences();
}
