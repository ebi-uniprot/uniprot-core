package uk.ac.ebi.uniprot.cv.ec;

import java.util.Optional;

public interface ECRepo {
    Optional<EC> getEC(String id);
}
