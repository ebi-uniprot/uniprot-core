package uk.ac.ebi.uniprot.flatfile.parser.impl.cc;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineWrapper;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.*;

public class CCDiseaseCommentLineBuilder extends CCLineBuilderAbstr<DiseaseComment> {

    @Override
    protected List<String> buildCommentLines(DiseaseComment comment,
                                             boolean includeFFMarkings, boolean showEvidence, boolean includeCommentType) {
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkings) {
            addFlatFileMarkingsIfRequired(includeFFMarkings, sb);
        }
        if(includeCommentType)
        	addCommentTypeName(comment, sb);


        //if the disease is defined then in needs to be represented in the string
        boolean needSpace = false;
        if (comment.hasDefinedDisease()) {
            sb.append(createDiseaseString(comment.getDisease()));
            sb = addEvidence(comment.getDisease(), sb, showEvidence, STOP);
            needSpace = true;
        }

        //append the note
        if (isValidNote(comment.getNote())) {
            if (needSpace)
                sb.append(SPACE);
            sb.append(NOTE);
            boolean isfirst = true;
            for (EvidencedValue val : comment.getNote().getTexts()) {
                if (!isfirst)
                    sb.append(SPACE);
                sb.append(val.getValue());
                appendIfNot(sb, STOP);
                sb = addEvidence(val, sb, showEvidence, STOP);
                isfirst = false;
            }
        }
        if (includeFFMarkings) {
            return FFLineWrapper.buildLines(sb.toString(), SEPS, linePrefix, LINE_LENGTH);
        } else {
            List<String> lines = new ArrayList<>();
            lines.add(sb.toString());
            return lines;
        }
    }

    private String createDiseaseString(Disease disease) {
        String diseaseString = "";

        diseaseString += disease.getDiseaseId() + " "
                + "(" + disease.getAcronym() + ") "
                + "[" + disease.getReference().getDatabaseType().toDisplayName() + ":" + disease.getReference()
                .getId() + "]: "
                + disease.getDescription();

        return diseaseString;
    }

}
