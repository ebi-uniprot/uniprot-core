package org.uniprot.cv.ec;

import java.util.Optional;

import org.uniprot.core.cv.ec.EC;

public interface ECRepo {
    Optional<EC> getEC(String id);
}
