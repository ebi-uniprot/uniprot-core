package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EntryAuditImpl;

import java.time.LocalDate;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public class EntryAuditBuilder implements Builder2<EntryAuditBuilder, EntryAudit> {
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
    public EntryAudit build() {
        return new EntryAuditImpl(firstPublicDate,
                                  lastAnnotationUpdateDate,
                                  lastSequenceUpdateDate,
                                  entryVersion,
                                  sequenceVersion);
    }

    @Override
    public EntryAuditBuilder from(EntryAudit instance) {
        return new EntryAuditBuilder()
                .lastSequenceUpdate(instance.getLastSequenceUpdateDate())
                .firstPublic(instance.getFirstPublicDate())
                .entryVersion(instance.getEntryVersion())
                .sequenceVersion(instance.getSequenceVersion());
    }
}
