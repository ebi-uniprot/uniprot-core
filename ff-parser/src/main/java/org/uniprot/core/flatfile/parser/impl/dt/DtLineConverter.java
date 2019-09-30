package org.uniprot.core.flatfile.parser.impl.dt;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.uniprot.EntryAudit;
import org.uniprot.core.uniprot.builder.EntryAuditBuilder;

public class DtLineConverter implements Converter<DtLineObject, EntryAudit> {

    @Override
    public EntryAudit convert(DtLineObject f) {
        return new EntryAuditBuilder()
                .entryVersion(f.entry_version)
                .sequenceVersion(f.seq_version)
                .lastSequenceUpdate(f.seq_date)
                .firstPublic(f.integration_date)
                .lastAnnotationUpdate(f.entry_date)
                .build();
    }
}
