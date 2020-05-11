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
        this.objectFactory = objectFactory;
    }

    @Override
    public Range fromXml(RangeType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        Integer start = parseIntOrNull(xmlObj.getStart());
        Integer end = parseIntOrNull(xmlObj.getEnd());
        Range range = new Range(start, end);
        return range;
    }

    @Override
    public RangeType toXml(Range uniObj) {
        if (Objects.isNull(uniObj)) return null;
        RangeType rangeType = this.objectFactory.createRangeType();

        if (Objects.nonNull(uniObj.getStart())) {
            rangeType.setStart(String.valueOf(uniObj.getStart().getValue()));
        }

        if (Objects.nonNull(uniObj.getEnd())) {
            rangeType.setEnd(String.valueOf(uniObj.getEnd().getValue()));
        }

        return rangeType;
    }

    Integer parseIntOrNull(String value) {
        Integer integralVal = null;
        try {
            integralVal = Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            // do nothing
        }
        return integralVal;
    }
}
