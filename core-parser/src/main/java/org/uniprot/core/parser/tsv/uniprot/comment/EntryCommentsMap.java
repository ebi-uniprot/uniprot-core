package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.comment.SequenceCautionComment;

public class EntryCommentsMap implements NamedValueMap {
    public static final List<String> FIELDS =
            Arrays.asList(
                    "cc_alternative_products",
                    "cc_mass_spectrometry",
                    "cc_polymorphism",
                    "cc_rna_editing",
                    "cc_sequence_caution",
                    "cc_catalytic_activity",
                    "cc_cofactor",
                    "cc_activity_regulation",
                    "cc_function",
                    "cc_pathway",
                    "cc_miscellaneous",
                    "cc_interaction",
                    "cc_subunit",
                    "cc_developmental_stage",
                    "cc_induction",
                    "cc_tissue_specificity",
                    "cc_allergen",
                    "cc_biotechnology",
                    "cc_disruption_phenotype",
                    "cc_disease",
                    "cc_pharmaceutical",
                    "cc_toxic_dose",
                    "cc_subcellular_location",
                    "cc_ptm",
                    "cc_domain",
                    "cc_similarity",
                    "cc_caution",
                    "absorption",
                    "kinetics",
                    "ph_dependence",
                    "redox_potential",
                    "temp_dependence",
                    "error_gmodel_pred",
                    "protein_families");

    private final List<Comment> comments;
    private static final Pattern PATTERN_FAMILY =
            Pattern.compile(
                    "(?:In the .+? section; )?[Bb]elongs to the (.+?family)\\.(?:"
                            + " (.+?family)\\.)?(?: (.+?family)\\.)?(?: Highly divergent\\.)?");

    public EntryCommentsMap(List<Comment> comments) {
        if (comments == null) {
            this.comments = Collections.emptyList();
        } else this.comments = Collections.unmodifiableList(comments);
    }

    @Override
    public Map<String, String> attributeValues() {
        if (comments.isEmpty()) return Collections.emptyMap();

        Map<String, String> map = new HashMap<>();
        for (CommentType type : CommentType.values()) {
            switch (type) {
                case ALTERNATIVE_PRODUCTS:
                    AlternativeProductsMap alternativeProductsMap =
                            new AlternativeProductsMap(getComments(type));
                    map.putAll(alternativeProductsMap.attributeValues());
                    break;
                case BIOPHYSICOCHEMICAL_PROPERTIES:
                    BPCPMap bpcpCommentsMap = new BPCPMap(getComments(type));
                    map.putAll(bpcpCommentsMap.attributeValues());
                    break;
                case CATALYTIC_ACTIVITY:
                    CatalyticActivityMap catalyticActivityMap =
                            new CatalyticActivityMap(getComments(type));
                    map.putAll(catalyticActivityMap.attributeValues());
                    break;
                case COFACTOR:
                    CofactorMap cofactorMap = new CofactorMap(getComments(type));
                    map.putAll(cofactorMap.attributeValues());
                    break;
                case DISEASE:
                    DiseaseMap diseaseMap = new DiseaseMap(getComments(type));
                    map.putAll(diseaseMap.attributeValues());
                    break;
                case INTERACTION:
                    InteractionMap interactionMap = new InteractionMap(getComments(type));
                    map.putAll(interactionMap.attributeValues());
                    break;
                case MASS_SPECTROMETRY:
                    MassSpectrometryMap massSpectrometryMap =
                            new MassSpectrometryMap(getComments(type));
                    map.putAll(massSpectrometryMap.attributeValues());
                    break;
                case RNA_EDITING:
                    RnaEditingMap rnaEditingMap = new RnaEditingMap(getComments(type));
                    map.putAll(rnaEditingMap.attributeValues());
                    break;
                case SEQUENCE_CAUTION:
                    SequenceCautionMap sequenceCautionMap =
                            new SequenceCautionMap(getComments(type));
                    map.putAll(sequenceCautionMap.attributeValues());
                    break;
                case SUBCELLULAR_LOCATION:
                    SubcellularLocationMap subcellularLocationMap =
                            new SubcellularLocationMap(getComments(type));
                    map.putAll(subcellularLocationMap.attributeValues());
                    break;
                case WEBRESOURCE:
                    // List<WRComment> wrComments = getComments(type);
                    break;
                case SIMILARITY:
                    List<FreeTextComment> simiComments = getComments(type);
                    FreeTextMap simiCommentsMap = new FreeTextMap(simiComments, type);
                    map.putAll(simiCommentsMap.attributeValues());
                    updateProteinFamility(map, type, simiComments);
                    break;
                default:
                    FreeTextMap freeTextMap = new FreeTextMap(getComments(type), type);
                    map.putAll(freeTextMap.attributeValues());
            }
        }
        return map;
    }

    private void updateProteinFamility(
            Map<String, String> map, CommentType type, List<FreeTextComment> txtComments) {
        if ((txtComments == null) || txtComments.isEmpty() || type != CommentType.SIMILARITY)
            return;

        String value =
                txtComments.stream()
                        .flatMap(val -> val.getTexts().stream())
                        .map(val -> convertToProteinFamily(val.getValue()))
                        .filter(val -> val != null && !val.isEmpty())
                        .collect(Collectors.joining("; "));
        map.put("protein_families", value);
    }

    private String convertToProteinFamily(String text) {
        String val = text;
        if (!val.endsWith(".")) {
            val += ".";
        }
        Matcher m = PATTERN_FAMILY.matcher(val);
        if (m.matches()) {
            StringBuilder line = new StringBuilder();
            line.append(m.group(1));
            if (m.group(2) != null) line.append(", ").append(m.group(2));
            if (m.group(3) != null) line.append(", ").append(m.group(3));
            String result = line.toString();
            return result.substring(0, 1).toUpperCase() + result.substring(1);
        }
        return null;
    }

    public static List<String> getSequenceCautionTypes(List<Comment> comments) {
        if (comments == null) return Collections.emptyList();
        Map<String, Long> values =
                comments.stream()
                        .filter(
                                comment ->
                                        (comment.getCommentType()
                                                .equals(CommentType.SEQUENCE_CAUTION)))
                        .map(val -> (SequenceCautionComment) val)
                        .map(val -> val.getSequenceCautionType().name())
                        .collect(
                                Collectors.groupingBy(
                                        val -> val, TreeMap::new, Collectors.counting()));
        return values.entrySet().stream()
                .map(val -> (val.getKey() + " (" + val.getValue().toString() + ")"))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private <T extends Comment> List<T> getComments(CommentType type) {
        return comments.stream()
                .filter(comment -> (comment.getCommentType().equals(type)))
                .map(val -> (T) val)
                .collect(Collectors.toList());
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
