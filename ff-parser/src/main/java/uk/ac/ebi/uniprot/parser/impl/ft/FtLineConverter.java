package uk.ac.ebi.uniprot.parser.impl.ft;

import com.google.common.base.Strings;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.PositionModifier;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.FeatureFactory;
import uk.ac.ebi.uniprot.domain.uniprot.feature.*;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.parser.impl.EvidenceConverterHelper;

import java.util.*;
import java.util.regex.Matcher;

public class FtLineConverter extends EvidenceCollector
		implements Converter<FtLineObject, List<Feature>> {
	private static final String CONFLICT_REGEX = ", | and ";
	private static final String MISSING = "Missing";
	private final FeatureFactory factory = FeatureFactory.INSTANCE;
	   private static final String ISOFORM_REGEX =", isoform | and isoform ";

	@Override
	public List< Feature> convert(FtLineObject f) {
		List< Feature> features = new ArrayList<>();
		Map<Object, List<Evidence>> evidenceMap = EvidenceConverterHelper.convert(f.getEvidenceInfo());
		this.addAll(evidenceMap.values());
		for (FtLineObject.FT ft : f.fts) {
			FeatureType featureType = convert(ft.type);
			Range location = convertFeatureLocation(ft.location_start, ft.location_end);
			Feature feature = convertFeature(featureType, location, ft, evidenceMap);
			features.add(feature);

		}
		return features;
	}

	private Feature convertFeature(FeatureType type, Range location, FtLineObject.FT ft,
			Map<Object, List<Evidence>> evidenceMap) {
		List<Evidence> evidences = evidenceMap.get(ft);
		switch (type) {
		case CARBOHYD:
			return convertCarbohydFeature(type, location, ft, evidences);
		case VAR_SEQ:
			return convertVarSeqFeature(type, location, ft, evidences);
		case VARIANT:
			return convertVariantFeature(type, location, ft, evidences);
		case CONFLICT:
			return convertConflictFeature(type, location, ft, evidences);
		case MUTAGEN:
			return convertMutagenFeature(type, location, ft, evidences);
		default:
			return convertSimpleFeature(type, location, ft, evidences);

		}
	}

	private Feature convertCarbohydFeature(FeatureType type, Range location, FtLineObject.FT ft,
			 List<Evidence> evidences) {
		String value = ft.ft_text;
//		CarbohydLinkType carbohydLinkType = CarbohydLinkType.UNKNOWN;
//		String description = "";
//		String linkedSugar ="";
//		if(!Strings.isNullOrEmpty(value)) {
//			Matcher matcher = ftLineConverterUtil.CARBOHYD_DESC_PATTERN.matcher(value);
//			if(matcher.matches()) {
//				String linkType = matcher.group(1);
//				carbohydLinkType = CarbohydLinkType.typeof(linkType);
//				linkedSugar =  matcher.group(2);
//				if(matcher.group(5) !=null) {
//					description = matcher.group(5);
//				}
//			}
		
		
//		}
		return factory.createFeature(type, location, value, factory.createFeatureId(ft.ftId), evidences);
	}


	private Feature convertVarSeqFeature(FeatureType type, Range location, FtLineObject.FT ft,
			 List<Evidence> evidences) {
		String value = ft.ft_text;
		value = fixVarSeqSpace(value);
		String originalSequence="";
		List<String> alternativeSequences =new ArrayList<>();
		List<String> isoforms = new ArrayList<>();
		Matcher matcher = FtLineConverterUtil.VAR_SEQ_DESC_PATTERN.matcher(value);
		String description ="";
		if(!Strings.isNullOrEmpty(value ) && matcher.matches()) {
			String val1 = matcher.group(1);
			if(!MISSING.equals(val1)) {
				originalSequence =matcher.group(3).replaceAll(" ", "");
				alternativeSequences.add(matcher.group(7).replaceAll(" ", ""));
			}
			 description ="in "+ matcher.group(10);
			String[] tokens = matcher.group(10).substring("isoform ".length()).split(ISOFORM_REGEX);
			for(String token: tokens) {
				int index = token.indexOf(' ');
				if((index>0) && (token.charAt(index-1)== '-')) {
					String temp = token.substring(0, index) + token.substring(index+1);
					description = description.replace(token, temp);
					
					token = temp;
				}
				isoforms.add(token);
			}
		}
		AlternativeSequence altSeq =factory.createAlternativeSequence(originalSequence, alternativeSequences);
		//	factory.createReport(isoforms));
		
		return factory.createFeature( type,  location, description, factory.createFeatureId(ft.ftId),
				altSeq,  evidences);
	}

	private String fixVarSeqSpace(String value) {
		String temp =value;
		int index = temp.indexOf("(in isoform");
		if(index !=-1) {
			if(temp.charAt(index -1) != ' ') {
				String val = temp.substring(0, index) + " " + temp.substring(index);
				temp =val;
			}
		}
		int spaceLocation =-1;
		do {
			 spaceLocation = getSpaceLocation(temp, index);
			 if(spaceLocation ==-1)
				 break;
			 String val = temp.substring(0, spaceLocation) + temp.substring(spaceLocation+1);
			 temp = val;
			 index -=1;
		}while (spaceLocation !=-1);
		
		return temp;
	}
	
	private int getSpaceLocation(String value, int index) {
		for (int i =0 ;i<index; i ++) {
			if(isCapital(value.charAt(i))) {
				if((i+2)<index) {
					if((value.charAt(i+1) ==' ') && isCapital(value.charAt(i+2))){
						return i+1;
					}
				}
			}
		}
		return -1;
	}
	
	private boolean isCapital(char c) {
		return c>='A' && c <='Z';
	}
	public boolean isSequenceLetter(String se) {
		for (int i = 0; i < se.length(); i++) {
			if (se.charAt(i) > 'Z' || se.charAt(i) < 'A')
				return false;
		}
		return true;
	}

	private Feature convertVariantFeature(FeatureType type, Range location, FtLineObject.FT ft,
			List<Evidence> evidences) {
		String value = ft.ft_text;
		Matcher matcher = FtLineConverterUtil.VAIANT_DESC_PATTERN.matcher(value);
		String originalSequence="";
		List<String> alternativeSequences =new ArrayList<>();
		List<String> reports = new ArrayList<>();
		String description ="";
		if(!Strings.isNullOrEmpty(value ) && matcher.matches()) {
			String val1 = matcher.group(1);
			if(!MISSING.equals(val1)) {
				originalSequence =matcher.group(3);
				alternativeSequences.add(matcher.group(5));
			}
			if(matcher.group(9) !=null)
			description =matcher.group(9) ;
		}
		
		AlternativeSequence altSeq =factory.createAlternativeSequence(originalSequence, alternativeSequences);
		//		factory.createReport(reports));
		DBCrossReference<FeatureXDbType> dbXref =null;
		return factory.createFeature( type,  location, description,factory.createFeatureId(ft.ftId),
				altSeq, dbXref,  evidences);

	}

	private Feature convertConflictFeature(FeatureType type, Range location, FtLineObject.FT ft,
			 List<Evidence> evidences) {
		String value = ft.ft_text;
		Matcher matcher = FtLineConverterUtil.CONFLICT_DESC_PATTERN.matcher(value);
		String originalSequence="";
		List<String> alternativeSequences =new ArrayList<>();
		List<String> reports = new ArrayList<>();
		String description ="";
		if(matcher.matches()) {
			String val1 = matcher.group(1);
			if(!MISSING.equals(val1)) {
				originalSequence =matcher.group(3);
				alternativeSequences.add(matcher.group(7));
			}
			String regex = CONFLICT_REGEX ;
			
			String[] tokens = matcher.group(10).split(regex);
			description ="in Ref. "+  matcher.group(10);
			reports = Arrays.asList(tokens);
		}
		AlternativeSequence altSeq =factory.createAlternativeSequence(originalSequence, alternativeSequences);
			//	factory.createReport(reports));
		
		return factory.createFeature( type,  location, description, null,
				altSeq,  evidences);
	}

	private Feature convertMutagenFeature(FeatureType type, Range location, FtLineObject.FT ft,
			 List<Evidence> evidences) {
		String value = ft.ft_text;
		Matcher matcher = FtLineConverterUtil.MUTAGEN_DESC_PATTERN.matcher(value);
		String originalSequence="";
		List<String> alternativeSequences =new ArrayList<>();
		List<String> reports = new ArrayList<>();
		String description ="";
		if(!Strings.isNullOrEmpty(value ) && matcher.matches()) {
			String val1 = matcher.group(1);
			if(!MISSING.equals(val1)) {
				originalSequence =matcher.group(3).trim();
				String val =matcher.group(5).trim();
				String [] tokens =val.split("\\,");
				alternativeSequences = Arrays.asList(tokens);
			}
			reports.add (matcher.group(8));
			description = matcher.group(8);
		}
		AlternativeSequence altSeq =factory.createAlternativeSequence(originalSequence, alternativeSequences);
			//	factory.createReport(reports));
		 	return factory.createFeature( type,  location, description, null,
				altSeq,  evidences);
	}

	private Feature convertSimpleFeature(FeatureType type, Range location, FtLineObject.FT ft,
			List<Evidence> evidences) {
		FeatureId featureId =null;
		if(!Strings.isNullOrEmpty(ft.ftId)) {
			featureId =factory.createFeatureId(ft.ftId);
		}
		
		return factory.createFeature(type, location, ft.ft_text, featureId, evidences);
	}

	private Range convertFeatureLocation(String locationStart, String locationEnd) {
		Map.Entry<PositionModifier, Integer> start = convertLocation( locationStart, '<');
		Map.Entry<PositionModifier, Integer> end = convertLocation( locationEnd, '>');
	
		return new Range(start.getValue(), end.getValue(), start.getKey(), end.getKey());
	}
	
	private Map.Entry<PositionModifier, Integer> convertLocation(String locationStart, char outSymbol){
		PositionModifier startModifier = PositionModifier.EXACT;

		Integer start = -1;
		if ((locationStart == null) || locationStart.trim().isEmpty()) {
			startModifier = PositionModifier.UNKOWN;
		} else {
			locationStart = locationStart.trim();
			char c = locationStart.charAt(0);
			if (c == '?') {
				if (locationStart.length() > 1) {
					String val = locationStart.substring(1).trim();
					if (val.isEmpty()) {
						startModifier = PositionModifier.UNKOWN;
					} else {
						int value = Integer.parseInt(val);
						if (value == -1) {
							startModifier = PositionModifier.UNKOWN;
						} else {
							startModifier = PositionModifier.UNSURE;
							start = value;
						}
					}

				} else {
					startModifier = PositionModifier.UNKOWN;
				}
			} else if (c == outSymbol) {
				startModifier = PositionModifier.OUTSIDE;
				if (locationStart.length() > 1) {
					String val = locationStart.substring(1);
					start = Integer.parseInt(val.trim());
				}
			} else {
				startModifier = PositionModifier.EXACT;
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
