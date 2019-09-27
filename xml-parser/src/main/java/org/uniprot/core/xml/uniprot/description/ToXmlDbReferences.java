package org.uniprot.core.xml.uniprot.description;

import java.util.List;

import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;

public interface ToXmlDbReferences<T> {
    List<DbReferenceType> toXmlDbReferences(T t);
}
