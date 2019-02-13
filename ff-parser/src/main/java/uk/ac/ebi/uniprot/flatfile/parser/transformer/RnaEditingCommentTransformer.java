package uk.ac.ebi.uniprot.flatfile.parser.transformer;

import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.RnaEditingCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.RnaEditingPositionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;


public class RnaEditingCommentTransformer implements CommentTransformer<RnaEditingComment> {
    private static final String UNKNOWN = "Unknown";

    private static final String UNDETERMINED = "Undetermined";

    private static final String NOT_APPLICABLE = "Not_applicable";

    private static final String NOTE = "Note=";

    private static final String MODIFIED_POSITIONS = "Modified_positions=";

    /**
     * String ccLineStringEvidence =("RNA EDITING: Modified_positions=156 {ECO:0000313|EMBL:BAG16761.1}, " + "158
     * {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}, 160 " + "{ECO:0000303|Ref.6}; Note=Partially edited. RNA
     * editing generates "+ "receptor isoforms that differ in their ability to interact with "+ "the phospholipase C
     * signaling cascade in a transfected cell line, "+ "suggesting that this RNA processing event may contribute to the
     * "+ "modulation of serotonergic neurotransmission in the central "+ "nervous system.
     * {ECO:0000256|HAMAP-Rule:MF_00205, " + "ECO:0000313|PDB:3OW2};");
     */

    private static final CommentType COMMENT_TYPE = CommentType.RNA_EDITING;

    @Override
    public RnaEditingComment transform(String annotation) {
        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        return transform(COMMENT_TYPE, annotation);
    }

    @Override
    public RnaEditingComment transform(CommentType commentType, String annotation) {
        annotation = CommentTransformerHelper.stripTrailing(annotation, ";");
        int noteIndex = annotation.indexOf("; " + NOTE);
        String noteStr = null;
        if (noteIndex != -1) {
            noteStr = annotation.substring(noteIndex + ("; " + NOTE).length());
            annotation = annotation.substring(0, noteIndex);
        }
        String[] tokens = annotation.split(";");
        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        for (String token : tokens) {
            token = token.trim();
            if (token.startsWith(MODIFIED_POSITIONS)) {
                token = token.substring(MODIFIED_POSITIONS.length());
                List<String> poses = splitPosition(token);
                RnaEditingLocationType type = getLocationType(poses);
                builder.locationType(type);
                if (type != RnaEditingLocationType.Known) {
                    continue;
                }
                List<RnaEdPosition> positions = new ArrayList<>();
                for (String pos : poses) {
                    pos = pos.trim();
                    List<Evidence> evidences = new ArrayList<>();
                    pos = CommentTransformerHelper.stripEvidences(pos, evidences);

                    RnaEdPosition position = new RnaEditingPositionBuilder(pos, evidences).build();
                    positions.add(position);
                }
                builder.positions(positions);
            }
        }
        if (noteStr != null) {
            Note note =
                    new NoteBuilder(CommentTransformerHelper.parseEvidencedValues(noteStr, true)).build();
            builder.note(note);
        }
        return builder.build();
    }

    private List<String> splitPosition(String pos) {
        List<String> positions = new ArrayList<>();
        String sep = ", ";
        String eCO = "ECO:";
        int index = 0;
        int start = 0;
        do {

            index = pos.indexOf(sep, start);
            if (index == -1) {
                positions.add(pos);
                break;
            }
            String sub = pos.substring(index + 2);
            if (sub.startsWith(eCO)) {
                start = index + 2;
            } else {
                String pos1 = pos.substring(0, index);
                pos = pos.substring(index + 2);
                positions.add(pos1);
                start = 0;
            }

        } while (index != -1);
        return positions;

    }

    private RnaEditingLocationType getLocationType(List<String> poses) {
        // Not_applicable,
        // Undetermined,
        // Unknown,
        // Known;
        if (poses.size() > 1)
            return RnaEditingLocationType.Known;
        else if (poses.get(0).startsWith(NOT_APPLICABLE))
            return RnaEditingLocationType.Not_applicable;
        else if (poses.get(0).startsWith(UNDETERMINED))
            return RnaEditingLocationType.Undetermined;
        else if (poses.get(0).startsWith(UNKNOWN))
            return RnaEditingLocationType.Unknown;
        else
            return RnaEditingLocationType.Known;

    }

}
