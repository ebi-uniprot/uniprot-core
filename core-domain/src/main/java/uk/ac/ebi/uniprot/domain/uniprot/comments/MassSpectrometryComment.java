package uk.ac.ebi.uniprot.domain.uniprot.comments;

import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.List;

public interface MassSpectrometryComment extends Comment {

    public Double getMolWeightError();

    public Double getMolWeight();
    public String getNote();

    public List<MassSpectrometryRange> getRanges();

    public MassSpectrometryMethod getMethod();

    public List<Evidence> getEvidences();

}
