package uk.ac.ebi.uniprot.xml.uniprot.comment;

import java.math.BigInteger;
import java.util.regex.Pattern;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.LocationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.PositionType;

public class SCPositionConverter implements Converter<LocationType, String> {
	private static final Pattern TARGET_A = Pattern.compile("\\d+\\-\\d+");
	private final ObjectFactory xmlUniprotFactory;

	public SCPositionConverter() {
		this(new ObjectFactory());
	}

	public SCPositionConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public String fromXml(LocationType locationType) {
		if(locationType ==null)
			return "";
          if(locationType.getBegin() != null && locationType.getEnd() != null){
              return locationType.getBegin().getPosition().toString() +"-"+ locationType.getEnd().getPosition().toString();
          }
          else if(locationType.getPosition() != null && locationType.getPosition().getPosition() != null){
              return locationType.getPosition().getPosition().toString();
          }else

          return "";
	}

	@Override
	public LocationType toXml(String position) {
		if(Strings.isNullOrEmpty(position))
			return null;
		LocationType locationType = xmlUniprotFactory.createLocationType();

		if (TARGET_A.matcher(position).matches()) {
			int index = position.indexOf('-');
			String start = position.substring(0, index);
			String end = position.substring(index + 1, (position.length()));

			PositionType startPositionType = xmlUniprotFactory.createPositionType();
			startPositionType.setPosition(new BigInteger(start));

			locationType.setBegin(startPositionType);

			PositionType endPositionType = xmlUniprotFactory.createPositionType();
			endPositionType.setPosition(new BigInteger(end));

			locationType.setEnd(endPositionType);
		} else if (position.isEmpty()) {
			locationType.setPosition(null);
		} else {
			PositionType singlePositionType = xmlUniprotFactory.createPositionType();
			if (isNumeric(position)) {
				singlePositionType.setPosition(new BigInteger(position));
			}
			locationType.setPosition(singlePositionType);

		}

		return locationType;
	}

	private boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
}
