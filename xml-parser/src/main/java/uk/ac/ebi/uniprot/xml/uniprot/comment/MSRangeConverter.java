package uk.ac.ebi.uniprot.xml.uniprot.comment;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.LocationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.PositionType;

import java.math.BigInteger;

import org.uniprot.core.Position;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;
import org.uniprot.core.uniprot.comment.builder.MassSpectrometryRangeBuilder;

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
        if (xmlObj == null)
            return null;
        Range range = null;
        if (xmlObj.getPosition() != null) {
            new Range(fromXml(xmlObj.getPosition()), fromXml(xmlObj.getPosition()));

        } else {
            range = new Range(fromXml(xmlObj.getBegin()), fromXml(xmlObj.getEnd()));
        }
        return new MassSpectrometryRangeBuilder()
                .range(range)
                .isoformId(xmlObj.getSequence())
                .build();
    }

    @Override
    public LocationType toXml(MassSpectrometryRange uniObj) {
        if (uniObj != null) {
            LocationType locationXML = xmlUniprotFactory.createLocationType();
            if (uniObj.getRange().getStart().getModifier() == PositionModifier.UNKNOWN) {
                locationXML.setBegin(toXml(uniObj.getRange().getStart()));
                locationXML.setEnd(toXml(uniObj.getRange().getEnd()));
            } else if (uniObj.getRange().getStart().equals(uniObj.getRange().getEnd())) {
                locationXML.setPosition(toXml(uniObj.getRange().getStart()));
            } else {
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
        if (UNKNOWN.equals(posType.getStatus())) {
            return new Position(0, PositionModifier.UNKNOWN);
        } else
            return new Position(posType.getPosition().intValue());
    }

    private PositionType toXml(Position position) {
        PositionType posType = xmlUniprotFactory.createPositionType();
        if (position.getModifier() == PositionModifier.UNKNOWN) {
            posType.setStatus(UNKNOWN);
        } else {
            posType.setPosition(BigInteger.valueOf(position.getValue()));
        }
        return posType;
    }

}
