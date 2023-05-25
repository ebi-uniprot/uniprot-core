package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.flatfile.parser.impl.cc.CatalyticActivityCCLineBuilder;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniprotkb.comment.CatalyticActivityComment;
import org.uniprot.core.uniprotkb.comment.Reaction;
import org.uniprot.core.uniprotkb.comment.ReactionDatabase;
import org.uniprot.core.util.Utils;

public class CatalyticActivityMap implements NamedValueMap {

    private final List<CatalyticActivityComment> catalyticActivityComments;
    private final CatalyticActivityCCLineBuilder lineBuilder = new CatalyticActivityCCLineBuilder();

    public CatalyticActivityMap(List<CatalyticActivityComment> catalyticActivityComments) {
        this.catalyticActivityComments = catalyticActivityComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getCatalyticActivityComments(this.catalyticActivityComments);
    }

    private Map<String, String> getCatalyticActivityComments(
            List<CatalyticActivityComment> catalyticActivityComments) {
        Map<String, String> catalyticActivityMap = new HashMap<>();
        if (catalyticActivityComments != null) {
            String catalyticActivityString =
                    catalyticActivityComments.stream()
                            .map(this::getCatalyticActivityString)
                            .collect(Collectors.joining(" "));

            catalyticActivityMap.put("cc_catalytic_activity", catalyticActivityString);
            String rheaIds = extractRheaIds(catalyticActivityComments);
            if (Utils.notNullNotEmpty(rheaIds)) {
                catalyticActivityMap.put("rhea", rheaIds);
            }
        }
        return catalyticActivityMap;
    }

    private String getCatalyticActivityString(CatalyticActivityComment catalyticActivityComment) {
        return lineBuilder.buildString(catalyticActivityComment, true, true).replaceAll("\n", " ");
    }

    private String extractRheaIds(List<CatalyticActivityComment> caComments) {
        return caComments.stream()
                .map(CatalyticActivityComment::getReaction)
                .filter(Reaction::hasReactionCrossReferences)
                .map(Reaction::getReactionCrossReferences)
                .flatMap(Collection::stream)
                .filter(this::filterOutRheaCompIds)
                .map(CrossReference::getId)
                .collect(Collectors.joining(" "));
    }
    private boolean filterOutRheaCompIds(CrossReference<ReactionDatabase> rr) {
        return ReactionDatabase.RHEA.equals(rr.getDatabase()) && !rr.getId().contains("RHEA-COMP:");
    }
}
