package uk.ac.ebi.uniprot.xml.uniprot.comment;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.LocationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.PositionType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;

import java.math.BigInteger;

import org.uniprot.core.uniprot.comment.RnaEdPosition;
import org.uniprot.core.uniprot.comment.builder.RnaEditingPositionBuilder;

public class RnaEdPositionConverter implements Converter<LocationType, RnaEdPosition> {


    private final ObjectFactory xmlUniprotFactory;
    private final EvidenceIndexMapper evRefMapper;

    public RnaEdPositionConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public RnaEdPositionConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.evRefMapper = evRefMapper;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }


    @Override
    public RnaEdPosition fromXml(LocationType xmlObj) {
        if (xmlObj != null && xmlObj.getPosition() != null) {
            String position = xmlObj.getPosition().getPosition().toString().trim();
            return new RnaEditingPositionBuilder(position, evRefMapper
                    .parseEvidenceIds(xmlObj.getPosition().getEvidence())).build();
        } else return null;
    }

    @Override
    public LocationType toXml(RnaEdPosition position) {
        if (position != null && !position.getPosition().isEmpty()) {
            LocationType locationType = xmlUniprotFactory.createLocationType();
            PositionType positionType = xmlUniprotFactory.createPositionType();

            final String pos = position.getPosition().trim();
            if (!Character.isDigit(pos.charAt(0))) {
                return null;
            }

            positionType.setPosition(new BigInteger(pos));
            positionType.setStatus(null);

            if (!position.getEvidences().isEmpty()) {
                positionType.getEvidence().addAll(evRefMapper.writeEvidences(position.getEvidences()));
            }

            locationType.setPosition(positionType);

            return locationType;
        } else return null;
    }

}
