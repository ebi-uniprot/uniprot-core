package uk.ac.ebi.uniprot.parser.impl.dt;



import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.Converter;


public class DtLineConverter implements Converter<DtLineObject, EntryAudit> {

	@Override
	public EntryAudit convert(DtLineObject f) {	
		return UniProtFactory.INSTANCE.createEntryAudit(f.integration_date, f.entry_date, f.seq_date, f.entry_version, f.seq_version);
	}
}
