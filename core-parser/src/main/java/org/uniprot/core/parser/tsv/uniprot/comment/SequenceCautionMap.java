package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.util.Utils;

import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CCSequenceCautionCommentLineBuilder;

public class SequenceCautionMap implements NamedValueMap {

    private final List<SequenceCautionComment> sequenceCautionComments;
    private final CCSequenceCautionCommentLineBuilder lineBuilder = new CCSequenceCautionCommentLineBuilder();

    public SequenceCautionMap(List<SequenceCautionComment> sequenceCautionComments){
        this.sequenceCautionComments = sequenceCautionComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getSeqCautionComments(this.sequenceCautionComments);
    }

    private Map<String, String> getSeqCautionComments(List<SequenceCautionComment> scComments) {
        Map<String, String> sequenceCautionMap = new HashMap<>();
        if (Utils.notEmpty(scComments)) {
            String sequenceCautions = scComments.stream()
                    .map(this::sequenceCautionToString)
                    .collect(Collectors.joining("  "));
            sequenceCautionMap.put("cc:sequence_caution", "SEQUENCE CAUTION:  " + sequenceCautions);
        }
        return sequenceCautionMap;
    }

    private String sequenceCautionToString(SequenceCautionComment sequenceCautionComment) {   	
    	return lineBuilder.buildString(sequenceCautionComment, true, false).replaceAll("\n",  " ");
    }

}
