package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import java.util.List;

import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;

public interface ToXmlDbReferences<T> {
	List<DbReferenceType> toXmlDbReferences(T t);
}
