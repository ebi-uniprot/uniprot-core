package uk.ac.ebi.uniprot.flatfile.parser.impl.cc;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.LineBuilderHelper;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.uniprot.comment.Cofactor;
import org.uniprot.core.uniprot.comment.CofactorComment;
import org.uniprot.core.uniprot.comment.CofactorReferenceType;

import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.*;


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
            boolean includeFFMarkings, boolean showEvidence, boolean includeCommentType) {
        List<String> lines = new ArrayList<>();
        // first line
        StringBuilder firstLine = new StringBuilder();
         if(includeCommentType)
        	 firstLine.append(buildStart(comment, includeFFMarkings));
        if(!Strings.isNullOrEmpty(comment.getMolecule())) {
            // if(includeFFMarkings)
            firstLine.append(SPACE);
            firstLine.append(comment.getMolecule()).append(":");
        }
        if (firstLine.length() > 0)
            lines.add(firstLine.toString());
        for (Cofactor cofactor : comment.getCofactors()) {
            StringBuilder sb = new StringBuilder();
            if (includeFFMarkings)
                sb.append(this.linePrefix);
            sb.append(NAME).append(cofactor.getName()).append(SEPARATOR_SEMICOLON);
            DBCrossReference<CofactorReferenceType> coRef = cofactor.getCofactorReference();
            sb.append(XREF).append(coRef.getDatabaseType().toDisplayName())
                    .append(":").append(coRef.getId())
                    .append(SEMICOLON);
            if (!cofactor.getEvidences().isEmpty() && showEvidence) {
                sb.append(SPACE);
                sb.append(EVIDENCE);
                String evStr = LineBuilderHelper.export(cofactor.getEvidences()).trim();
                sb.append(evStr).append(SEMICOLON);
            }
            if (includeFFMarkings) {
                List<String> lls = FFLineWrapper.buildLines(sb.toString(), SEPS, CC_PREFIX_INDENT, LINE_LENGTH);
                lines.addAll(lls);
            } else
                lines.add(sb.toString());

        }
        if(isValidNote(comment.getNote())) {
            StringBuilder sb = new StringBuilder();
            if (includeFFMarkings)
                sb.append(this.linePrefix);
            sb.append(NOTE);
            String freeTextStr= buildFreeText(comment.getNote(), showEvidence, STOP, SEMICOLON);
            sb.append(freeTextStr);        
            if (includeFFMarkings)
                lines.addAll(FFLineWrapper.buildLines(sb.toString(), SEPS, linePrefix, LINE_LENGTH));
            else
                lines.add(sb.toString());
        }
        return lines;
    }

}
