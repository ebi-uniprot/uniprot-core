package org.uniprot.core.xml.uniprot.description;

import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;

import java.util.List;

public interface ToXmlDbReferences<T> {
    List<DbReferenceType> toXmlDbReferences(T t);
}
