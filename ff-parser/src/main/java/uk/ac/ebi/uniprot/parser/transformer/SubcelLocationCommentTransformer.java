package uk.ac.ebi.uniprot.parser.transformer;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SubcellularLocationCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;


public class SubcelLocationCommentTransformer implements
        CommentTransformer<SubcellularLocationComment> {
    private static final CommentType COMMENT_TYPE = CommentType.SUBCELLULAR_LOCATION;

    @Override
    public SubcellularLocationComment transform(String annotation) {
        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        return transform(COMMENT_TYPE, annotation);
    }

    @Override
    public SubcellularLocationComment transform(CommentType commentType, String annotation) {

        if (annotation == null)
            return null;
        SubcellularLocationCommentBuilder builder = SubcellularLocationCommentBuilder.newInstance();
        int moleculeEndIndex;

        String preNote;
        if (annotation.contains("Note=")) {
            String[] splitByNote = annotation.split("Note=");

            // Populate note if it exists in the text
            if (splitByNote.length > 1) {
                preNote = splitByNote[0].trim();
                String noteStr = splitByNote[1].trim();
                builder.note(CommentFactory.INSTANCE.createNote(CommentTransformerHelper
                        .parseEvidencedValues(noteStr, true, '.')));
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
            locations.add( populateLocation(aSubcellularLocation));

        }
        return locations;
    }

    private SubcellularLocation populateLocation(String subcellularLocationsStrings) {
        String[] locationElements = subcellularLocationsStrings.split(";");
        SubcellularLocationValue location = populateValue(locationElements[0]);
        SubcellularLocationValue topology = null; 
        SubcellularLocationValue orientation = null;
        if (locationElements.length > 1) {
        	topology =populateValue(locationElements[1]);
        }
        if (locationElements.length > 2) {
        	orientation =populateValue(locationElements[2]);
        }
        return SubcellularLocationCommentBuilder.createSubcellularLocation(location, topology, orientation);
    }

    private SubcellularLocationValue populateValue(String value) {

        value = CommentTransformerHelper.stripTrailing(value, ".").trim();
        List<Evidence> evidences = new ArrayList<>();
        value = CommentTransformerHelper.stripEvidences(value, evidences);
        value = CommentTransformerHelper.stripTrailing(value, ".");
        return SubcellularLocationCommentBuilder.createSubcellularLocationValue(value, evidences);
    }
}
