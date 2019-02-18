package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

public interface MassSpectrometryComment extends Comment {

    Float getMolWeight();

    Float getMolWeightError();

    String getNote();

    List<MassSpectrometryRange> getRanges();

    MassSpectrometryMethod getMethod();

    List<Evidence> getEvidences();
}
