package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import java.util.List;


public interface DatabaseDefContext {
//	DatabaseDef getXRefDBDef(String dbNme);
	DatabaseDisplayOrder getXRefDBDef(DatabaseType type);
	List<DatabaseDisplayOrder> getAllXRefDBDefs();
	List<String> getAllXrefDBName();
}
