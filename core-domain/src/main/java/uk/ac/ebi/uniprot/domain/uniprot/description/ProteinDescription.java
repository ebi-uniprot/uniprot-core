package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;

public interface ProteinDescription {

    ProteinName getRecommendedName();

    List<ProteinName> getAlternativeNames();

    List<ProteinName> getSubmissionNames();

    Name getAllergenName();

    Name getBiotechName();

    List<Name> getCdAntigenNames();

    List<Name> getInnNames();

    List<ProteinSection> getIncludes();

    List<ProteinSection> getContains();

    boolean isValid();

    Flag getFlag();

}
