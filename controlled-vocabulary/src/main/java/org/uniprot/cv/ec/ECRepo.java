package org.uniprot.cv.ec;

import java.util.Optional;

import org.uniprot.core.cv.ec.ECEntry;

public interface ECRepo {
    Optional<ECEntry> getEC(String id);
}
