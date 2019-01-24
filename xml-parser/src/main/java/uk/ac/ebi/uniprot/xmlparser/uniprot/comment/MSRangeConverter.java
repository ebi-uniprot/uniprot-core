package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import java.math.BigInteger;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.Position;
import uk.ac.ebi.uniprot.domain.PositionModifier;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.MassSpectrometryCommentBuilder;	
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.LocationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.PositionType;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class MSRangeConverter implements Converter<LocationType, MassSpectrometryRange> {
	private static final String UNKNOWN = "unknown";
	private final ObjectFactory xmlUniprotFactory;

	public MSRangeConverter() {
		this(new ObjectFactory());
	}

	public MSRangeConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;

	}

	@Override
	public MassSpectrometryRange fromXml(LocationType xmlObj) {
		if(xmlObj ==null)
			return null;
		Range range = null;
		if(xmlObj.getPosition() !=null) {
			new Range(fromXml(xmlObj.getPosition()), fromXml(xmlObj.getPosition()));
			
		}else {
			range =new Range(fromXml(xmlObj.getBegin()), fromXml(xmlObj.getEnd()));
		}	
		return MassSpectrometryCommentBuilder.createMassSpectrometryRange(range, xmlObj.getSequence());
	}

	@Override
	public LocationType toXml(MassSpectrometryRange uniObj) {
		if (uniObj != null) {
			LocationType locationXML = xmlUniprotFactory.createLocationType();
			if(uniObj.getRange().getStart().getModifier() ==PositionModifier.UNKOWN) {
				locationXML.setBegin(toXml(uniObj.getRange().getStart()));
				locationXML.setEnd(toXml(uniObj.getRange().getEnd()));
			}
			else if(uniObj.getRange().getStart().equals(uniObj.getRange().getEnd())) {
				locationXML.setPosition(toXml(uniObj.getRange().getStart()));
			}	
			else {
				locationXML.setBegin(toXml(uniObj.getRange().getStart()));
				locationXML.setEnd(toXml(uniObj.getRange().getEnd()));
			}
			if (!Strings.isNullOrEmpty(uniObj.getIsoformId())) {
				locationXML.setSequence(uniObj.getIsoformId());
			}
			return locationXML;
		} else
			return null;
	}

	private Position fromXml(PositionType posType) {
		if(UNKNOWN.equals(posType.getStatus())) {
			return new Position(0, PositionModifier.UNKOWN );
		}else
			return new Position(posType.getPosition().intValue() );
	}
	private PositionType toXml(Position position) {
		PositionType posType = xmlUniprotFactory.createPositionType();
		if (position.getModifier() == PositionModifier.UNKOWN) {
			posType.setStatus(UNKNOWN);
		} else {
			posType.setPosition(BigInteger.valueOf(position.getValue()));
		}
		return posType;
	}

}
