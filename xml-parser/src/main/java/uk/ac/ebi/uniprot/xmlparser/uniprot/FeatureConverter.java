package uk.ac.ebi.uniprot.xmlparser.uniprot;

import java.util.List;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.FeatureFactory;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.uniprot.feature.impl.AlternativeSequenceImpl;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class FeatureConverter implements Converter<FeatureType, Feature> {

	private static final String STOP = ".";
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;
	private final FeatureLocationConverter locationConverter;

	public FeatureConverter(EvidenceIndexMapper evRefMapper) {
		this(new FeatureLocationConverter(), evRefMapper, new ObjectFactory());
	}

	public FeatureConverter(FeatureLocationConverter locationConverter, EvidenceIndexMapper evRefMapper,
			ObjectFactory xmlUniprotFactory) {
		this.locationConverter = locationConverter;
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;

	}

	@Override
	public Feature fromXml(FeatureType xmlObj) {
		uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType type = uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType
				.typeOf(xmlObj.getType());
		String description = "";
		if (xmlObj.getDescription() != null) {
			description = xmlObj.getDescription();
			if (AlternativeSequenceImpl.hasAlternativeSequence(type)) {
				description = XmlTextHelper.removeIfPostfix(description, STOP);
				if(type != uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType.MUTAGEN)
					description = XmlTextHelper.lowercaseFirstLetter(description);
			}
		}
	
		Range location = locationConverter.fromXml(xmlObj.getLocation());
		List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlObj.getEvidence());
		String ftid = xmlObj.getId();
		FeatureId featureId = null;
		if(!Strings.isNullOrEmpty(ftid)) {
			featureId = FeatureFactory.INSTANCE.createFeatureId(ftid);
		}
		AlternativeSequence altSeq =null;
		if(!Strings.isNullOrEmpty(xmlObj.getOriginal())) {
			altSeq = FeatureFactory.INSTANCE.createAlternativeSequence(xmlObj.getOriginal(), xmlObj.getVariation());
		}
		
		return FeatureFactory.INSTANCE.createFeature(type, location, description, featureId, altSeq,  evidences);
	}

	
	@Override
	public FeatureType toXml(Feature uniObj) {
		FeatureType xmlFeature = xmlUniprotFactory.createFeatureType();

		// feature type
		if (uniObj.getType() != null) {
			xmlFeature.setType(uniObj.getType().getValue());
		}
		xmlFeature.setLocation(locationConverter.toXml(uniObj.getLocation()));
		if (uniObj.getFeatureId() != null) {
			xmlFeature.setId(uniObj.getFeatureId().getValue());
		}
		if ((uniObj.getDescription() != null) && !uniObj.getDescription().getValue().isEmpty()) {
			String val =uniObj.getDescription().getValue();
			if (AlternativeSequenceImpl.hasAlternativeSequence(uniObj.getType())) {
				val  = XmlTextHelper.addIfNoPostfix(val, STOP);
				
				if(uniObj.getType() != uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType.MUTAGEN)
					val = XmlTextHelper.uppercaseFirstLetter(val);
			}
			xmlFeature.setDescription(val);
		}
		// feature xml evidence tags set
		if (!uniObj.getEvidences().isEmpty()) {
			List<Integer> evs = evRefMapper.writeEvidences(uniObj.getEvidences());
			if (!evs.isEmpty())
				xmlFeature.getEvidence().addAll(evs);
		}
		if(uniObj.getAlternativeSequence() !=null) {
			AlternativeSequence altSeq = uniObj.getAlternativeSequence() ;
			xmlFeature.setOriginal(altSeq.getOriginalSequence());
			xmlFeature.getVariation().addAll(altSeq.getAlternativeSequences());
		}
		return xmlFeature;
	}

}
