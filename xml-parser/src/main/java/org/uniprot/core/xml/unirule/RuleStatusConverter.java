package org.uniprot.core.xml.unirule;

import java.util.Objects;

import org.uniprot.core.unirule.RuleStatus;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.jaxb.unirule.RuleStatusType;

public class RuleStatusConverter implements Converter<RuleStatusType, RuleStatus> {
    private final ObjectFactory objectFactory;

    public RuleStatusConverter() {
        this(new ObjectFactory());
    }

    public RuleStatusConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public RuleStatus fromXml(RuleStatusType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        return RuleStatus.typeOf(xmlObj.name());
    }

    @Override
    public RuleStatusType toXml(RuleStatus uniObj) {
        if (Objects.isNull(uniObj)) return null;
        return RuleStatusType.fromValue(uniObj.name());
    }
}
