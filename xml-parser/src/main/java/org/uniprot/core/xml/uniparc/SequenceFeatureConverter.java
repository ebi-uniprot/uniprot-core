package org.uniprot.core.xml.uniparc;

import java.util.stream.Collectors;

import org.uniprot.core.Location;
import org.uniprot.core.uniparc.InterProGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.uniparc.impl.InterProGroupBuilder;
import org.uniprot.core.uniparc.impl.SequenceFeatureBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniparc.LocationType;
import org.uniprot.core.xml.jaxb.uniparc.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniparc.SeqFeatureGroupType;
import org.uniprot.core.xml.jaxb.uniparc.SeqFeatureType;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class SequenceFeatureConverter implements Converter<SeqFeatureType, SequenceFeature> {
    private final ObjectFactory xmlFactory;
    private final InterProGroupConverter interproGroupConverter;
    private final LocationConverter locationConverter;

    public SequenceFeatureConverter() {
        this(new ObjectFactory());
    }

    public SequenceFeatureConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
        interproGroupConverter = new InterProGroupConverter(xmlFactory);
        locationConverter = new LocationConverter(xmlFactory);
    }

    @Override
    public SequenceFeature fromXml(SeqFeatureType xmlObj) {
        SequenceFeatureBuilder builder = new SequenceFeatureBuilder();
        builder.interproGroup(interproGroupConverter.fromXml(xmlObj.getIpr()))
                .signatureDbType(SignatureDbType.typeOf(xmlObj.getDatabase()))
                .signatureDbId(xmlObj.getId())
                .locationsSet(
                        xmlObj.getLcn().stream()
                                .map(locationConverter::fromXml)
                                .collect(Collectors.toList()));

        return builder.build();
    }

    @Override
    public SeqFeatureType toXml(SequenceFeature uniObj) {
        SeqFeatureType xmlObj = xmlFactory.createSeqFeatureType();
        xmlObj.setDatabase(uniObj.getSignatureDbType().toDisplayName());
        xmlObj.setId(uniObj.getSignatureDbId());
        xmlObj.setIpr(interproGroupConverter.toXml(uniObj.getInterProDomain()));

        uniObj.getLocations().stream()
                .map(locationConverter::toXml)
                .forEach(val -> xmlObj.getLcn().add(val));
        return xmlObj;
    }

    class InterProGroupConverter implements Converter<SeqFeatureGroupType, InterProGroup> {
        private final ObjectFactory xmlFactory;

        InterProGroupConverter(ObjectFactory xmlFactory) {
            this.xmlFactory = xmlFactory;
        }

        @Override
        public InterProGroup fromXml(SeqFeatureGroupType xmlObj) {
            return new InterProGroupBuilder().name(xmlObj.getName()).id(xmlObj.getId()).build();
        }

        @Override
        public SeqFeatureGroupType toXml(InterProGroup uniObj) {
            SeqFeatureGroupType xmlObj = xmlFactory.createSeqFeatureGroupType();
            xmlObj.setName(uniObj.getName());
            xmlObj.setId(uniObj.getId());
            return xmlObj;
        }
    }

    class LocationConverter implements Converter<LocationType, Location> {
        private final ObjectFactory xmlFactory;

        LocationConverter(ObjectFactory xmlFactory) {
            this.xmlFactory = xmlFactory;
        }

        @Override
        public Location fromXml(LocationType xmlObj) {
            return new Location(xmlObj.getStart(), xmlObj.getEnd());
        }

        @Override
        public LocationType toXml(Location uniObj) {
            LocationType xmlObj = xmlFactory.createLocationType();
            xmlObj.setStart(uniObj.getStart());
            xmlObj.setEnd(uniObj.getEnd());
            return xmlObj;
        }
    }
}
