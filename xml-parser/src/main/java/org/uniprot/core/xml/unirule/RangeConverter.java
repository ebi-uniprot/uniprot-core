package org.uniprot.core.xml.unirule;

import java.util.Objects;

import org.uniprot.core.Range;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.jaxb.unirule.RangeType;

public class RangeConverter implements Converter<RangeType, Range> {

    private final ObjectFactory objectFactory;

    public RangeConverter() {
        this(new ObjectFactory());
    }

    public RangeConverter(ObjectFactory objectFactory) {
        this.objectFactory = new ObjectFactory();
    }

    @Override
    public Range fromXml(RangeType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;
        Range range =
                new Range(Integer.parseInt(xmlObj.getStart()), Integer.parseInt(xmlObj.getEnd()));
        return range;
    }

    @Override
    public RangeType toXml(Range uniObj) {
        if (Objects.isNull(uniObj)) return null;
        RangeType rangeType = this.objectFactory.createRangeType();
        rangeType.setStart(String.valueOf(uniObj.getStart().getValue()));
        rangeType.setEnd(String.valueOf(uniObj.getEnd().getValue()));
        return rangeType;
    }
}
