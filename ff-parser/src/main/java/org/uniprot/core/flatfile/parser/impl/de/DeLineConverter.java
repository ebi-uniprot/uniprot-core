package org.uniprot.core.flatfile.parser.impl.de;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.impl.EvidenceCollector;
import org.uniprot.core.flatfile.parser.impl.EvidenceConverterHelper;
import org.uniprot.core.flatfile.parser.impl.de.DeLineObject.FlagType;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinAltName;
import org.uniprot.core.uniprot.description.ProteinDescription;
import org.uniprot.core.uniprot.description.ProteinRecName;
import org.uniprot.core.uniprot.description.ProteinSection;
import org.uniprot.core.uniprot.description.ProteinSubName;
import org.uniprot.core.uniprot.description.builder.ECBuilder;
import org.uniprot.core.uniprot.description.builder.NameBuilder;
import org.uniprot.core.uniprot.description.builder.ProteinAltNameBuilder;
import org.uniprot.core.uniprot.description.builder.ProteinDescriptionBuilder;
import org.uniprot.core.uniprot.description.builder.ProteinRecNameBuilder;
import org.uniprot.core.uniprot.description.builder.ProteinSectionBuilder;
import org.uniprot.core.uniprot.description.builder.ProteinSubNameBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

import com.google.common.base.Strings;

