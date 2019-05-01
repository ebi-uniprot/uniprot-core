package uk.ac.ebi.uniprot.xml.uniprot;

import java.math.BigInteger;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.Position;
import uk.ac.ebi.uniprot.domain.PositionModifier;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.LocationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.PositionType;

public class FeatureLocationConverter implements Converter<LocationType, Range> {
	private static final String GREATER_THAN = "greater than";
	private static final String LESS_THAN = "less than";
	private static final String UNKNOWN = "unknown";
	private static final String UNCERTAIN = "uncertain";
	private final ObjectFactory xmlUniprotFactory;

	public FeatureLocationConverter() {
		this(new ObjectFactory());
	}

	public FeatureLocationConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Range fromXml(LocationType xmlLocation) {
		Position start;
		Position end;
		if (xmlLocation.getPosition() != null) {
			start = fromXml(xmlLocation.getPosition(), LESS_THAN);
			end = fromXml(xmlLocation.getPosition(), GREATER_THAN);
		} else {
			start = fromXml(xmlLocation.getBegin(), LESS_THAN);
			end = fromXml(xmlLocation.getEnd(), GREATER_THAN);
		}

		return new Range(start, end);

	}

	private Position fromXml(PositionType position, String outsideString) {
		PositionModifier modifier = PositionModifier.EXACT;
		String status = position.getStatus();
		if (Strings.isNullOrEmpty(status)) {

		} else if (status.equals(UNKNOWN)) {
			modifier = PositionModifier.UNKNOWN;
			return new Position(-1, modifier);
		} else if (status.equals(UNCERTAIN)) {
			modifier = PositionModifier.UNSURE;
		} else if (status.equals(outsideString)) {
			modifier = PositionModifier.OUTSIDE;
		}
		return new Position(position.getPosition().intValue(), modifier);

	}

	@Override
	public LocationType toXml(Range location) {
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
		return locationType;
	}

	private PositionType toXml(Position position, String outsideString) {
		PositionType positionType = xmlUniprotFactory.createPositionType();
		switch (position.getModifier()) {
		case UNKNOWN:
			positionType.setStatus(UNKNOWN);
			break;
		case UNSURE:
			positionType.setStatus(UNCERTAIN);
			positionType.setPosition(BigInteger.valueOf(position.getValue()));
			break;
		case OUTSIDE:
			positionType.setStatus(outsideString);
			positionType.setPosition(BigInteger.valueOf(position.getValue()));
			break;
		default:
			positionType.setPosition(BigInteger.valueOf(position.getValue()));
		}

		return positionType;
	}

	private void setExactPosition(LocationType locationType, Range location) {
		PositionType positionType = xmlUniprotFactory.createPositionType();
		positionType.setPosition(BigInteger.valueOf(location.getStart().getValue()));
		locationType.setPosition(positionType);
	}

	private void setUnsurePosition(LocationType locationType, Range location) {
		PositionType positionType = xmlUniprotFactory.createPositionType();
		positionType.setPosition(BigInteger.valueOf(location.getStart().getValue()));
		positionType.setStatus(UNCERTAIN);
		locationType.setPosition(positionType);
	}

	private void setUnknownPosition(LocationType locationType) {
		PositionType positionType = xmlUniprotFactory.createPositionType();
		positionType.setStatus(UNKNOWN);
		locationType.setPosition(positionType);
	}

	private boolean locationIsSame(Range location, PositionModifier modifier) {
		boolean isModifierSame = (location.getStart().getModifier() == modifier)
				&& (location.getEnd().getModifier() == modifier);
		if (modifier == PositionModifier.UNKNOWN)
			return isModifierSame;
		else {
			if (!isModifierSame)
				return isModifierSame;
			else
				return location.getStart().getValue().equals(location.getEnd().getValue());
		}

	}
}
