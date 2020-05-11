package org.uniprot.core.parser.tsv.uniprot;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniprotkb.description.*;

public class EntryProteinMap implements NamedValueMap {
    private static final String EC2 = "EC";
    private static final String SQUARE_BLACKET_RIGHT = "]";
    private static final String SQUARE_BLACKET_LEFT = "[";
    private static final String SEMICOLON = "; ";
    private static final String CLEAVED_INTO = "Cleaved into:";
    private static final String INCLUDES = "Includes:";
    private static final String CD_ANTIGEN = "CD antigen";
    private static final String BIOTECH = "biotech";
    private static final String ALLERGEN = "allergen";
    private static final String BLACKET_RIGHT = ")";
    private static final String BLACKET_LEFT = "(";
    private static final String SPACE = " ";
    private static final String DELIMITER = ", ";

    public static final List<String> FIELDS = Arrays.asList("protein_name", "ec", "fragment");

    private final ProteinDescription protein;

    public EntryProteinMap(ProteinDescription protein) {
        this.protein = protein;
    }

    @Override
    public Map<String, String> attributeValues() {
        if (protein == null) {
            return Collections.emptyMap();
        }

        Map<String, String> map = new HashMap<>();
        map.put(FIELDS.get(0), getProteinName());
        String ecs = getECNumber();
        if (!ecs.isEmpty()) {
            map.put(FIELDS.get(1), ecs);
        }
        String fragment = getFragment();
        map.put(FIELDS.get(2), fragment);
        return map;
    }

    private String getFragment() {
        if (protein.getFlag() != null
                && protein.getFlag().getType() != null
                && !protein.getFlag().getType().equals(FlagType.PRECURSOR)) {
            return "fragment";
        }
        return "";
    }

    private String getProteinName() {
        StringBuilder sb = new StringBuilder();
        if (protein.getRecommendedName() != null) {
            sb.append(convertProteinNameToString(protein.getRecommendedName()));
        }
        if ((protein.getAlternativeNames() != null) && !protein.getAlternativeNames().isEmpty()) {
            if (sb.length() > 0) {
                sb.append(SPACE);
            }
            sb.append(
                    protein.getAlternativeNames().stream()
                            .map(
                                    val ->
                                            BLACKET_LEFT
                                                    + convertProteinAltNameToString(val)
                                                    + BLACKET_RIGHT)
                            .collect(Collectors.joining(SPACE)));
        }
        if ((protein.getSubmissionNames() != null) && !protein.getSubmissionNames().isEmpty()) {
            sb.append(convertProteinSubNameToString(protein.getSubmissionNames().get(0)));
            String data =
                    protein.getSubmissionNames().stream()
                            .skip(1)
                            .map(
                                    val ->
                                            BLACKET_LEFT
                                                    + convertProteinSubNameToString(val)
                                                    + BLACKET_RIGHT)
                            .collect(Collectors.joining(SPACE));
            if (data != null && !data.isEmpty()) {
                sb.append(SPACE).append(data);
            }
        }
        if (protein.getAllergenName() != null) {
            sb.append(SPACE)
                    .append(BLACKET_LEFT)
                    .append(ALLERGEN)
                    .append(SPACE)
                    .append(protein.getAllergenName().getValue())
                    .append(BLACKET_RIGHT);
        }
        if (protein.getBiotechName() != null) {
            sb.append(SPACE)
                    .append(BLACKET_LEFT)
                    .append(BIOTECH)
                    .append(SPACE)
                    .append(protein.getBiotechName().getValue())
                    .append(BLACKET_RIGHT);
        }
        if ((protein.getCdAntigenNames() != null) && !protein.getCdAntigenNames().isEmpty()) {
            sb.append(SPACE);
            sb.append(
                    protein.getCdAntigenNames().stream()
                            .map(
                                    val ->
                                            BLACKET_LEFT
                                                    + CD_ANTIGEN
                                                    + SPACE
                                                    + val.getValue()
                                                    + BLACKET_RIGHT)
                            .collect(Collectors.joining(SPACE)));
        }
        if ((protein.getInnNames() != null) && !protein.getInnNames().isEmpty()) {
            sb.append(SPACE);
            sb.append(
                    protein.getInnNames().stream()
                            .map(val -> BLACKET_LEFT + val.getValue() + BLACKET_RIGHT)
                            .collect(Collectors.joining(SPACE)));
        }

        if ((protein.getContains() != null) && !protein.getContains().isEmpty()) {
            sb.append(SPACE)
                    .append(SQUARE_BLACKET_LEFT)
                    .append(CLEAVED_INTO)
                    .append(SPACE)
                    .append(
                            protein.getContains().stream()
                                    .map(this::convertProteinSectionToString)
                                    .collect(Collectors.joining(SEMICOLON)))
                    .append(SPACE)
                    .append(SQUARE_BLACKET_RIGHT);
        }

        if ((protein.getIncludes() != null) && !protein.getIncludes().isEmpty()) {
            sb.append(SPACE)
                    .append(SQUARE_BLACKET_LEFT)
                    .append(INCLUDES)
                    .append(SPACE)
                    .append(
                            protein.getIncludes().stream()
                                    .map(this::convertProteinSectionToString)
                                    .collect(Collectors.joining(SEMICOLON)))
                    .append(SPACE)
                    .append(SQUARE_BLACKET_RIGHT);
        }
        return sb.toString();
    }

