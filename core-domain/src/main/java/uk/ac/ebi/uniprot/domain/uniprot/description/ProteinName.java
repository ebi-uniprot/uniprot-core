package uk.ac.ebi.uniprot.domain.uniprot.description;

import java.util.List;

public interface ProteinName {
    Name getFullName();

    List<Name> getShortNames();

    List<EC> getEcNumbers();

    boolean isValid();
}
