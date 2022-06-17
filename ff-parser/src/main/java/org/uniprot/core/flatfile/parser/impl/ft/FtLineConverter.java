package org.uniprot.core.flatfile.parser.impl.ft;

import java.util.*;
import java.util.regex.Matcher;

import org.uniprot.core.CrossReference;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.impl.EvidenceCollector;
import org.uniprot.core.flatfile.parser.impl.EvidenceConverterHelper;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureDatabase;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceBuilder;
import org.uniprot.core.uniprotkb.feature.impl.LigandBuilder;
import org.uniprot.core.uniprotkb.feature.impl.LigandPartBuilder;
import org.uniprot.core.uniprotkb.feature.impl.UniProtKBFeatureBuilder;

import com.google.common.base.Strings;

public class FtLineConverter extends EvidenceCollector
        implements Converter<FtLineObject, List<UniProtKBFeature>> {
    /** */
    private static final long serialVersionUID = -5497576148432940559L;

    private static final String CONFLICT_REGEX = ", | and ";
    private static final String MISSING = "Missing";
    private static final String ISOFORM_REGEX = ", isoform | and isoform ";

    @Override
    public List<UniProtKBFeature> convert(FtLineObject f) {
        List<UniProtKBFeature> features = new ArrayList<>();
        Map<Object, List<Evidence>> evidenceMap =
                EvidenceConverterHelper.convert(f.getEvidenceInfo());
        this.addAll(evidenceMap.values());
        for (FtLineObject.FT ft : f.getFts()) {
            UniprotKBFeatureType featureType = convert(ft.getType());

            FeatureLocation location =
                    convertFeatureLocation(
                            ft.getSequence(), ft.getLocationStart(), ft.getLocationEnd());
            UniProtKBFeature feature = convertFeature(featureType, location, ft, evidenceMap);
            features.add(feature);
        }
        return features;
    }

    private UniProtKBFeature convertFeature(
            UniprotKBFeatureType type,
            FeatureLocation location,
            FtLineObject.FT ft,
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
            case BINDING:
                return convertBindingFeature(type, location, ft, evidences);
            default:
                return convertSimpleFeature(type, location, ft, evidences);
        }
    }

    private UniProtKBFeature convertCarbohydFeature(
            UniprotKBFeatureType type,
            FeatureLocation location,
            FtLineObject.FT ft,
            List<Evidence> evidences) {
        return new UniProtKBFeatureBuilder()
                .type(type)
                .location(location)
                .featureId(ft.getFtId())
                .evidencesSet(evidences)
                .description(ft.getFtText())
                .build();
    }

    private UniProtKBFeature convertVarSeqFeature(
            UniprotKBFeatureType type,
            FeatureLocation location,
            FtLineObject.FT ft,
            List<Evidence> evidences) {
        String value = ft.getFtText();
        value = fixVarSeqSpace(value);
        String originalSequence = "";
        List<String> alternativeSequences = new ArrayList<>();
        List<String> isoforms = new ArrayList<>();
        Matcher matcher = FtLineConverterUtil.VAR_SEQ_DESC_PATTERN.matcher(value);
        String description = "";
        if (!Strings.isNullOrEmpty(value) && matcher.matches()) {
            String val1 = matcher.group(1);
            if (!MISSING.equals(val1)) {
                originalSequence = matcher.group(3).replaceAll(" ", "");
                alternativeSequences.add(matcher.group(7).replaceAll(" ", ""));
            }
            description = "in " + matcher.group(10);
            String[] tokens = matcher.group(10).substring("isoform ".length()).split(ISOFORM_REGEX);
            for (String token : tokens) {
                int index = token.indexOf(' ');
                if ((index > 0) && (token.charAt(index - 1) == '-')) {
                    String temp = token.substring(0, index) + token.substring(index + 1);
                    description = description.replace(token, temp);

                    token = temp;
                }
                isoforms.add(token);
            }
        }
        AlternativeSequence altSeq =
                new AlternativeSequenceBuilder()
                        .original(originalSequence)
                        .alternativeSequencesSet(alternativeSequences)
                        .build();
        //	factory.createReport(isoforms));

        return new UniProtKBFeatureBuilder()
                .type(type)
                .location(location)
                .description(description)
                .featureId(ft.getFtId())
                .alternativeSequence(altSeq)
                .evidencesSet(evidences)
                .build();
    }

    private String fixVarSeqSpace(String value) {
        String temp = value;
        int index = temp.indexOf("(in isoform");
        if (index != -1) {
            if (temp.charAt(index - 1) != ' ') {
                String val = temp.substring(0, index) + " " + temp.substring(index);
                temp = val;
            }
        }
        int spaceLocation = -1;
        do {
            spaceLocation = getSpaceLocation(temp, index);
            if (spaceLocation == -1) break;
            String val = temp.substring(0, spaceLocation) + temp.substring(spaceLocation + 1);
            temp = val;
            index -= 1;
        } while (spaceLocation != -1);

        return temp;
    }

    private int getSpaceLocation(String value, int index) {
        for (int i = 0; i < index; i++) {
            if (isCapital(value.charAt(i))) {
                if ((i + 2) < index) {
                    if ((value.charAt(i + 1) == ' ') && isCapital(value.charAt(i + 2))) {
                        return i + 1;
                    }
                }
            }
        }
        return -1;
    }

    private boolean isCapital(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public boolean isSequenceLetter(String se) {
        for (int i = 0; i < se.length(); i++) {
            if (se.charAt(i) > 'Z' || se.charAt(i) < 'A') return false;
        }
        return true;
    }

    private UniProtKBFeature convertVariantFeature(
            UniprotKBFeatureType type,
            FeatureLocation location,
            FtLineObject.FT ft,
            List<Evidence> evidences) {
        String value = ft.getFtText();
        Matcher matcher = FtLineConverterUtil.VAIANT_DESC_PATTERN.matcher(value);
        String originalSequence = "";
        List<String> alternativeSequences = new ArrayList<>();
        //    List<String> reports = new ArrayList<>();
        String description = "";
        if (!Strings.isNullOrEmpty(value) && matcher.matches()) {
            String val1 = matcher.group(1);
            if (!MISSING.equals(val1)) {
                originalSequence = matcher.group(3);
                alternativeSequences.add(matcher.group(5));
            }
            if (matcher.group(9) != null) description = matcher.group(9);
        }

        AlternativeSequence altSeq =
                new AlternativeSequenceBuilder()
                        .original(originalSequence)
                        .alternativeSequencesSet(alternativeSequences)
                        .build();
        //		factory.createReport(reports));
        CrossReference<UniprotKBFeatureDatabase> dbXref = null;
        return new UniProtKBFeatureBuilder()
                .type(type)
                .location(location)
                .description(description)
                .featureId(ft.getFtId())
                .alternativeSequence(altSeq)
                .evidencesSet(evidences)
                .featureCrossReference(dbXref)
                .build();
    }

    private UniProtKBFeature convertConflictFeature(
            UniprotKBFeatureType type,
            FeatureLocation location,
            FtLineObject.FT ft,
            List<Evidence> evidences) {
        String value = ft.getFtText();
        Matcher matcher = FtLineConverterUtil.CONFLICT_DESC_PATTERN.matcher(value);
        String originalSequence = "";
        List<String> alternativeSequences = new ArrayList<>();
        List<String> reports = new ArrayList<>();
        String description = "";
        if (matcher.matches()) {
            String val1 = matcher.group(1);
            if (!MISSING.equals(val1)) {
                originalSequence = matcher.group(3);
                alternativeSequences.add(matcher.group(7));
            }
            String regex = CONFLICT_REGEX;

            String[] tokens = matcher.group(10).split(regex);
            description = "in Ref. " + matcher.group(10);
            reports = Arrays.asList(tokens);
        }
        AlternativeSequence altSeq =
                new AlternativeSequenceBuilder()
                        .original(originalSequence)
                        .alternativeSequencesSet(alternativeSequences)
                        .build();
        ;
        //	factory.createReport(reports));

        return new UniProtKBFeatureBuilder()
                .type(type)
                .location(location)
                .evidencesSet(evidences)
                .alternativeSequence(altSeq)
                .description(description)
                .build();
    }

    private UniProtKBFeature convertMutagenFeature(
            UniprotKBFeatureType type,
            FeatureLocation location,
            FtLineObject.FT ft,
            List<Evidence> evidences) {
        String value = ft.getFtText();
        Matcher matcher = FtLineConverterUtil.MUTAGEN_DESC_PATTERN.matcher(value);
        String originalSequence = "";
        List<String> alternativeSequences = new ArrayList<>();
        List<String> reports = new ArrayList<>();
        String description = "";
        if (!Strings.isNullOrEmpty(value) && matcher.matches()) {
            String val1 = matcher.group(1);
            if (!MISSING.equals(val1)) {
                originalSequence = matcher.group(3).trim();
                String val = matcher.group(5).trim();
                String[] tokens = val.split("\\,");
                alternativeSequences = Arrays.asList(tokens);
            }
            reports.add(matcher.group(8));
            description = matcher.group(8);
        }
        AlternativeSequence altSeq =
                new AlternativeSequenceBuilder()
                        .original(originalSequence)
                        .alternativeSequencesSet(alternativeSequences)
                        .build();
        //	factory.createReport(reports));
        return new UniProtKBFeatureBuilder()
                .type(type)
                .location(location)
                //                .featureId(ft.ftId)
                .evidencesSet(evidences)
                .alternativeSequence(altSeq)
                .description(description)
                .build();
    }
    
    private UniProtKBFeature convertSimpleFeature(
            UniprotKBFeatureType type,
            FeatureLocation location,
            FtLineObject.FT ft,
            List<Evidence> evidences) {
        UniProtKBFeatureBuilder featureBuilder =
                new UniProtKBFeatureBuilder()
                        .type(type)
                        .location(location)
                        .evidencesSet(evidences)
                        .description(ft.getFtText());

        if (!Strings.isNullOrEmpty(ft.getFtId())) {
            featureBuilder.featureId(ft.getFtId());
        }

        return featureBuilder.build();
    }

    private UniProtKBFeature convertBindingFeature(
            UniprotKBFeatureType type,
            FeatureLocation location,
            FtLineObject.FT ft,
            List<Evidence> evidences) {
        UniProtKBFeatureBuilder featureBuilder =
                new UniProtKBFeatureBuilder()
                        .type(type)
                        .location(location)
                        .evidencesSet(evidences)
                        .description(ft.getFtText())
                        .ligand(convertLigand(ft));

        LigandPart ligandPart = convertLigandPart(ft);
        if(ligandPart !=null) {
        	featureBuilder.ligandPart(ligandPart);
        }
        return featureBuilder.build();
    }

    private Ligand convertLigand(FtLineObject.FT ft) {
    	LigandBuilder builder = new LigandBuilder();
    	if (ft.getLigand() !=null) {
    		builder.name(ft.getLigand().getName())
    		.id(ft.getLigand().getId())
    		.label(ft.getLigand().getLabel())
    		.note(ft.getLigand().getNote());
    		
        }
    	return builder.build();
    }
    
    private LigandPart convertLigandPart(FtLineObject.FT ft) {

    		if(ft.getLigandPart() !=null) {
    			LigandPartBuilder partBuilder = new LigandPartBuilder();
    			partBuilder.name(ft.getLigandPart().getName())
        		.id(ft.getLigandPart().getId())
        		.label(ft.getLigandPart().getLabel())
        		.note(ft.getLigandPart().getNote());
    			return partBuilder.build();
    		}
    		else
    			return null;
    }
    
    private FeatureLocation convertFeatureLocation(
            String sequence, String locationStart, String locationEnd) {
        Map.Entry<PositionModifier, Integer> start = convertLocation(locationStart, '<');
        Map.Entry<PositionModifier, Integer> end = convertLocation(locationEnd, '>');

        return new FeatureLocation(
                sequence, start.getValue(), end.getValue(), start.getKey(), end.getKey());
    }

    private Map.Entry<PositionModifier, Integer> convertLocation(
            String locationStart, char outSymbol) {
        PositionModifier startModifier = PositionModifier.EXACT;

        Integer start = -1;
        if ((locationStart == null) || locationStart.trim().isEmpty()) {
            startModifier = PositionModifier.UNKNOWN;
        } else {
            locationStart = locationStart.trim();
            char c = locationStart.charAt(0);
            if (c == '?') {
                if (locationStart.length() > 1) {
                    String val = locationStart.substring(1).trim();
                    if (val.isEmpty()) {
                        startModifier = PositionModifier.UNKNOWN;
                    } else {
                        int value = Integer.parseInt(val);
                        if (value == -1) {
                            startModifier = PositionModifier.UNKNOWN;
                        } else {
                            startModifier = PositionModifier.UNSURE;
                            start = value;
                        }
                    }

                } else {
                    startModifier = PositionModifier.UNKNOWN;
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

    private UniprotKBFeatureType convert(FtLineObject.FTType type) {
        UniprotKBFeatureType ftype = UniprotKBFeatureType.ACT_SITE;
        switch (type) {
            case INIT_MET:
                ftype = UniprotKBFeatureType.INIT_MET;
                break;
            case SIGNAL:
                ftype = UniprotKBFeatureType.SIGNAL;
                break;
            case PROPEP:
                ftype = UniprotKBFeatureType.PROPEP;
                break;
            case TRANSIT:
                ftype = UniprotKBFeatureType.TRANSIT;
                break;
            case CHAIN:
                ftype = UniprotKBFeatureType.CHAIN;
                break;
            case PEPTIDE:
                ftype = UniprotKBFeatureType.PEPTIDE;
                break;
            case TOPO_DOM:
                ftype = UniprotKBFeatureType.TOPO_DOM;
                break;
            case TRANSMEM:
                ftype = UniprotKBFeatureType.TRANSMEM;
                break;
            case INTRAMEM:
                ftype = UniprotKBFeatureType.INTRAMEM;
                break;
            case DOMAIN:
                ftype = UniprotKBFeatureType.DOMAIN;
                break;
            case REPEAT:
                ftype = UniprotKBFeatureType.REPEAT;
                break;
            case CA_BIND:
                ftype = UniprotKBFeatureType.CA_BIND;
                break;
            case ZN_FING:
                ftype = UniprotKBFeatureType.ZN_FING;
                break;
            case DNA_BIND:
                ftype = UniprotKBFeatureType.DNA_BIND;
                break;
            case NP_BIND:
                ftype = UniprotKBFeatureType.NP_BIND;
                break;
            case REGION:
                ftype = UniprotKBFeatureType.REGION;
                break;
            case COILED:
                ftype = UniprotKBFeatureType.COILED;
                break;
            case MOTIF:
                ftype = UniprotKBFeatureType.MOTIF;
                break;
            case COMPBIAS:
                ftype = UniprotKBFeatureType.COMPBIAS;
                break;
            case ACT_SITE:
                ftype = UniprotKBFeatureType.ACT_SITE;
                break;
            case METAL:
                ftype = UniprotKBFeatureType.METAL;
                break;
            case BINDING:
                ftype = UniprotKBFeatureType.BINDING;
                break;
            case SITE:
                ftype = UniprotKBFeatureType.SITE;
                break;
            case NON_STD:
                ftype = UniprotKBFeatureType.NON_STD;
                break;
            case MOD_RES:
                ftype = UniprotKBFeatureType.MOD_RES;
                break;
            case LIPID:
                ftype = UniprotKBFeatureType.LIPID;
                break;
            case CARBOHYD:
                ftype = UniprotKBFeatureType.CARBOHYD;
                break;
            case DISULFID:
                ftype = UniprotKBFeatureType.DISULFID;
                break;
            case CROSSLNK:
                ftype = UniprotKBFeatureType.CROSSLNK;
                break;
            case VAR_SEQ:
                ftype = UniprotKBFeatureType.VAR_SEQ;
                break;
            case VARIANT:
                ftype = UniprotKBFeatureType.VARIANT;
                break;
            case MUTAGEN:
                ftype = UniprotKBFeatureType.MUTAGEN;
                break;
            case UNSURE:
                ftype = UniprotKBFeatureType.UNSURE;
                break;
            case CONFLICT:
                ftype = UniprotKBFeatureType.CONFLICT;
                break;
            case NON_CONS:
                ftype = UniprotKBFeatureType.NON_CONS;
                break;
            case NON_TER:
                ftype = UniprotKBFeatureType.NON_TER;
                break;
            case HELIX:
                ftype = UniprotKBFeatureType.HELIX;
                break;
            case STRAND:
                ftype = UniprotKBFeatureType.STRAND;
                break;
            case TURN:
                ftype = UniprotKBFeatureType.TURN;
                break;
        }
        return ftype;
    }
}
