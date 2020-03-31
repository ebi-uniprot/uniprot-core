package org.uniprot.core.flatfile.parser.impl.cc;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEMICOLON;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPARATOR_COMA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.flatfile.writer.impl.FFLineConstant;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.uniprotkb.comment.CatalyticActivityComment;
import org.uniprot.core.uniprotkb.comment.PhysiologicalReaction;
import org.uniprot.core.uniprotkb.comment.Reaction;
import org.uniprot.core.uniprotkb.comment.ReactionDatabase;

public class CatalyticActivityCCLineBuilder extends CCLineBuilderAbstr<CatalyticActivityComment> {
    private static final String EVIDENCE = "Evidence=";
    private static final String XREF = "Xref=";
    private static final String EC = "EC=";
    private static final String PHYSIO_DIRECTION = "PhysiologicalDirection=";
    private static final String REACTION = "Reaction=";
    // CC Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-
    // CC rhamnose + H(+) + NADPH; Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273,
    // CC ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;
    // CC EC=1.1.1.271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,
    // CC ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971,
    // CC ECO:0000269|PubMed:9473059};
    // CC PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;
    // CC Evidence={ECO:0000255|HAMAP-Rule:MF_00956};

    private String convertReactionReference(CrossReference<ReactionDatabase> rs) {
        StringBuilder sb = new StringBuilder();
        sb.append(rs.getDatabase().toDisplayName()).append(FFLineConstant.COLON).append(rs.getId());

        return sb.toString();
    }

    @Override
    protected List<String> buildCommentLines(
            CatalyticActivityComment comment,
            boolean includeFFMarkings,
            boolean showEvidence,
            boolean includeCommentType) {
        List<String> lines = new ArrayList<>();
        // first line
        StringBuilder firstLine = new StringBuilder();
        firstLine.append(buildStartWithMolecule(comment, includeFFMarkings, includeCommentType));

        if (firstLine.length() > 0) lines.add(firstLine.toString());
        Reaction reaction = comment.getReaction();
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkings) sb.append(this.linePrefix);
        sb.append(REACTION).append(reaction.getName()).append(FFLineConstant.SEMICOLON);
        List<CrossReference<ReactionDatabase>> xrefs = reaction.getReactionCrossReferences();
        //	List<String> words = new ArrayList<>();
        if (!xrefs.isEmpty()) {
            //	words =xrefs.stream().map(val ->
            // val.getName()).filter(val->val.contains("-")).collect(Collectors.toList());
            sb.append(FFLineConstant.SPACE)
                    .append(XREF)
                    .append(
                            xrefs.stream()
                                    .map(this::convertReactionReference)
                                    .collect(Collectors.joining(FFLineConstant.SEPARATOR_COMA)))
                    .append(FFLineConstant.SEMICOLON);
        }
        if ((reaction.getEcNumber() != null) && reaction.getEcNumber().isValid()) {
            sb.append(FFLineConstant.SPACE)
                    .append(EC)
                    .append(reaction.getEcNumber().getValue())
                    .append(FFLineConstant.SEMICOLON);
        }
        if (!reaction.getEvidences().isEmpty() && showEvidence) {
            sb.append(FFLineConstant.SPACE);
            sb.append(EVIDENCE);
            sb.append("{");
            for (int i = 0; i < reaction.getEvidences().size(); i++) {
                if (i > 0) sb.append(SEPARATOR_COMA);

                sb.append(reaction.getEvidences().get(i).getValue());
            }
            sb.append("}");
            sb.append(SEMICOLON);
        }
        if (includeFFMarkings) {
            List<String> lls =
                    FFLineWrapper.buildLines(
                            sb.toString(),
                            FFLineConstant.SEPS,
                            CC_PREFIX_INDENT,
                            FFLineConstant.LINE_LENGTH);
            lines.addAll(lls);
        } else lines.add(sb.toString());

        for (PhysiologicalReaction direction : comment.getPhysiologicalReactions()) {
            StringBuilder sb2 = new StringBuilder();
            if (includeFFMarkings) sb2.append(this.linePrefix);
            sb2.append(PHYSIO_DIRECTION)
                    .append(direction.getDirectionType().toDisplayName())
                    .append(FFLineConstant.SEPARATOR_SEMICOLON);

            sb2.append(XREF)
                    .append(convertReactionReference(direction.getReactionCrossReference()))
                    .append(FFLineConstant.SEMICOLON);
            if (!direction.getEvidences().isEmpty() && showEvidence) {
                sb2.append(FFLineConstant.SPACE);
                sb2.append(EVIDENCE);
                sb2.append("{");
                for (int i = 0; i < direction.getEvidences().size(); i++) {
                    if (i > 0) sb2.append(SEPARATOR_COMA);

                    sb2.append(direction.getEvidences().get(i).getValue());
                }
                sb2.append("}");
                sb2.append(SEMICOLON);
            }
            if (includeFFMarkings) {
                List<String> lls =
                        FFLineWrapper.buildLines(
                                sb2.toString(),
                                FFLineConstant.SEPS,
                                CC_PREFIX_INDENT,
                                FFLineConstant.LINE_LENGTH);
                lines.addAll(lls);
            } else lines.add(sb2.toString());
        }

        return lines;
    }
}
