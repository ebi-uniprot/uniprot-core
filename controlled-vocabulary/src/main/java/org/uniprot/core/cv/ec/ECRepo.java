package org.uniprot.core.cv.ec;

import java.util.Optional;

public interface ECRepo {
    Optional<EC> getEC(String id);
}
