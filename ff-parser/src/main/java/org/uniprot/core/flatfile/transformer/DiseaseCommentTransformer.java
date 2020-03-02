package org.uniprot.core.flatfile.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.uniprot.core.CrossReference;
import org.uniprot.core.builder.CrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.DiseaseBuilder;
import org.uniprot.core.uniprot.comment.builder.DiseaseCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

public class DiseaseCommentTransformer implements CommentTransformer<DiseaseComment> {
    private static final CommentType COMMENT_TYPE = CommentType.DISEASE;

    @Override
    public DiseaseComment transform(String annotation) {

        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        return transform(COMMENT_TYPE, annotation);
    }

    @Override
    public DiseaseComment transform(CommentType type, String annotation) {
        DiseaseCommentBuilder builder = new DiseaseCommentBuilder();

        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        annotation = updateMolecule(annotation, builder);

        String[] splitByNote = annotation.split("Note=");

        builder.disease(populateDisease(splitByNote[0].trim()));
        if (splitByNote.length == 2) {
            String noteValue = splitByNote[1].trim();
            Note note =
                    new NoteBuilder(CommentTransformerHelper.parseEvidencedValues(noteValue, true))
                            .build();
            builder.note(note);
        }
        return builder.build();
    }

    private String updateMolecule(String annotation, DiseaseCommentBuilder builder) {
        if (annotation.startsWith("[") && annotation.contains("]")) {
            int index = annotation.indexOf("]");
            String molecule = annotation.substring(1, index);
            molecule = molecule.replaceAll("\n", " ");
            builder.molecule(molecule);
            annotation = annotation.substring(index + 2).trim();
            if (annotation.startsWith("\n")) annotation = annotation.substring(1);
            return annotation;
        }
        return annotation;
    }

    private Disease populateDisease(String diseaseString) {

        String sep = "[MIM:";
        String sep2 = "]:";
        // if there is any disease defenition, then parse it
        if (diseaseString.length() > 0) {
            DiseaseBuilder builder = new DiseaseBuilder();
            int index = diseaseString.indexOf(sep);
            int index2 = diseaseString.indexOf(sep2);
            String firstPart = diseaseString;
            String description = diseaseString;
            if ((index != -1) && (index2 != -1)) {
                firstPart = diseaseString.substring(0, index2 + 2);
                description = diseaseString.substring(index2 + 2).trim();
            }

            // retrieve the text between square brackets
            builder.diseaseId(populateDiseaseId(firstPart));
            builder.acronym(populateDiseaseAcronym(firstPart));
            builder.reference(populateDiseaseReference(firstPart));
            populateDiseaseDescription(builder, description);

            return builder.build();
        }

        return null;
    }

    /**
     * Populates the {@link DiseaseId} object using its String representation
     *
     * @param diseaseString - the annotation with the definition of disease
     * @return the disease id
     */
    private String populateDiseaseId(String diseaseString) {

        String idString = diseaseString.substring(0, diseaseString.indexOf('(')).trim();
        return idString;
    }

    private static final Pattern BETWEEN_PARENTHESIS_PATTERN =
            Pattern.compile("\\((.*?)\\) \\[MIM:");

    /**
     * Populates the {@link DiseaseAcronym} object using its String representation
     *
     * @param diseaseString - the annotation with the definition of disease
     * @return the disease acronym
     */
    private String populateDiseaseAcronym(String diseaseString) {

        Matcher parenthesisMatcher = BETWEEN_PARENTHESIS_PATTERN.matcher(diseaseString);

        // retrieve the text between parenthesis of the first text element with
        // parenthesis
        String acronymString = "";
        if (parenthesisMatcher.find()) {
            acronymString = parenthesisMatcher.group(1);
        }

        return acronymString;
    }

    // pattern that identifies the disease reference
    private static final Pattern BETWEEN_SQUARE_BRACKETS_PATTERN =
            Pattern.compile("\\[(.*\\:.*?)\\]");

    /**
     * Populates the {@link DiseaseDescription} object using its String representation
     *
     * @param diseaseString - the annotation with the definition of disease
     * @return the disease description
     */
    private void populateDiseaseDescription(DiseaseBuilder builder, String diseaseString) {

        String descriptionString = diseaseString;
        List<Evidence> evidences = new ArrayList<>();
        descriptionString = CommentTransformerHelper.stripEvidences(descriptionString, evidences);
        // remove trailing full stop
        builder.description(descriptionString).evidencesSet(evidences);
    }

    /**
     * Populates the {@link DiseaseReference} object using its String representation
     *
     * @param diseaseString - the annotation with the definition of disease
     * @return the disease reference
     */
    private CrossReference<DiseaseDatabase> populateDiseaseReference(String diseaseString) {

        Matcher bracketsMatcher = BETWEEN_SQUARE_BRACKETS_PATTERN.matcher(diseaseString);

        // retrieve the text between square brackets
        if (bracketsMatcher.find()) {
            String referenceString = bracketsMatcher.group(1);

            String[] referenceElements = referenceString.split(":");

            DiseaseDatabase referenceType = DiseaseDatabase.typeOf(referenceElements[0]);

            return new CrossReferenceBuilder<DiseaseDatabase>()
                    .databaseType(referenceType)
                    .id(referenceElements[1])
                    .build();
        }

        return null;
    }
}
