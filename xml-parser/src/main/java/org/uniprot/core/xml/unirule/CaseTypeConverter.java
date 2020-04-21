package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.CaseRule;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.CaseType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class CaseTypeConverter implements Converter<CaseType, CaseRule> {

    private final ObjectFactory objectFactory;

    public CaseTypeConverter() {
        this(new ObjectFactory());
    }

    public CaseTypeConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public CaseRule fromXml(CaseType xmlObj) {
        return null;
    }

    @Override
    public CaseType toXml(CaseRule uniObj) {
        return null;
    }
}
