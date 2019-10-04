package org.uniprot.core.uniprot.builder;

import java.time.LocalDate;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.EntryAudit;
import org.uniprot.core.uniprot.impl.EntryAuditImpl;

import javax.annotation.Nonnull;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class EntryAuditBuilder implements Builder<EntryAuditBuilder, EntryAudit> {
    private LocalDate firstPublicDate;
    private LocalDate lastAnnotationUpdateDate;
    private LocalDate lastSequenceUpdateDate;
    private int entryVersion;
    private int sequenceVersion;

    public EntryAuditBuilder firstPublic(LocalDate firstPublic) {
        this.firstPublicDate = firstPublic;
        return this;
    }

    public EntryAuditBuilder lastAnnotationUpdate(LocalDate lastAnnotationUpdate) {
        this.lastAnnotationUpdateDate = lastAnnotationUpdate;
        return this;
    }

    public EntryAuditBuilder lastSequenceUpdate(LocalDate lastSequenceUpdate) {
        this.lastSequenceUpdateDate = lastSequenceUpdate;
        return this;
    }

    public EntryAuditBuilder entryVersion(int entryVersion) {
        this.entryVersion = entryVersion;
        return this;
    }

    public EntryAuditBuilder sequenceVersion(int sequenceVersion) {
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

    @Override
    public @Nonnull EntryAuditBuilder from(@Nonnull EntryAudit instance) {
        return new EntryAuditBuilder()
                .lastAnnotationUpdate(instance.getLastAnnotationUpdateDate())
                .lastSequenceUpdate(instance.getLastSequenceUpdateDate())
                .firstPublic(instance.getFirstPublicDate())
                .entryVersion(instance.getEntryVersion())
                .sequenceVersion(instance.getSequenceVersion());
    }
}
