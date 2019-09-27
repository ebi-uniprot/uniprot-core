package org.uniprot.core.impl;

import java.time.LocalDate;

import org.uniprot.core.UniProtRelease;

/**
 * @author jluo
 * @date: 13 Aug 2019
 */
public class UniProtReleaseImpl implements UniProtRelease {
    private static final long serialVersionUID = -7846089090404292951L;
    private String currentVersion;
    private LocalDate currentReleaseDate;
    private String nextVersion;
    private LocalDate nextReleaseDate;

    protected UniProtReleaseImpl() {}

    public UniProtReleaseImpl(
            String currentVersion,
            LocalDate currentReleaseDate,
            String nextVersion,
            LocalDate nextReleaseDate) {
        this.currentVersion = currentVersion;
        this.currentReleaseDate = currentReleaseDate;
        this.nextVersion = nextVersion;
        this.nextReleaseDate = nextReleaseDate;
    }

    @Override
    public String getCurrentVersion() {
        return this.currentVersion;
    }

    @Override
    public LocalDate getCurrentReleaseDate() {
        return this.currentReleaseDate;
    }

    @Override
    public String getNextVersion() {
        return this.nextVersion;
    }

    @Override
    public LocalDate getNextReleaseDate() {
        return this.nextReleaseDate;
    }
}
