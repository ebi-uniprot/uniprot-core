package uk.ac.ebi.uniprot.parser.impl.cc;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.LINE_LENGTH;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEMI_COMA;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEPARATOR_SEMICOMA;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEPS;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SPACE;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.STOP;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReference;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.LineBuilderHelper;


/**
 * CC -!- COFACTOR: CC Name=Mg(2+); Xref=ChEBI:CHEBI:18420; Evidence={ECO:0000255|HAMAP-Rule:MF_00086}; CC Name=Co(2+);
 * Xref=ChEBI:CHEBI:48828; Evidence={ECO:0000255|HAMAP-Rule:MF_00086}; CC Note=Binds 2 divalent ions per subunit
 * (magnesium or cobalt). CC {ECO:0000255|HAMAP-Rule:MF_00086}; CC -!- COFACTOR: Serine protease NS3: CC Name=Zn(2+);
 * Xref=ChEBI:CHEBI:29105; Evidence={ECO:0000269|PubMed:9060645}; CC Note=Binds 1 zinc ion per NS3 protease domain.;
 * 
 * @author jieluo
 *
 */
public class CCCofactorCommentLineBuilder extends CCLineBuilderAbstr<CofactorComment> {

    private static final String EVIDENCE = "Evidence=";
    private static final String XREF = "Xref=";

    @Override
    protected List<String> buildCommentLines(CofactorComment comment,
            boolean includeFFMarkings, boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        // first line
        StringBuilder firstLine = new StringBuilder();
        // if(includeFFMarkings)
        firstLine.append(buildStart(comment, includeFFMarkings));
        if(comment.getMolecule().isPresent()) {
            // if(includeFFMarkings)
            firstLine.append(SPACE);
            firstLine.append(comment.getMolecule().get()).append(":");
        }
        if (firstLine.length() > 0)
            lines.add(firstLine.toString());
        for (Cofactor cofactor : comment.getCofactors()) {
            StringBuilder sb = new StringBuilder();
            if (includeFFMarkings)
                sb.append(this.linePrefix);
            sb.append(NAME).append(cofactor.getName()).append(SEPARATOR_SEMICOMA);
            CofactorReference coRef = cofactor.getCofactorReference();
            sb.append(XREF).append(coRef.getCofactorReferenceType().toDisplayName())
                    .append(":").append(coRef.getReferenceId())
                    .append(SEMI_COMA);
            if (!cofactor.getEvidences().isEmpty()) {
                sb.append(SPACE);
                sb.append(EVIDENCE);
                String evStr = LineBuilderHelper.export(cofactor.getEvidences()).trim();
                sb.append(evStr).append(SEMI_COMA);
            }
            if (includeFFMarkings) {
                List<String> lls = FFLineWrapper.buildLines(sb.toString(), SEPS, CC_PREFIX_INDENT, LINE_LENGTH);
                lines.addAll(lls);
            } else
                lines.add(sb.toString());

        }
        if(comment.getNote().isPresent()) {
            StringBuilder sb = new StringBuilder();
            if (includeFFMarkings)
                sb.append(this.linePrefix);
            sb.append(NOTE);
            String freeTextStr= buildFreeText(comment.getNote().get(), showEvidence, STOP, SEMI_COMA);
            sb.append(freeTextStr);        
            if (includeFFMarkings)
                lines.addAll(FFLineWrapper.buildLines(sb.toString(), SEPS, linePrefix, LINE_LENGTH));
            else
                lines.add(sb.toString());
        }
        return lines;
    }

}
