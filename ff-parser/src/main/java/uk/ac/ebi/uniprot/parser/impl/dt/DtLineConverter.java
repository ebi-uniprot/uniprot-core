package uk.ac.ebi.uniprot.parser.impl.dt;



import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.builder.EntryAuditBuilder;
import uk.ac.ebi.uniprot.parser.Converter;


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
