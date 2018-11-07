package uk.ac.ebi.uniprot.domain.xdb;

import java.util.List;

import uk.ac.ebi.uniprot.domain.common.Property;

public interface DBCrossReference {
	String getDatabaseName();
    String getId();
    List<Property> getProperties();
}