    private String getECNumber() {
        Set<String> ecs = new TreeSet<>();
        if (protein.getRecommendedName() != null) {
            ecs.addAll(convertProteinNameEcNumbersToString(protein.getRecommendedName()));
        }
        if ((protein.getAlternativeNames() != null) && !protein.getAlternativeNames().isEmpty()) {
            protein.getAlternativeNames()
                    .forEach(val -> ecs.addAll(convertProteinNameEcNumbersToString(val)));
        }
        if ((protein.getSubmissionNames() != null) && !protein.getSubmissionNames().isEmpty()) {
            protein.getSubmissionNames()
                    .forEach(val -> ecs.addAll(convertProteinNameEcNumbersToString(val)));
        }

        if ((protein.getIncludes() != null) && !protein.getIncludes().isEmpty()) {
            protein.getIncludes()
                    .forEach(val -> ecs.addAll(convertProteinSectionEcNumbersToString(val)));
        }

        if ((protein.getContains() != null) && !protein.getContains().isEmpty()) {
            protein.getContains()
                    .forEach(val -> ecs.addAll(convertProteinSectionEcNumbersToString(val)));
        }
        if (ecs.isEmpty()) {
            return "";
        } else {
            return String.join("; ", ecs);
        }
    }

    private String convertProteinSectionToString(ProteinSection section) {
        StringBuilder sb = new StringBuilder();
        if (section.getRecommendedName() != null) {
            sb.append(convertProteinNameToString(section.getRecommendedName()));
        }
        if (section.getAlternativeNames() != null && !section.getAlternativeNames().isEmpty()) {
            if (sb.length() > 0) {
                sb.append(SPACE);
            }
            String alternativeNames =
                    section.getAlternativeNames().stream()
                            .map(
                                    val ->
                                            BLACKET_LEFT
                                                    + convertProteinAltNameToString(val)
                                                    + BLACKET_RIGHT)
                            .collect(Collectors.joining(SPACE));
            sb.append(alternativeNames);
        }
        return sb.toString();
    }

    private String convertProteinAltNameToString(ProteinAltName name) {
        StringBuilder sb = new StringBuilder();
        sb.append(name.getFullName().getValue());
        String sname =
                name.getShortNames().stream()
                        .map(Name::getValue)
                        .collect(Collectors.joining(DELIMITER));
        String ec =
                name.getEcNumbers().stream()
                        .map(val -> EC2 + SPACE + val.getValue())
                        .collect(Collectors.joining(DELIMITER));
        if (sname != null && !sname.isEmpty()) {
            sb.append(DELIMITER).append(sname);
        }
        if (ec != null && !ec.isEmpty()) {
            sb.append(DELIMITER).append(ec);
        }
        return sb.toString();
    }

    private String convertProteinSubNameToString(ProteinSubName name) {
        StringBuilder sb = new StringBuilder();
        sb.append(name.getFullName().getValue());
        String ec =
                name.getEcNumbers().stream()
                        .map(val -> EC2 + SPACE + val.getValue())
                        .collect(Collectors.joining(DELIMITER));
        if (ec != null && !ec.isEmpty()) {
            sb.append(DELIMITER).append(ec);
        }
        return sb.toString();
    }

    private String convertProteinNameToString(ProteinRecName name) {
        StringBuilder sb = new StringBuilder();
        sb.append(name.getFullName().getValue());
        String sname =
                name.getShortNames().stream()
                        .map(Name::getValue)
                        .collect(Collectors.joining(DELIMITER));
        String ec =
                name.getEcNumbers().stream()
                        .map(val -> EC2 + SPACE + val.getValue())
                        .collect(Collectors.joining(DELIMITER));
        if (sname != null && !sname.isEmpty()) {
            sb.append(DELIMITER).append(sname);
        }
        if (ec != null && !ec.isEmpty()) {
            sb.append(DELIMITER).append(ec);
        }
        return sb.toString();
    }

    private List<String> convertProteinSectionEcNumbersToString(ProteinSection section) {
        List<String> ec = new ArrayList<>();
        if (section.getRecommendedName() != null) {
            ec.addAll(convertProteinNameEcNumbersToString(section.getRecommendedName()));
        }
        if (section.getAlternativeNames() != null) {
            List<String> ecs = new ArrayList<>();
            for (ProteinAltName proteinName : section.getAlternativeNames()) {
                ecs.addAll(convertProteinNameEcNumbersToString(proteinName));
            }
            ec.addAll(ecs);
        }
        return ec;
    }

    private List<String> convertProteinNameEcNumbersToString(ProteinRecName name) {
        return name.getEcNumbers().stream().map(EC::getValue).collect(Collectors.toList());
    }

    private List<String> convertProteinNameEcNumbersToString(ProteinAltName name) {
        return name.getEcNumbers().stream().map(EC::getValue).collect(Collectors.toList());
    }

    private List<String> convertProteinNameEcNumbersToString(ProteinSubName name) {
        return name.getEcNumbers().stream().map(EC::getValue).collect(Collectors.toList());
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
