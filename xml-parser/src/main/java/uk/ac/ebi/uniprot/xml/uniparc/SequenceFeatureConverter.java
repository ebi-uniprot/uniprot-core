package uk.ac.ebi.uniprot.xml.uniparc;

import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.Location;
import uk.ac.ebi.uniprot.domain.uniparc.InterproGroup;
import uk.ac.ebi.uniprot.domain.uniparc.SequenceFeature;
import uk.ac.ebi.uniprot.domain.uniparc.SignatureDbType;
import uk.ac.ebi.uniprot.domain.uniparc.builder.InterProGroupBuilder;
import uk.ac.ebi.uniprot.domain.uniparc.builder.SequenceFeatureBuilder;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniparc.LocationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniparc.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniparc.SeqFeatureGroupType;
import uk.ac.ebi.uniprot.xml.jaxb.uniparc.SeqFeatureType;

/**
 *
 * @author jluo
 * @date: 23 May 2019
 *
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
		.locations(
		xmlObj.getLcn().stream().map(locationConverter::fromXml)
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
		.forEach(val-> xmlObj.getLcn().add(val));
		return xmlObj;
	}

	class InterProGroupConverter implements Converter<SeqFeatureGroupType,  InterproGroup>{
		private final ObjectFactory xmlFactory;
		InterProGroupConverter(ObjectFactory xmlFactory){
			this.xmlFactory = xmlFactory;
		}
		@Override
		public InterproGroup fromXml(SeqFeatureGroupType xmlObj) {
			return new InterProGroupBuilder()
			.name(xmlObj.getName())
			.id(xmlObj.getId())		
			.build();
		}

		@Override
		public SeqFeatureGroupType toXml(InterproGroup uniObj) {
			SeqFeatureGroupType xmlObj = xmlFactory.createSeqFeatureGroupType();
			xmlObj.setName(uniObj.getName());
			xmlObj.setId(uniObj.getId());
			return xmlObj;
		}
		
	}
	class LocationConverter implements Converter<LocationType,  Location>{
		private final ObjectFactory xmlFactory;
		LocationConverter(ObjectFactory xmlFactory){
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

