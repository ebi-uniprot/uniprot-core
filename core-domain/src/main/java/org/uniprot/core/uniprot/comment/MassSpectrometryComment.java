package org.uniprot.core.uniprot.comment;

import java.util.List;

import org.uniprot.core.uniprot.evidence.Evidence;

public interface MassSpectrometryComment extends Comment {

    public Float getMolWeight();

    public Float getMolWeightError();

    public String getNote();

    public List<MassSpectrometryRange> getRanges();

    public MassSpectrometryMethod getMethod();

    public List<Evidence> getEvidences();

    boolean hasMolWeight();

    boolean hasMolWeightError();

    boolean hasNote();

    boolean hasRanges();

    boolean hasMethod();

    boolean hasEvidences();
}
