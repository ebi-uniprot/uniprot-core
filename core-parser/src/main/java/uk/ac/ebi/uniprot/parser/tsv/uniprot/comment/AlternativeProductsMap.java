package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.Value;
import uk.ac.ebi.uniprot.domain.uniprot.comment.APEventType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformId;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AlternativeProductsMap implements NamedValueMap {

    private final List<AlternativeProductsComment> alternativeProductsComments;

    public AlternativeProductsMap(List<AlternativeProductsComment> alternativeProductsComments) {
        this.alternativeProductsComments = alternativeProductsComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getAPComments(this.alternativeProductsComments);
    }

    private Map<String, String> getAPComments(List<AlternativeProductsComment> apComments) {
        Map<String, String> apCommentMap = new HashMap<>();
        if ((apComments != null)) {
            String result = apComments.stream()
                    .map(this::getAlternativeProductsCommentsString)
                    .collect(Collectors.joining("; "));
            apCommentMap.put("cc:alternative_products", "ALTERNATIVE PRODUCTS:  " +result+";");
        }
        return apCommentMap;
    }

    private String getAlternativeProductsCommentsString(AlternativeProductsComment alternativeProductsComment) {
        List<String> alternativeProductsStr = new ArrayList<>();
        if (alternativeProductsComment.hasEvents()) {
            alternativeProductsStr.add("Event=" + alternativeProductsComment.getEvents().stream()
                    .map(APEventType::getName)
                    .collect(Collectors.joining(", ")));
        }

        if (alternativeProductsComment.hasIsoforms()) {
            alternativeProductsStr.add("Named isoforms=" + alternativeProductsComment.getIsoforms().size());
            if (alternativeProductsComment.hasNote()) {
                alternativeProductsStr.add("Comment=" + alternativeProductsComment.getNote().getTexts().stream()
                        .map(EntryMapUtil::evidencedValueToString)
                        .collect(Collectors.joining("; ")));
            }
            alternativeProductsStr.add(alternativeProductsComment.getIsoforms().stream()
                    .map(this::getIsoformIdString)
                    .collect(Collectors.joining("; ")));
        }
        return String.join("; ",alternativeProductsStr);
    }

    private String getIsoformIdString(APIsoform apIsoform) {
        List<String> apIsoformStr = new ArrayList<>();
        if (apIsoform.hasName()) {
            apIsoformStr.add("Name=" + EntryMapUtil.evidencedValueToString(apIsoform.getName()));
        }
        if(apIsoform.hasSynonyms()){
            apIsoformStr.add("Synonyms=" + apIsoform.getSynonyms().stream()
                    .map(Value::getValue)
                    .collect(Collectors.joining(", ")));
        }
        if (apIsoform.hasIsoformIds()) {
            apIsoformStr.add("IsoId=" + apIsoform.getIsoformIds().stream()
                    .map(IsoformId::getValue)
                    .collect(Collectors.joining(", ")));
        }
        if (apIsoform.hasSequenceIds()) {
            apIsoformStr.add("Sequence=" + String.join(", ", apIsoform.getSequenceIds()));
        } else {
            apIsoformStr.add("Sequence=" + apIsoform.getIsoformSequenceStatus().toDisplayName());
        }
        if (apIsoform.hasNote()) {
            apIsoformStr.add(EntryMapUtil.getNoteStringWithoutDot(apIsoform.getNote()));
        }

        return String.join("; ", apIsoformStr);
    }

}
