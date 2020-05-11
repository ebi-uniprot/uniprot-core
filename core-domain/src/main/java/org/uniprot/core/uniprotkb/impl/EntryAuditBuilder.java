package org.uniprot.core.uniprotkb.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.EntryAudit;

import java.time.LocalDate;

import javax.annotation.Nonnull;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class EntryAuditBuilder implements Builder<EntryAudit> {
    private LocalDate firstPublicDate;
    private LocalDate lastAnnotationUpdateDate;
    private LocalDate lastSequenceUpdateDate;
    private int entryVersion;
    private int sequenceVersion;

    public @Nonnull EntryAuditBuilder firstPublic(LocalDate firstPublic) {
        this.firstPublicDate = firstPublic;
        return this;
    }

    public @Nonnull EntryAuditBuilder lastAnnotationUpdate(LocalDate lastAnnotationUpdate) {
        this.lastAnnotationUpdateDate = lastAnnotationUpdate;
        return this;
    }

    public @Nonnull EntryAuditBuilder lastSequenceUpdate(LocalDate lastSequenceUpdate) {
        this.lastSequenceUpdateDate = lastSequenceUpdate;
        return this;
    }

    public @Nonnull EntryAuditBuilder entryVersion(int entryVersion) {
        this.entryVersion = entryVersion;
        return this;
    }

    public @Nonnull EntryAuditBuilder sequenceVersion(int sequenceVersion) {
        this.sequenceVersion = sequenceVersion;
        return this;
    }

    @Override
    public @Nonnull EntryAudit build() {
        return new EntryAuditImpl(
                firstPublicDate,
                lastAnnotationUpdateDate,
                lastSequenceUpdateDate,
                entryVersion,
                sequenceVersion);
    }

    public static @Nonnull EntryAuditBuilder from(@Nonnull EntryAudit instance) {
        return new EntryAuditBuilder()
                .lastAnnotationUpdate(instance.getLastAnnotationUpdateDate())
                .lastSequenceUpdate(instance.getLastSequenceUpdateDate())
                .firstPublic(instance.getFirstPublicDate())
                .entryVersion(instance.getEntryVersion())
                .sequenceVersion(instance.getSequenceVersion());
    }
}
