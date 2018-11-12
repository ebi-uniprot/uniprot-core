package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import java.util.List;

import uk.ac.ebi.uniprot.domain.Database;

public interface UniProtXDbType extends Database{
	String getDisplayName();
	DatabaseCategory getCategory();
	String getUriLink();
	List<DBXRefTypeAttribute> getAttributes();
}
