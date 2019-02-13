package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.APEventType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformId;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

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
            String result = apComments.stream().map(alternativeProductsComment -> {
                String alternativeProductsStr = "";
                if (alternativeProductsComment.getEvents() != null && !alternativeProductsComment.getEvents().isEmpty()) {
                    alternativeProductsStr = "Event=" + alternativeProductsComment.getEvents().stream()
                            .map(APEventType::getName)
                            .collect(Collectors.joining(", ")) + ";";
                }

                if (alternativeProductsComment.getIsoforms() != null && !alternativeProductsComment.getIsoforms().isEmpty()) {
                    alternativeProductsStr += " Named isoforms=" + alternativeProductsComment.getIsoforms().size() + ";";
                    if (alternativeProductsComment.getNote() != null) {
                        alternativeProductsStr += "Comment=" + alternativeProductsComment.getNote().getTexts()
                                .stream().map(EntryMapUtil::evidencedValueToString)
                                .collect(Collectors.joining("; ")) + ";";
                    }
                    alternativeProductsStr += " " + alternativeProductsComment.getIsoforms().stream()
                            .map(apIsoform -> {
                                String apIsoformStr = "";
                                if (apIsoform.getName() != null) {
                                    apIsoformStr += " Name=" + EntryMapUtil.evidencedValueToString(apIsoform.getName()) + ";";
                                }
                                if (apIsoform.getIsoformIds() != null && !apIsoform.getIsoformIds().isEmpty()) {
                                    apIsoformStr += " IsoId=" + apIsoform.getIsoformIds().stream()
                                            .map(IsoformId::getValue)
                                            .collect(Collectors.joining(", ")) + ";";
                                }
                                if (apIsoform.getSequenceIds() != null && !apIsoform.getSequenceIds().isEmpty()) {
                                    apIsoformStr += " Sequence=" + String.join(", ", apIsoform.getSequenceIds()) + ";";
                                } else {
                                    apIsoformStr += " Sequence=" + apIsoform.getIsoformSequenceStatus().getValue() + ";";
                                }
                                if (apIsoform.getNote() != null) {
                                    apIsoformStr += EntryMapUtil.getNoteString(apIsoform.getNote()) + ";";
                                }

                                return apIsoformStr;
                            })
                            .collect(Collectors.joining(""));
                }
                return alternativeProductsStr;
            }).collect(Collectors.joining("; "));
            apCommentMap.put("cc:alternative_products", "ALTERNATIVE PRODUCTS:  " +result);
        }
        return apCommentMap;
    }

}
