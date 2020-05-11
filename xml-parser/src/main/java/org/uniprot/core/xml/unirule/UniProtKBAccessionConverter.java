package org.uniprot.core.xml.unirule;

import java.util.Objects;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class UniProtKBAccessionConverter implements Converter<String, UniProtKBAccession> {

    private final ObjectFactory objectFactory;

    public UniProtKBAccessionConverter() {
        this(new ObjectFactory());
    }

    public UniProtKBAccessionConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public UniProtKBAccession fromXml(String xmlObj) {
        if (Objects.isNull(xmlObj)) return null;
        return new UniProtKBAccessionBuilder(xmlObj).build();
    }

    @Override
    public String toXml(UniProtKBAccession uniObj) {
        if (Objects.isNull(uniObj)) return null;

        return uniObj.getValue();
    }
}