public class DeLineConverter extends EvidenceCollector
        implements Converter<DeLineObject, ProteinDescription> {

    @Override
    public ProteinDescription convert(DeLineObject f) {
        Map<Object, List<Evidence>> evidenceMap =
                EvidenceConverterHelper.convert(f.getEvidenceInfo());
        this.addAll(evidenceMap.values());
        // ProteinDescription pd = factory.buildProteinDescription();
        ProteinRecName recName = null;
        if (f.recName != null) {
            recName = convertRecName(f.recName, evidenceMap);
        }
        List<ProteinAltName> altNames =
                f.altName.stream()
                        .map(val -> convertAltName(val, evidenceMap))
                        .collect(Collectors.toList());

        List<Name> cdAntigenNames =
                f.alt_CD_antigen.stream()
                        .map(val -> convertName(val, evidenceMap))
                        .collect(Collectors.toList());

        List<Name> innNames =
                f.alt_INN.stream()
                        .map(val -> convertName(val, evidenceMap))
                        .collect(Collectors.toList());
        Name biotechName = null;
        if (!Strings.isNullOrEmpty(f.alt_Biotech)) {
            biotechName = convertName(f.alt_Biotech, evidenceMap);
        }
        Name allergenName = null;
        if (!Strings.isNullOrEmpty(f.alt_Allergen)) {
            allergenName = convertName(f.alt_Allergen, evidenceMap);
        }
        List<ProteinSubName> subNames =
                f.subName.stream()
                        .map(val -> convertSubmissionName(val, evidenceMap))
                        .collect(Collectors.toList());

        List<ProteinSection> contained =
                f.containedNames.stream()
                        .map(val -> convertProteinNameSection(val, evidenceMap))
                        .collect(Collectors.toList());

        List<ProteinSection> included =
                f.includedNames.stream()
                        .map(val -> convertProteinNameSection(val, evidenceMap))
                        .collect(Collectors.toList());

        org.uniprot.core.uniprot.description.FlagType flag = createFlag(f.flags);
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        builder.recommendedName(recName)
                .submissionNames(subNames)
                .alternativeNames(altNames)
                .contains(contained)
                .includes(included)
                .flag(flag)
                .cdAntigenNames(cdAntigenNames)
                .innNames(innNames)
                .allergenName(allergenName)
                .biotechName(biotechName);

        return builder.build();
    }

    private org.uniprot.core.uniprot.description.FlagType createFlag(List<FlagType> flags) {
        if (flags.contains(FlagType.Fragment)) {
            if (flags.contains(FlagType.Precursor)) {
                return org.uniprot.core.uniprot.description.FlagType.FRAGMENT_PRECURSOR;
            } else {
                return org.uniprot.core.uniprot.description.FlagType.FRAGMENT;
            }
        } else if (flags.contains(FlagType.Fragments)) {
            if (flags.contains(FlagType.Precursor)) {
                return org.uniprot.core.uniprot.description.FlagType.FRAGMENTS_PRECURSOR;
            } else {
                return org.uniprot.core.uniprot.description.FlagType.FRAGMENTS;
            }
        } else if (flags.contains(FlagType.Precursor)) {
            return org.uniprot.core.uniprot.description.FlagType.PRECURSOR;
        } else if (flags.contains(FlagType.Precursor_Fragment)) {
            return org.uniprot.core.uniprot.description.FlagType.FRAGMENT_PRECURSOR;
        } else {
            return null;
        }
    }

    private ProteinSection convertProteinNameSection(
            DeLineObject.NameBlock nameBlock, Map<Object, List<Evidence>> evidenceMap) {
        ProteinRecName recName = null;
        if (nameBlock.recName != null) {
            recName = convertRecName(nameBlock.recName, evidenceMap);
        }

        List<ProteinAltName> altNames =
                nameBlock.altName.stream()
                        .map(val -> convertAltName(val, evidenceMap))
                        .collect(Collectors.toList());

        List<Name> cdAntigenNames =
                nameBlock.alt_CD_antigen.stream()
                        .map(val -> convertName(val, evidenceMap))
                        .collect(Collectors.toList());

        List<Name> innNames =
                nameBlock.alt_INN.stream()
                        .map(val -> convertName(val, evidenceMap))
                        .collect(Collectors.toList());
        Name biotechName = null;
        if (!Strings.isNullOrEmpty(nameBlock.alt_Biotech)) {
            biotechName = convertName(nameBlock.alt_Biotech, evidenceMap);
        }
        Name allergenName = null;
        if (!Strings.isNullOrEmpty(nameBlock.alt_Allergen)) {
            allergenName = convertName(nameBlock.alt_Allergen, evidenceMap);
        }

        return new ProteinSectionBuilder()
                .recommendedName(recName)
                .alternativeNames(altNames)
                .cdAntigenNames(cdAntigenNames)
                .innNames(innNames)
                .allergenName(allergenName)
                .biotechName(biotechName)
                .build();
    }

    private Name convertName(String val, Map<Object, List<Evidence>> evidenceMap) {
        NameBuilder builder = new NameBuilder();
        return builder.value(val).evidences(evidenceMap.get(val)).build();
    }

    private ProteinAltName convertAltName(
            DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
        Name fullName = null;
        if ((val.fullName != null) || (!val.fullName.isEmpty())) {
            fullName =
                    new NameBuilder()
                            .value(val.fullName)
                            .evidences(evidenceMap.get(val.fullName))
                            .build();
        }
        List<Name> shortNames =
                val.shortNames.stream()
                        .map(
                                shortName ->
                                        new NameBuilder()
                                                .value(shortName)
                                                .evidences(evidenceMap.get(shortName))
                                                .build())
                        .collect(Collectors.toList());
        List<EC> ecNumbers =
                val.ecs.stream()
                        .map(
                                ec -> {
                                    DeLineObject.ECEvidence ecEvidence =
                                            new DeLineObject.ECEvidence();
                                    ecEvidence.ecValue = ec;
                                    ecEvidence.nameECBelong = val;
                                    return new ECBuilder()
                                            .value(ec)
                                            .evidences(evidenceMap.get(ecEvidence))
                                            .build();
                                })
                        .collect(Collectors.toList());
        return new ProteinAltNameBuilder()
                .fullName(fullName)
                .shortNames(shortNames)
                .ecNumbers(ecNumbers)
                .build();
    }

    private ProteinRecName convertRecName(
            DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
        Name fullName = null;
        if ((val.fullName != null) || (!val.fullName.isEmpty())) {
            fullName =
                    new NameBuilder()
                            .value(val.fullName)
                            .evidences(evidenceMap.get(val.fullName))
                            .build();
        }
        List<Name> shortNames =
                val.shortNames.stream()
                        .map(
                                shortName ->
                                        new NameBuilder()
                                                .value(shortName)
                                                .evidences(evidenceMap.get(shortName))
                                                .build())
                        .collect(Collectors.toList());
        List<EC> ecNumbers =
                val.ecs.stream()
                        .map(ec -> createEC(ec, val, evidenceMap))
                        .collect(Collectors.toList());
        return new ProteinRecNameBuilder()
                .fullName(fullName)
                .shortNames(shortNames)
                .ecNumbers(ecNumbers)
                .build();
    }

    private EC createEC(String ec, DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
        DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
        ecEvidence.ecValue = ec;
        ecEvidence.nameECBelong = val;
        List<Evidence> evidences = evidenceMap.get(ecEvidence);
        return new ECBuilder().value(ec).evidences(evidences).build();
    }

    private ProteinSubName convertSubmissionName(
            DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
        Name fullName = null;
        if ((val.fullName != null) || (!val.fullName.isEmpty())) {
            fullName =
                    new NameBuilder()
                            .value(val.fullName)
                            .evidences(evidenceMap.get(val.fullName))
                            .build();
        }
        // List<Name> shortNames =val.shortNames.stream()
        // .map(shortName ->factory.createProteinName(shortName,
        // evidenceMap.get(shortName)) )
        // .collect(Collectors.toList());
        List<EC> ecNumbers =
                val.ecs.stream()
                        .map(
                                ec -> {
                                    DeLineObject.ECEvidence ecEvidence =
                                            new DeLineObject.ECEvidence();
                                    ecEvidence.ecValue = ec;
                                    ecEvidence.nameECBelong = val;
                                    return new ECBuilder()
                                            .value(ec)
                                            .evidences(evidenceMap.get(ecEvidence))
                                            .build();
                                })
                        .collect(Collectors.toList());
        return new ProteinSubNameBuilder().fullName(fullName).ecNumbers(ecNumbers).build();
    }
}
