package uk.ac.ebi.uniprot.parser.impl.ft;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.feature.CarbohydLinkType;
import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocationModifier;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.FeatureFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;

public class FtLineConverter extends EvidenceCollector
		implements Converter<FtLineObject, List<UniProtFeature<? extends Feature>>> {
	private static final String CONFLICT_REGEX = ", | and ";
	private static final String MISSING = "Missing";
	private final FeatureFactory factory = FeatureFactory.INSTANCE;
	   private static final String BRACKET_RIGHT = ")";
	    private static final String BRACKET_LEFT = "(";
	    private static final String SEMICOLON = ";";
	   private static final String ISOFORM_REGEX =", isoform | and isoform ";

	@Override
	public List<UniProtFeature<? extends Feature>> convert(FtLineObject f) {
		List<UniProtFeature<? extends Feature>> features = new ArrayList<>();
		Map<Object, List<Evidence>> evidenceMap = EvidenceHelper.convert(f.getEvidenceInfo());
		this.addAll(evidenceMap.values());
		for (FtLineObject.FT ft : f.fts) {
			FeatureType featureType = convert(ft.type);
			FeatureLocation location = convertFeatureLocation(ft.location_start, ft.location_end);

			Feature feature = convertFeature(featureType, location, ft, evidenceMap);
			List<Evidence> evidences = evidenceMap.get(ft);
			UniProtFeature<? extends Feature> uniProtFeature = factory.createUniProtFeature(feature, evidences);
			features.add(uniProtFeature);

		}
		return features;
	}

	private Feature convertFeature(FeatureType type, FeatureLocation location, FtLineObject.FT ft,
			Map<Object, List<Evidence>> evidenceMap) {
		switch (type) {
		case CARBOHYD:
			return convertCarbohydFeature(type, location, ft, evidenceMap);
		case VAR_SEQ:
			return convertVarSeqFeature(type, location, ft, evidenceMap);
		case VARIANT:
			return convertVariantFeature(type, location, ft, evidenceMap);
		case CONFLICT:
			return convertConflictFeature(type, location, ft, evidenceMap);
		case MUTAGEN:
			return convertMutagenFeature(type, location, ft, evidenceMap);
		default:
			return convertSimpleFeature(type, location, ft, evidenceMap);

		}
	}

	private Feature convertCarbohydFeature(FeatureType type, FeatureLocation location, FtLineObject.FT ft,
			Map<Object, List<Evidence>> evidenceMap) {
		String value = ft.ft_text;
		CarbohydLinkType carbohydLinkType = CarbohydLinkType.UNKNOWN;
		String description = "";
		String linkedSugar ="";
		if(!Strings.isNullOrEmpty(value)) {
			Matcher matcher = ftLineConverterUtil.CARBOHYD_DESC_PATTERN.matcher(value);
			if(matcher.matches()) {
				String linkType = matcher.group(1);
				carbohydLinkType = CarbohydLinkType.typeof(linkType);
				linkedSugar =  matcher.group(2);
				if(matcher.group(5) !=null) {
					description = matcher.group(5);
				}
			}
		}
		return factory.buildCarbohydFeature(location, description, ft.ftId, carbohydLinkType, linkedSugar);
	}


	private Feature convertVarSeqFeature(FeatureType type, FeatureLocation location, FtLineObject.FT ft,
			Map<Object, List<Evidence>> evidenceMap) {
		String value = ft.ft_text;
		
		String orginalSequence="";
		List<String> alternativeSequences =new ArrayList<>();
		List<String> isoforms = new ArrayList<>();
		Matcher matcher = ftLineConverterUtil.VAR_SEQ_DESC_PATTERN.matcher(value);
		if(!Strings.isNullOrEmpty(value ) && matcher.matches()) {
			String val1 = matcher.group(1);
			if(!MISSING.equals(val1)) {
				orginalSequence =matcher.group(3);
				alternativeSequences.add(matcher.group(5));
			}
			
			String[] tokens = matcher.group(8).substring("isoform ".length()).split(ISOFORM_REGEX);
			isoforms = Arrays.asList(tokens);
		}
		
		return factory.buildVarSeqFeature(location, orginalSequence, alternativeSequences, isoforms,
				factory.createFeatureId(ft.ftId));
	}

	private Feature convertVariantFeature(FeatureType type, FeatureLocation location, FtLineObject.FT ft,
			Map<Object, List<Evidence>> evidenceMap) {
		String value = ft.ft_text;
		Matcher matcher = ftLineConverterUtil.VAIANT_DESC_PATTERN.matcher(value);
		String orginalSequence="";
		List<String> alternativeSequences =new ArrayList<>();
		List<String> reports = new ArrayList<>();
		
		if(!Strings.isNullOrEmpty(value ) && matcher.matches()) {
			String val1 = matcher.group(1);
			if(!MISSING.equals(val1)) {
				orginalSequence =matcher.group(3);
				alternativeSequences.add(matcher.group(5));
			}
			if(matcher.group(8) !=null)
			reports.add( matcher.group(8) );
		}
		
		return factory.buildVariantFeature(location, orginalSequence, alternativeSequences,reports,
				factory.createFeatureId(ft.ftId));
	}

	private Feature convertConflictFeature(FeatureType type, FeatureLocation location, FtLineObject.FT ft,
			Map<Object, List<Evidence>> evidenceMap) {
		String value = ft.ft_text;
		Matcher matcher = ftLineConverterUtil.CONFLICT_DESC_PATTERN.matcher(value);
		String orginalSequence="";
		List<String> alternativeSequences =new ArrayList<>();
		List<String> reports = new ArrayList<>();
		
		if(matcher.matches()) {
			String val1 = matcher.group(1);
			if(!MISSING.equals(val1)) {
				orginalSequence =matcher.group(3);
				alternativeSequences.add(matcher.group(5));
			}
			String regex = CONFLICT_REGEX ;
			String[] tokens = matcher.group(8).split(regex);
			
			reports = Arrays.asList(tokens);
		}
		
		return factory.buildConflictFeature(location, orginalSequence, alternativeSequences, reports);
	}

	private Feature convertMutagenFeature(FeatureType type, FeatureLocation location, FtLineObject.FT ft,
			Map<Object, List<Evidence>> evidenceMap) {
		String value = ft.ft_text;
		Matcher matcher = ftLineConverterUtil.MUTAGEN_DESC_PATTERN.matcher(value);
		String orginalSequence="";
		List<String> alternativeSequences =new ArrayList<>();
		List<String> reports = new ArrayList<>();
		
		if(!Strings.isNullOrEmpty(value ) && matcher.matches()) {
			String val1 = matcher.group(1);
			if(!MISSING.equals(val1)) {
				orginalSequence =matcher.group(3).trim();
				String val =matcher.group(5).trim();
				String [] tokens =val.split("\\,");
				alternativeSequences = Arrays.asList(tokens);
			}
			reports.add (matcher.group(8));
		}
		
		return factory.buildMutagenFeature(location, orginalSequence, alternativeSequences, reports);
	}

	private Feature convertSimpleFeature(FeatureType type, FeatureLocation location, FtLineObject.FT ft,
			Map<Object, List<Evidence>> evidenceMap) {
		return factory.buildSimpleFeatureWithFeatureId(type, location, ft.ft_text, ft.ftId);
	}

	private FeatureLocation convertFeatureLocation(String locationStart, String locationEnd) {
		Map.Entry<FeatureLocationModifier, Integer> start = convertLocation( locationStart, '<');
		Map.Entry<FeatureLocationModifier, Integer> end = convertLocation( locationEnd, '>');
	
		return factory.createFeatureLocation(start.getValue(), end.getValue(), start.getKey(), end.getKey());
	}
	
	private Map.Entry<FeatureLocationModifier, Integer> convertLocation(String locationStart, char outSymbol){
		FeatureLocationModifier startModifier = FeatureLocationModifier.EXACT;

		Integer start = -1;
		if ((locationStart == null) || locationStart.trim().isEmpty()) {
			startModifier = FeatureLocationModifier.UNKOWN;
		} else {
			locationStart = locationStart.trim();
			char c = locationStart.charAt(0);
			if (c == '?') {
				if (locationStart.length() > 1) {
					String val = locationStart.substring(1).trim();
					if (val.isEmpty()) {
						startModifier = FeatureLocationModifier.UNKOWN;
					} else {
						int value = Integer.parseInt(val);
						if (value == -1) {
							startModifier = FeatureLocationModifier.UNKOWN;
						} else {
							startModifier = FeatureLocationModifier.UNSURE;
							start = value;
						}
					}

				} else {
					startModifier = FeatureLocationModifier.UNKOWN;
				}
			} else if (c == outSymbol) {
				startModifier = FeatureLocationModifier.OUTSIDE_KNOWN_SEQUENCE;
				if (locationStart.length() > 1) {
					String val = locationStart.substring(1);
					start = Integer.parseInt(val.trim());
				}
			} else {
				startModifier = FeatureLocationModifier.EXACT;
				start = Integer.parseInt(locationStart);
			}
		}
		return new AbstractMap.SimpleEntry<>(startModifier, start);
	}

	private FeatureType convert(FtLineObject.FTType type) {
		FeatureType ftype = FeatureType.ACT_SITE;
		switch (type) {
		case INIT_MET:
			ftype = FeatureType.INIT_MET;
			break;
		case SIGNAL:
			ftype = FeatureType.SIGNAL;
			break;
		case PROPEP:
			ftype = FeatureType.PROPEP;
			break;
		case TRANSIT:
			ftype = FeatureType.TRANSIT;
			break;
		case CHAIN:
			ftype = FeatureType.CHAIN;
			break;
		case PEPTIDE:
			ftype = FeatureType.PEPTIDE;
			break;
		case TOPO_DOM:
			ftype = FeatureType.TOPO_DOM;
			break;
		case TRANSMEM:
			ftype = FeatureType.TRANSMEM;
			break;
		case INTRAMEM:
			ftype = FeatureType.INTRAMEM;
			break;
		case DOMAIN:
			ftype = FeatureType.DOMAIN;
			break;
		case REPEAT:
			ftype = FeatureType.REPEAT;
			break;
		case CA_BIND:
			ftype = FeatureType.CA_BIND;
			break;
		case ZN_FING:
			ftype = FeatureType.ZN_FING;
			break;
		case DNA_BIND:
			ftype = FeatureType.DNA_BIND;
			break;
		case NP_BIND:
			ftype = FeatureType.NP_BIND;
			break;
		case REGION:
			ftype = FeatureType.REGION;
			break;
		case COILED:
			ftype = FeatureType.COILED;
			break;
		case MOTIF:
			ftype = FeatureType.MOTIF;
			break;
		case COMPBIAS:
			ftype = FeatureType.COMPBIAS;
			break;
		case ACT_SITE:
			ftype = FeatureType.ACT_SITE;
			break;
		case METAL:
			ftype = FeatureType.METAL;
			break;
		case BINDING:
			ftype = FeatureType.BINDING;
			break;
		case SITE:
			ftype = FeatureType.SITE;
			break;
		case NON_STD:
			ftype = FeatureType.NON_STD;
			break;
		case MOD_RES:
			ftype = FeatureType.MOD_RES;
			break;
		case LIPID:
			ftype = FeatureType.LIPID;
			break;
		case CARBOHYD:
			ftype = FeatureType.CARBOHYD;
			break;
		case DISULFID:
			ftype = FeatureType.DISULFID;
			break;
		case CROSSLNK:
			ftype = FeatureType.CROSSLNK;
			break;
		case VAR_SEQ:
			ftype = FeatureType.VAR_SEQ;
			break;
		case VARIANT:
			ftype = FeatureType.VARIANT;
			break;
		case MUTAGEN:
			ftype = FeatureType.MUTAGEN;
			break;
		case UNSURE:
			ftype = FeatureType.UNSURE;
			break;
		case CONFLICT:
			ftype = FeatureType.CONFLICT;
			break;
		case NON_CONS:
			ftype = FeatureType.NON_CONS;
			break;
		case NON_TER:
			ftype = FeatureType.NON_TER;
			break;
		case HELIX:
			ftype = FeatureType.HELIX;
			break;
		case STRAND:
			ftype = FeatureType.STRAND;
			break;
		case TURN:
			ftype = FeatureType.TURN;
			break;
		}
		return ftype;
	}
}
