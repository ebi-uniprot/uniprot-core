package org.uniprot.cv.ec;

import org.uniprot.core.cv.ec.ECEntry;

import java.util.Optional;

public interface ECRepo {
    Optional<ECEntry> getEC(String id);
}
