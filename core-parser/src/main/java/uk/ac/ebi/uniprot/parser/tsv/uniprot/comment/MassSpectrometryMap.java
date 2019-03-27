package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CCMassSpecCommentLineBuilder;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

public class MassSpectrometryMap implements NamedValueMap {

    private final List<MassSpectrometryComment> massSpectrometryComments;
    private final CCMassSpecCommentLineBuilder lineBuilder =new CCMassSpecCommentLineBuilder();
    public MassSpectrometryMap(List<MassSpectrometryComment> massSpectrometryComments){
        this.massSpectrometryComments = massSpectrometryComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getMassSpecComments(this.massSpectrometryComments);
    }

    private Map<String, String>  getMassSpecComments(List<MassSpectrometryComment> msComments) {
        Map<String, String> massSpectrometryCommentMap = new HashMap<>();
        if ((msComments != null && !msComments.isEmpty())) {
            String result = msComments.stream()
                    .map(this::mapMassSpectrometryCommentToString)
                    .collect(Collectors.joining(" "));
            massSpectrometryCommentMap.put("cc:mass_spectrometry", result);
        }
        return massSpectrometryCommentMap;
    }

    private String mapMassSpectrometryCommentToString(MassSpectrometryComment massSpectrometryComment) {
    	return lineBuilder.buildString(massSpectrometryComment, true, true).replaceAll("\n", " ");
    }

}
