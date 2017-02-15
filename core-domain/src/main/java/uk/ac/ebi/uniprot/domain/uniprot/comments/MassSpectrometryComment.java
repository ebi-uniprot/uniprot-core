package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;

public interface MassSpectrometryComment extends Comment {

    public float getMolWeightError();

    public float getMolWeight();

    public boolean hasNote();
    public MassSpectrometryCommentNote getNote();

    public List<MassSpectrometryRange> getRanges();

    public MassSpectrometryMethod getMethod();

    public List<MassSpectrometryCommentSource> getSources();

}
