package org.uniprot.core.xml.unirule;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.MultiValueType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class MultiValueConverter implements Converter<MultiValueType, List<String>> {

    private final ObjectFactory objectFactory;

    public MultiValueConverter() {
        this(new ObjectFactory());
    }

    public MultiValueConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public List<String> fromXml(MultiValueType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        return xmlObj.getValue();
    }

    @Override
    public MultiValueType toXml(List<String> uniObj) {
        MultiValueType multiValueType = null;
        if (Objects.nonNull(uniObj)) {
            multiValueType = this.objectFactory.createMultiValueType();
            List<String> multiValueValues = multiValueType.getValue();
            multiValueValues.addAll(uniObj);
        }
        return multiValueType;
    }
}
