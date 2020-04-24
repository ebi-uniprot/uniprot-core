package org.uniprot.core.xml.unirule;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

import java.util.Objects;

public class UniProtKBAccessionConverter implements Converter<String, UniProtKBAccession> {

    private final ObjectFactory objectFactory;

    public UniProtKBAccessionConverter(){
        this(new ObjectFactory());
    }

    public UniProtKBAccessionConverter(ObjectFactory objectFactory){
        this.objectFactory = new ObjectFactory();
    }

    @Override
    public UniProtKBAccession fromXml(String xmlObj) {
        if (Objects.isNull(xmlObj)) return null;
        return new UniProtKBAccessionBuilder(xmlObj).build();
    }

    @Override
    public String toXml(UniProtKBAccession uniObj) {
        return null;
    }
}
