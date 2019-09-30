package org.uniprot.core.flatfile.parser.impl.cc;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

import com.google.common.base.Strings;

/**
 * @author jieluo CC -!- SUBCELLULAR LOCATION: Golgi apparatus, trans-Golgi network |CC membrane;
 *     Multi-pass membrane protein (By similarity). |CC Note=Predominantly found in the trans-Golgi
 *     network (TGN). Not |CC redistributed to the plasma membrane in response to elevated |CC
 *     copper levels.
 */
public class CCSubCellCommentLineBuilder extends CCLineBuilderAbstr<SubcellularLocationComment> {

    @Override
    protected List<String> buildCommentLines(
            SubcellularLocationComment comment,
            boolean includeFFMarkings,
            boolean showEvidence,
            boolean includeCommentType) {
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkings) {
            addFlatFileMarkingsIfRequired(includeFFMarkings, sb);
        }
        if (includeCommentType) addCommentTypeName(comment, sb);
        // Add molecule
        boolean needSpace = false;
        if (!Strings.isNullOrEmpty(comment.getMolecule())) {
            sb.append(comment.getMolecule()).append(":");
            needSpace = true;
        }
        StringBuilder locations =
                buildLocations(comment, comment.getSubcellularLocations(), showEvidence);
        if (locations.length() > 0) {
            if (needSpace) sb.append(SPACE);
            sb.append(locations);
            needSpace = true;
        }
        // Add note
        StringBuilder notes = buildNote(comment, showEvidence);
        if (notes.length() > 0) {
            if ((needSpace) && (isValidNote(comment.getNote()))) sb.append(SPACE);
            sb.append(notes);
        }
        if (includeFFMarkings) {
            return FFLineWrapper.buildLines(sb.toString(), SEPS, linePrefix, LINE_LENGTH);
        } else {
            List<String> lines = new ArrayList<>();
            lines.add(sb.toString());
            return lines;
        }
    }

    private StringBuilder buildNote(SubcellularLocationComment comment, boolean showEvidence) {
        StringBuilder noteBuilder = new StringBuilder();
        if (!isValidNote(comment.getNote())) return noteBuilder;
        noteBuilder.append(NOTE);
        boolean isfirst = true;
        Note note = comment.getNote();
        if (note != null) {
            for (EvidencedValue val : note.getTexts()) {
                if (!isfirst) noteBuilder.append(SPACE);
                noteBuilder.append(val.getValue());
                appendIfNot(noteBuilder, '.');
                if (showEvidence) {
                    if ((val.getEvidences() != null) && (val.getEvidences().size() > 0)) {
                        addEvidence(val, noteBuilder, showEvidence, STOP, "");
                    }
                }
                isfirst = false;
            }
        }
        return noteBuilder;
    }

    private StringBuilder buildLocations(
            Comment comment, List<SubcellularLocation> alocations, boolean showEvidence) {
        StringBuilder builder = new StringBuilder();
        // Add locations
        int locationCount = 0;
        for (SubcellularLocation aLocation : alocations) {
            if (aLocation != null) {
                if (locationCount > 0) builder.append(". ");
                builder.append(buildLocation(comment, aLocation.getLocation(), "", showEvidence));
                builder.append(buildLocation(comment, aLocation.getTopology(), "; ", showEvidence));
                builder.append(
                        buildLocation(comment, aLocation.getOrientation(), "; ", showEvidence));
                locationCount++;
            }
        }
        if (locationCount > 0) builder.append(STOP);
        return builder;
    }

    private StringBuilder buildLocation(
            Comment comment,
            SubcellularLocationValue location,
            String prefix,
            boolean showEvidence) {
        StringBuilder sb = new StringBuilder();
        if (isValidLocationValue(location)) {
            sb.append(prefix);
            sb.append(location.getValue());

            if (showEvidence) {
                if (!location.getEvidences().isEmpty()) {
                    addEvidence(location, sb, showEvidence, "", "");
                }
                //	 else if((comment.getEvidenceIds() !=null)&&(comment.getEvidenceIds().size()>0)){
                //     		 addEvidence(comment, sb,   showEvidence, "", "");
                //     	}
            }
        }
        return sb;
    }

    private boolean isValidLocationValue(SubcellularLocationValue location) {
        return (location != null) && !Strings.isNullOrEmpty(location.getValue());
    }
}
