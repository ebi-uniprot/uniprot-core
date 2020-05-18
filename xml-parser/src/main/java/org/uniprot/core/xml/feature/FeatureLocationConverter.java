package org.uniprot.core.xml.feature;

import com.google.common.base.Strings;
import org.uniprot.core.Position;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.feature.LocationType;
import org.uniprot.core.xml.jaxb.feature.ObjectFactory;
import org.uniprot.core.xml.jaxb.feature.PositionType;

import java.math.BigInteger;

import static org.uniprot.core.xml.utils.FeatureUtils.*;

public class FeatureLocationConverter implements Converter<LocationType, FeatureLocation> {

    private final ObjectFactory xmlUniprotFactory;

    public FeatureLocationConverter() {
        this.xmlUniprotFactory = new ObjectFactory();
    }

    public FeatureLocationConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public FeatureLocation fromXml(LocationType xmlLocation) {
        Position start;
        Position end;
        if (Utils.notNull(xmlLocation.getPosition())) {
            start = fromXml(xmlLocation.getPosition(), LESS_THAN);
            end = fromXml(xmlLocation.getPosition(), GREATER_THAN);
        } else {
            start = fromXml(xmlLocation.getBegin(), LESS_THAN);
            end = fromXml(xmlLocation.getEnd(), GREATER_THAN);
        }

        String sequence = xmlLocation.getSequence();
        return new FeatureLocation(
                sequence, start.getValue(), end.getValue(), start.getModifier(), end.getModifier());
    }

    @Override
    public LocationType toXml(FeatureLocation location) {
        LocationType locationType = xmlUniprotFactory.createLocationType();
        if (locationIsSame(location, PositionModifier.EXACT)) {
            setExactPosition(locationType, location);
        } else if (locationIsSame(location, PositionModifier.UNSURE)) {
            setUnsurePosition(locationType, location);
        } else if (locationIsSame(location, PositionModifier.UNKNOWN)) {
            setUnknownPosition(locationType);
        } else {
            locationType.setBegin(toXml(location.getStart(), LESS_THAN));
            locationType.setEnd(toXml(location.getEnd(), GREATER_THAN));
        }
        if (!Strings.isNullOrEmpty(location.getSequence())) {
            locationType.setSequence(location.getSequence());
        }
        return locationType;
    }

    private Position fromXml(PositionType position, String outsideString) {
        return positionfromXml(position.getPosition(), position.getStatus(), outsideString);
    }

    private PositionType toXml(Position position, String outsideString) {
        PositionType positionType = xmlUniprotFactory.createPositionType();
        if(Utils.notNull(position.getValue())) {
            switch (position.getModifier()) {
                case UNKNOWN:
                    positionType.setStatus(UNKNOWN);
                    break;
                case OUTSIDE:
                    positionType.setStatus(outsideString);
                    positionType.setPosition(BigInteger.valueOf(position.getValue()));
                    break;
                case UNSURE:
                    positionType.setStatus(UNCERTAIN);
                    positionType.setPosition(BigInteger.valueOf(position.getValue()));
                    break;
                default:
                    positionType.setPosition(BigInteger.valueOf(position.getValue()));
            }
        }
        return positionType;
    }

    private void setUnsurePosition(LocationType locationType, Range location) {
        PositionType positionType = xmlUniprotFactory.createPositionType();
        positionType.setStatus(UNCERTAIN);
        if(Utils.notNull(location.getStart().getValue())) {
            positionType.setPosition(BigInteger.valueOf(location.getStart().getValue()));
        }
        locationType.setPosition(positionType);
    }

    private void setExactPosition(LocationType locationType, Range location) {
        PositionType positionType = xmlUniprotFactory.createPositionType();
        if(Utils.notNull(location.getStart().getValue())) {
            positionType.setPosition(BigInteger.valueOf(location.getStart().getValue()));
        }
        locationType.setPosition(positionType);
    }

    private void setUnknownPosition(LocationType locationType) {
        PositionType positionType = xmlUniprotFactory.createPositionType();
        locationType.setPosition(positionType);
        positionType.setStatus(UNKNOWN);
    }


}
