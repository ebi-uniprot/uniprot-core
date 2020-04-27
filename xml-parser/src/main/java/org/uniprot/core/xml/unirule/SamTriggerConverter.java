package org.uniprot.core.xml.unirule;

import java.util.Objects;

import org.uniprot.core.Range;
import org.uniprot.core.unirule.SamTrigger;
import org.uniprot.core.unirule.impl.SamTriggerBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.jaxb.unirule.RangeType;
import org.uniprot.core.xml.jaxb.unirule.SamTriggerType;

public class SamTriggerConverter implements Converter<SamTriggerType, SamTrigger> {

    private final ObjectFactory objectFactory;

    public SamTriggerConverter() {
        this(new ObjectFactory());
    }

    public SamTriggerConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public SamTrigger fromXml(SamTriggerType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        SamTriggerBuilder builder = new SamTriggerBuilder();
        if (Objects.nonNull(xmlObj.getTransmembrane())) {
            builder.expectedHits(fromXml(xmlObj.getTransmembrane().getExpectedHits()));
        } else if (Objects.nonNull(xmlObj.getSignal())) {
            builder.expectedHits(fromXml(xmlObj.getSignal().getExpectedHits()));
        } else if (Objects.nonNull(xmlObj.getCoiledCoil())) {
            builder.expectedHits(fromXml(xmlObj.getCoiledCoil().getExpectedHits()));
        }
        return builder.build();
    }

    @Override
    public SamTriggerType toXml(SamTrigger uniObj) {
        return null;
    }

    private Range fromXml(RangeType rangeType) {
        Range range =
                new Range(
                        Integer.parseInt(rangeType.getStart()),
                        Integer.parseInt(rangeType.getEnd()));
        return range;
    }
}
