package org.uniprot.core.flatfile.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.comment.APIsoform;
import org.uniprot.core.uniprotkb.comment.AlternativeProductsComment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;
import org.uniprot.core.util.Utils;

public class SplicedSequenceCalculator {

    public static String getSplicedSequenceByName(UniProtKBEntry entry, String isoformName) {
        List<AlternativeProductsComment> apComments =
                entry.getCommentsByType(CommentType.ALTERNATIVE_PRODUCTS);
        Optional<APIsoform> opIsoform = findIsoformByName(apComments, isoformName);
        return getSplicedSequence(entry, opIsoform);
    }

    public static String getSplicedSequenceByIsoId(UniProtKBEntry entry, String isoformId) {
        List<AlternativeProductsComment> apComments =
                entry.getCommentsByType(CommentType.ALTERNATIVE_PRODUCTS);
        Optional<APIsoform> opIsoform = findByIsoformId(apComments, isoformId);
        return getSplicedSequence(entry, opIsoform);
    }

    private static String getSplicedSequence(UniProtKBEntry entry, Optional<APIsoform> opIsoform) {
        if (!opIsoform.isPresent()) return "";
        APIsoform isoform = opIsoform.get();
        List<String> sequenceIds = isoform.getSequenceIds();
        List<UniProtKBFeature> vsfeatures =
                new ArrayList<>(entry.getFeaturesByType(UniprotKBFeatureType.VAR_SEQ));
        String result = entry.getSequence().getValue();
        for (int iii = vsfeatures.size() - 1; iii > -1; iii--) {
            UniProtKBFeature feature = vsfeatures.get(iii);
            String featureID = feature.getFeatureId().getValue();
            for (String isoformSequenceId : sequenceIds) {
                if (featureID.equals(isoformSequenceId)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(result.substring(0, feature.getLocation().getStart().getValue() - 1));
                    if ((Utils.notNull(feature.getAlternativeSequence()))
                            && Utils.notNullNotEmpty(
                                    feature.getAlternativeSequence().getAlternativeSequences())) {
                        String alSequence =
                                feature.getAlternativeSequence().getAlternativeSequences().get(0);
                        sb.append(alSequence.replaceAll("\\s*", ""));
                    }
                    sb.append(result.substring(feature.getLocation().getEnd().getValue()));
                    result = sb.toString();
                }
            }
        }
        return result;
    }

    private static Optional<APIsoform> findIsoformByName(
            List<AlternativeProductsComment> apComments, String isoformName) {
        return apComments.stream()
                .flatMap(comment -> comment.getIsoforms().stream())
                .filter(isoform -> isoform.getName().getValue().equals(isoformName))
                .findFirst();
    }

    private static Optional<APIsoform> findByIsoformId(
            List<AlternativeProductsComment> apComments, String isoformId) {
        return apComments.stream()
                .flatMap(comment -> comment.getIsoforms().stream())
                .filter(isoform -> isoformHasId(isoform, isoformId))
                .findFirst();
    }

    private static boolean isoformHasId(APIsoform isoform, String isoformId) {
        return isoform.getIsoformIds().get(0).getValue().equalsIgnoreCase(isoformId);
    }
}
