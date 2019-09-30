package org.uniprot.core.flatfile.transformer;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.SubcellularLocation;
import org.uniprot.core.uniprot.comment.SubcellularLocationComment;
import org.uniprot.core.uniprot.comment.SubcellularLocationValue;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.comment.builder.SubcellularLocationBuilder;
import org.uniprot.core.uniprot.comment.builder.SubcellularLocationCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.SubcellularLocationValueBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

public class SubcelLocationCommentTransformer
        implements CommentTransformer<SubcellularLocationComment> {
    private static final CommentType COMMENT_TYPE = CommentType.SUBCELLULAR_LOCATION;

    @Override
    public SubcellularLocationComment transform(String annotation) {
        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        return transform(COMMENT_TYPE, annotation);
    }

    @Override
    public SubcellularLocationComment transform(CommentType commentType, String annotation) {

        if (annotation == null) return null;
        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        int moleculeEndIndex;

        String preNote;
        if (annotation.contains("Note=")) {
            String[] splitByNote = annotation.split("Note=");

            // Populate note if it exists in the text
            if (splitByNote.length > 1) {
                preNote = splitByNote[0].trim();
                String noteStr = splitByNote[1].trim();
                builder.note(
                        new NoteBuilder(
                                        CommentTransformerHelper.parseEvidencedValues(
                                                noteStr, true, '.'))
                                .build());
            } else {
                preNote = annotation;
            }
        } else {
            preNote = annotation;
        }

        // Look for molecule in section ahead of Note, if it exists

        moleculeEndIndex = preNote.indexOf(": ");
        if (moleculeEndIndex > -1) {
            builder.molecule(preNote.substring(0, moleculeEndIndex));
            preNote = preNote.substring(moleculeEndIndex + 1).trim();
        }

        List<SubcellularLocation> locations = populateLocations(preNote);
        builder.subcellularLocations(locations);
        return builder.build();
    }

    private List<SubcellularLocation> populateLocations(String preNote) {
        List<SubcellularLocation> locations = new ArrayList<>();
        String[] strings = preNote.split("\\. ");
        for (String aSubcellularLocation : strings) {

            // Each value here is an individual sub-location of the subcellular
            // location
            if (aSubcellularLocation.trim().length() == 0) {
                continue;
            }
            locations.add(populateLocation(aSubcellularLocation));
        }
        return locations;
    }

    private SubcellularLocation populateLocation(String subcellularLocationsStrings) {
        String[] locationElements = subcellularLocationsStrings.split(";");
        SubcellularLocationValue location = populateValue(locationElements[0]);
        SubcellularLocationValue topology = null;
        SubcellularLocationValue orientation = null;
        if (locationElements.length > 1) {
            topology = populateValue(locationElements[1]);
        }
        if (locationElements.length > 2) {
            orientation = populateValue(locationElements[2]);
        }
        return new SubcellularLocationBuilder()
                .location(location)
                .topology(topology)
                .orientation(orientation)
                .build();
    }

    private SubcellularLocationValue populateValue(String value) {
        value = CommentTransformerHelper.stripTrailing(value, ".").trim();
        List<Evidence> evidences = new ArrayList<>();
        value = CommentTransformerHelper.stripEvidences(value, evidences);
        value = CommentTransformerHelper.stripTrailing(value, ".");
        return new SubcellularLocationValueBuilder("", value, evidences).build();
    }
}
