package org.uniprot.core.flatfile.transformer;

import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueBuilder;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CommentTransformerHelper {
    private static final CommentTransformer<FreeTextComment> DEAFULT_BUILDER =
            new FreeTextCommentTranslator();
    private static Map<CommentType, CommentTransformer<? extends Comment>> commentTranslator =
            new EnumMap<>(CommentType.class);

    static {
        commentTranslator.put(CommentType.ALTERNATIVE_PRODUCTS, new APCommentTransformer());
        commentTranslator.put(
                CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, new BPCPCommentTransformer());
        commentTranslator.put(CommentType.COFACTOR, new CofactorCommentTransformer());
        commentTranslator.put(CommentType.DISEASE, new DiseaseCommentTransformer());
        commentTranslator.put(CommentType.INTERACTION, new InteractionCommentTransformer());
        commentTranslator.put(CommentType.MASS_SPECTROMETRY, new MSCommentTransformer());
        commentTranslator.put(CommentType.RNA_EDITING, new RnaEditingCommentTransformer());
        commentTranslator.put(CommentType.SEQUENCE_CAUTION, new SeqCautionCommentTransformer());
        commentTranslator.put(
                CommentType.SUBCELLULAR_LOCATION, new SubcelLocationCommentTransformer());

        commentTranslator.put(CommentType.WEBRESOURCE, new WebResourceCommentTransformer());
        commentTranslator.put(
                CommentType.CATALYTIC_ACTIVITY, new CatalyticActivityCommentTransformer());
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comment> CommentTransformer<T> createTranslator(CommentType type) {
        CommentTransformer<T> builder = (CommentTransformer<T>) commentTranslator.get(type);
        if (builder != null) return builder;
        else return (CommentTransformer<T>) DEAFULT_BUILDER;
    }

    public static String trimCommentHeader(String annotation, CommentType type) {
        if (annotation.startsWith(type.getDisplayName())) {
            annotation = annotation.substring(type.getDisplayName().length() + 1).trim();
            if (annotation.startsWith("\n")) annotation = annotation.substring(1);
        }
        return annotation;
    }

    public static <T extends Comment> T transform(String annotation, CommentType type) {
        CommentTransformer<T> translator = createTranslator(type);
        return translator.transform(type, annotation);
    }

    public static String stripTrailing(String val, String trail) {
        if (val.endsWith(trail)) return val.substring(0, val.length() - trail.length());
        else return val;
    }

    public static String stripEvidences(String line, List<Evidence> evidences) {
        if (line.endsWith("}") || line.endsWith("};") || line.endsWith("}.")) {
            String ev = line.substring(line.lastIndexOf('{') + 1, line.lastIndexOf('}'));
            String[] tokens = ev.split(",");
            for (String token : tokens) {
                evidences.add(parseEvidenceLine(token.trim()));
            }
        } else {
            return line;
        }
        return line.substring(0, line.lastIndexOf('{')).trim();
    }

    public static List<EvidencedValue> parseEvidencedValues(String value, boolean trimEndFullStop) {
        return parseEvidencedValues(value, trimEndFullStop, '.');
    }

    public static List<EvidencedValue> parseEvidencedValues(
            String value, boolean trimEndFullStop, char separator) {

        List<EvidencedValue> evValues = new ArrayList<>();
        char comm = separator;
        char curing = '}';
        char dot = '.';

        String temp = value;
        if (temp.endsWith(";")) temp = temp.substring(0, temp.length() - 1);
        int index = temp.indexOf(comm);
        if ((index == -1) || (index == value.length() - 1)) {
            evValues.add(parseEvidencedValue(temp, trimEndFullStop));
            return evValues;
        }

        do {
            if ((temp.charAt(index - 1) == curing) || (temp.charAt(index - 1) == dot)) {
                String token = temp.substring(0, index).trim();
                evValues.add(parseEvidencedValue(token, trimEndFullStop));
                temp = temp.substring(index + 1).trim();
                index = temp.indexOf(comm);
            } else {
                index = temp.indexOf(comm, index + 1);
            }

        } while ((index != -1) && (index != temp.length() - 1));
        if (!temp.isEmpty()) {
            evValues.add(parseEvidencedValue(temp, trimEndFullStop));
        }
        return evValues;
    }

    public static EvidencedValue parseEvidencedValue(String value, boolean trimEndFullStop) {
        List<Evidence> evidences = new ArrayList<>();
        String val = value;
        if (val.endsWith(";")) {
            val = val.substring(0, val.length() - 1);
        }
        val = stripEvidences(val, evidences);
        if (trimEndFullStop && (val.endsWith("."))) {
            val = val.substring(0, val.length() - 1).trim();
        }
        return new EvidencedValueBuilder(val, evidences).build();
    }
}
