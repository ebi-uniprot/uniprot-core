package org.uniprot.core.flatfile.parser.impl.de;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.impl.EvidenceCollector;
import org.uniprot.core.flatfile.parser.impl.EvidenceConverterHelper;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject.FlagType;
import org.uniprot.core.uniprotkb.description.*;
import org.uniprot.core.uniprotkb.description.impl.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;

import com.google.common.base.Strings;

public class DeLineConverter extends EvidenceCollector
        implements Converter<DeLineObject, ProteinDescription> {

    @Override
    public ProteinDescription convert(DeLineObject f) {
        Map<Object, List<Evidence>> evidenceMap =
                EvidenceConverterHelper.convert(f.getEvidenceInfo());
        this.addAll(evidenceMap.values());
        // ProteinDescription pd = factory.buildProteinDescription();
        ProteinName recName = null;
        if (f.getRecName() != null) {
            recName = convertRecName(f.getRecName(), evidenceMap);
        }
        List<ProteinName> altNames =
                f.getAltNames().stream()
                        .map(val -> convertAltName(val, evidenceMap))
                        .collect(Collectors.toList());

        List<Name> cdAntigenNames =
                f.getAltCdAntigens().stream()
                        .map(val -> convertName(val, evidenceMap))
                        .collect(Collectors.toList());

        List<Name> innNames =
                f.getAltInns().stream()
                        .map(val -> convertName(val, evidenceMap))
                        .collect(Collectors.toList());
        Name biotechName = null;
        if (!Strings.isNullOrEmpty(f.getAltBiotech())) {
            biotechName = convertName(f.getAltBiotech(), evidenceMap);
        }
        Name allergenName = null;
        if (!Strings.isNullOrEmpty(f.getAltAllergen())) {
            allergenName = convertName(f.getAltAllergen(), evidenceMap);
        }
        List<ProteinSubName> subNames =
                f.getSubNames().stream()
                        .map(val -> convertSubmissionName(val, evidenceMap))
                        .collect(Collectors.toList());

        List<ProteinSection> contained =
                f.getContainedNames().stream()
                        .map(val -> convertProteinNameSection(val, evidenceMap))
                        .collect(Collectors.toList());

        List<ProteinSection> included =
                f.getIncludedNames().stream()
                        .map(val -> convertProteinNameSection(val, evidenceMap))
                        .collect(Collectors.toList());

        org.uniprot.core.uniprotkb.description.FlagType flag = createFlag(f.getFlags());
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        builder.recommendedName(recName)
                .submissionNamesSet(subNames)
                .alternativeNamesSet(altNames)
                .containsSet(contained)
                .includesSet(included)
                .flag(flag)
                .cdAntigenNamesSet(cdAntigenNames)
                .innNamesSet(innNames)
                .allergenName(allergenName)
                .biotechName(biotechName);

        return builder.build();
    }

    private org.uniprot.core.uniprotkb.description.FlagType createFlag(List<FlagType> flags) {
        if (flags.contains(FlagType.Fragment)) {
            if (flags.contains(FlagType.Precursor)) {
                return org.uniprot.core.uniprotkb.description.FlagType.FRAGMENT_PRECURSOR;
            } else {
                return org.uniprot.core.uniprotkb.description.FlagType.FRAGMENT;
            }
        } else if (flags.contains(FlagType.Fragments)) {
            if (flags.contains(FlagType.Precursor)) {
                return org.uniprot.core.uniprotkb.description.FlagType.FRAGMENTS_PRECURSOR;
            } else {
                return org.uniprot.core.uniprotkb.description.FlagType.FRAGMENTS;
            }
        } else if (flags.contains(FlagType.Precursor)) {
            return org.uniprot.core.uniprotkb.description.FlagType.PRECURSOR;
        } else if (flags.contains(FlagType.Precursor_Fragment)) {
            return org.uniprot.core.uniprotkb.description.FlagType.FRAGMENT_PRECURSOR;
        } else {
            return null;
        }
    }

    private ProteinSection convertProteinNameSection(
            DeLineObject.NameBlock nameBlock, Map<Object, List<Evidence>> evidenceMap) {
        ProteinName recName = null;
        if (nameBlock.getRecName() != null) {
            recName = convertRecName(nameBlock.getRecName(), evidenceMap);
        }

        List<ProteinName> altNames =
                nameBlock.getAltNames().stream()
                        .map(val -> convertAltName(val, evidenceMap))
                        .collect(Collectors.toList());

        List<Name> cdAntigenNames =
                nameBlock.getAltCdAntigens().stream()
                        .map(val -> convertName(val, evidenceMap))
                        .collect(Collectors.toList());

        List<Name> innNames =
                nameBlock.getAltInns().stream()
                        .map(val -> convertName(val, evidenceMap))
                        .collect(Collectors.toList());
        Name biotechName = null;
        if (!Strings.isNullOrEmpty(nameBlock.getAltBiotech())) {
            biotechName = convertName(nameBlock.getAltBiotech(), evidenceMap);
        }
        Name allergenName = null;
        if (!Strings.isNullOrEmpty(nameBlock.getAltAllergen())) {
            allergenName = convertName(nameBlock.getAltAllergen(), evidenceMap);
        }

        return new ProteinSectionBuilder()
                .recommendedName(recName)
                .alternativeNamesSet(altNames)
                .cdAntigenNamesSet(cdAntigenNames)
                .innNamesSet(innNames)
                .allergenName(allergenName)
                .biotechName(biotechName)
                .build();
    }

    private Name convertName(String val, Map<Object, List<Evidence>> evidenceMap) {
        NameBuilder builder = new NameBuilder();
        return builder.value(val).evidencesSet(evidenceMap.get(val)).build();
    }

    private ProteinName convertAltName(
            DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
        Name fullName = null;
        if ((val.getFullName() != null) || (!val.getFullName().isEmpty())) {
            fullName =
                    new NameBuilder()
                            .value(val.getFullName())
                            .evidencesSet(evidenceMap.get(val.getFullName()))
                            .build();
        }
        List<Name> shortNames =
                val.getShortNames().stream()
                        .map(
                                shortName ->
                                        new NameBuilder()
                                                .value(shortName)
                                                .evidencesSet(evidenceMap.get(shortName))
                                                .build())
                        .collect(Collectors.toList());
        List<EC> ecNumbers =
                val.getEcs().stream()
                        .map(
                                ec -> {
                                    DeLineObject.ECEvidence ecEvidence =
                                            new DeLineObject.ECEvidence();
                                    ecEvidence.setEcValue(ec);
                                    ecEvidence.setNameECBelong(val);
                                    return new ECBuilder()
                                            .value(ec)
                                            .evidencesSet(evidenceMap.get(ecEvidence))
                                            .build();
                                })
                        .collect(Collectors.toList());
        return new ProteinNameBuilder()
                .fullName(fullName)
                .shortNamesSet(shortNames)
                .ecNumbersSet(ecNumbers)
                .build();
    }

    private ProteinName convertRecName(
            DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
        Name fullName = null;
        if ((val.getFullName() != null) || (!val.getFullName().isEmpty())) {
            fullName =
                    new NameBuilder()
                            .value(val.getFullName())
                            .evidencesSet(evidenceMap.get(val.getFullName()))
                            .build();
        }
        List<Name> shortNames =
                val.getShortNames().stream()
                        .map(
                                shortName ->
                                        new NameBuilder()
                                                .value(shortName)
                                                .evidencesSet(evidenceMap.get(shortName))
                                                .build())
                        .collect(Collectors.toList());
        List<EC> ecNumbers =
                val.getEcs().stream()
                        .map(ec -> createEC(ec, val, evidenceMap))
                        .collect(Collectors.toList());
        return new ProteinNameBuilder()
                .fullName(fullName)
                .shortNamesSet(shortNames)
                .ecNumbersSet(ecNumbers)
                .build();
    }

    private EC createEC(String ec, DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
        DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
        ecEvidence.setEcValue(ec);
        ecEvidence.setNameECBelong(val);
        List<Evidence> evidences = evidenceMap.get(ecEvidence);
        return new ECBuilder().value(ec).evidencesSet(evidences).build();
    }

    private ProteinSubName convertSubmissionName(
            DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
        Name fullName = null;
        if ((val.getFullName() != null) || (!val.getFullName().isEmpty())) {
            fullName =
                    new NameBuilder()
                            .value(val.getFullName())
                            .evidencesSet(evidenceMap.get(val.getFullName()))
                            .build();
        }
        // List<Name> shortNames =val.shortNames.stream()
        // .map(shortName ->factory.createProteinName(shortName,
        // evidenceMap.get(shortName)) )
        // .collect(Collectors.toList());
        List<EC> ecNumbers =
                val.getEcs().stream()
                        .map(
                                ec -> {
                                    DeLineObject.ECEvidence ecEvidence =
                                            new DeLineObject.ECEvidence();
                                    ecEvidence.setEcValue(ec);
                                    ecEvidence.setNameECBelong(val);
                                    return new ECBuilder()
                                            .value(ec)
                                            .evidencesSet(evidenceMap.get(ecEvidence))
                                            .build();
                                })
                        .collect(Collectors.toList());
        return new ProteinSubNameBuilder().fullName(fullName).ecNumbersSet(ecNumbers).build();
    }
}
