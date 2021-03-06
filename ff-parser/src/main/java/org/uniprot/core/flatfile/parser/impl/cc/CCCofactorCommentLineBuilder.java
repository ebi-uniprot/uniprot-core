package org.uniprot.core.flatfile.parser.impl.cc;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.LINE_LENGTH;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEMICOLON;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPARATOR_SEMICOLON;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPS;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SPACE;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.STOP;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.LineBuilderHelper;
import org.uniprot.core.uniprotkb.comment.Cofactor;
import org.uniprot.core.uniprotkb.comment.CofactorComment;
import org.uniprot.core.uniprotkb.comment.CofactorDatabase;

/**
 * CC -!- COFACTOR: CC Name=Mg(2+); Xref=ChEBI:CHEBI:18420;
 * Evidence={ECO:0000255|HAMAP-Rule:MF_00086}; CC Name=Co(2+); Xref=ChEBI:CHEBI:48828;
 * Evidence={ECO:0000255|HAMAP-Rule:MF_00086}; CC Note=Binds 2 divalent ions per subunit (magnesium
 * or cobalt). CC {ECO:0000255|HAMAP-Rule:MF_00086}; CC -!- COFACTOR: Serine protease NS3: CC
 * Name=Zn(2+); Xref=ChEBI:CHEBI:29105; Evidence={ECO:0000269|PubMed:9060645}; CC Note=Binds 1 zinc
 * ion per NS3 protease domain.;
 *
 * @author jieluo
 */
public class CCCofactorCommentLineBuilder extends CCLineBuilderAbstr<CofactorComment> {

    private static final String EVIDENCE = "Evidence=";
    private static final String XREF = "Xref=";

    @Override
    protected List<String> buildCommentLines(
            CofactorComment comment,
            boolean includeFFMarkings,
            boolean showEvidence,
            boolean includeCommentType) {
        List<String> lines = new ArrayList<>();
        // first line
        StringBuilder firstLine = new StringBuilder();

        firstLine.append(buildStartWithMolecule(comment, includeFFMarkings, includeCommentType));
        if (firstLine.length() > 0) lines.add(firstLine.toString());
        for (Cofactor cofactor : comment.getCofactors()) {
            StringBuilder sb = new StringBuilder();
            if (includeFFMarkings) sb.append(this.linePrefix);
            sb.append(NAME).append(cofactor.getName()).append(SEPARATOR_SEMICOLON);
            CrossReference<CofactorDatabase> coRef = cofactor.getCofactorCrossReference();
            sb.append(XREF)
                    .append(coRef.getDatabase().getDisplayName())
                    .append(":")
                    .append(coRef.getId())
                    .append(SEMICOLON);
            if (!cofactor.getEvidences().isEmpty() && showEvidence) {
                sb.append(SPACE);
                sb.append(EVIDENCE);
                String evStr = LineBuilderHelper.export(cofactor.getEvidences()).trim();
                sb.append(evStr).append(SEMICOLON);
            }
            if (includeFFMarkings) {
                List<String> lls =
                        FFLineWrapper.buildLines(
                                sb.toString(), SEPS, CC_PREFIX_INDENT, LINE_LENGTH);
                lines.addAll(lls);
            } else lines.add(sb.toString());
        }
        if (isValidNote(comment.getNote())) {
            StringBuilder sb = new StringBuilder();
            if (includeFFMarkings) sb.append(this.linePrefix);
            sb.append(NOTE);
            String freeTextStr = buildFreeText(comment.getNote(), showEvidence, STOP, SEMICOLON);
            sb.append(freeTextStr);
            if (includeFFMarkings)
                lines.addAll(
                        FFLineWrapper.buildLines(sb.toString(), SEPS, linePrefix, LINE_LENGTH));
            else lines.add(sb.toString());
        }
        return lines;
    }
}
