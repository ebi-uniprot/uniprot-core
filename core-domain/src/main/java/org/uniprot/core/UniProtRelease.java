package org.uniprot.core;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author jluo
 * @date: 13 Aug 2019
 */
public interface UniProtRelease extends Serializable {
    String getCurrentVersion();

    LocalDate getCurrentReleaseDate();

    String getNextVersion();

    LocalDate getNextReleaseDate();
}
