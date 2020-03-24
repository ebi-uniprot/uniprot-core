package org.uniprot.core.parser.tsv.literature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Literature;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 2019-07-04
 */
public class LiteratureEntryMapper implements EntityValueMapper<LiteratureEntry> {

    @Override
    public Map<String, String> mapEntity(LiteratureEntry entry, List<String> fields) {
        Literature literature = (Literature) entry.getCitation();
        Map<String, String> map = new HashMap<>();
        map.put("id", getPubmedId(literature));
        map.put("doi", getDoiId(literature));
        map.put("title", getTittle(literature));
        map.put("lit_abstract", getAbstract(literature));
        map.put("author", getAuthors(literature));
        map.put("authoring_group", getAuthoringGroup(literature));
        map.put("author_and_group", getAuthorsAndAuthoringGroups(literature));
        map.put("journal", getJournal(literature));
        map.put("publication", getPublication(literature));
        map.put("reference", getReference(literature));
        map.put("statistics", getStatistics(entry));

        return map;
    }

    private String getAbstract(Literature literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = Utils.emptyOrString(literature.getLiteratureAbstract());
        }
        return result;
    }

    private String getTittle(Literature literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = Utils.emptyOrString(literature.getTitle());
        }
        return result;
    }

    private String getDoiId(Literature literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = Utils.emptyOrString(literature.getDoiId());
        }
        return result;
    }

    private String getPubmedId(Literature literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = String.valueOf(literature.getPubmedId());
        }
        return result;
    }

    private String getPublication(Literature literature) {
        String result = "";
        if (Utils.notNull(literature) && literature.hasPublicationDate()) {
            result = literature.getPublicationDate().getValue();
        }
        return result;
    }

    private String getJournal(Literature literature) {
        String result = "";
        if (Utils.notNull(literature) && literature.hasJournal()) {
            result = literature.getJournal().getName();
        }
        return result;
    }

    private String getAuthorsAndAuthoringGroups(Literature literature) {
        String result = getAuthoringGroup(literature);
        if (Utils.notNullNotEmpty(result)) {
            result += "; " + getAuthors(literature);
        }
        return result;
    }

    private String getAuthors(Literature literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result =
                    literature.getAuthors().stream()
                            .map(Author::getValue)
                            .collect(Collectors.joining(", "));
        }
        return result;
    }

    private String getAuthoringGroup(Literature literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = String.join(", ", literature.getAuthoringGroups());
        }
        return result;
    }

    private String getReference(Literature literature) {
        StringBuilder result = new StringBuilder();
        if (Utils.notNull(literature)) {
            if (literature.hasJournal()) {
                result.append(literature.getJournal().getName());
            }
            if (literature.hasVolume()) {
                result.append(" ").append(literature.getVolume());
            }
            if (result.length() > 0) {
                result.append(":");
            }
            if (literature.hasFirstPage()) {
                result.append(literature.getFirstPage());

                if (literature.hasLastPage()) {
                    result.append("-").append(literature.getLastPage());
                }
            }
            if (literature.hasPublicationDate()) {
                result.append("(").append(literature.getPublicationDate().getValue()).append(")");
            }
        }
        return result.toString();
    }

    private String getStatistics(LiteratureEntry literatureEntry) {
        StringBuilder result = new StringBuilder();
        if (literatureEntry.hasStatistics()) {
            result.append("mapped:")
                    .append(literatureEntry.getStatistics().getMappedProteinCount())
                    .append("; ")
                    .append("reviewed:")
                    .append(literatureEntry.getStatistics().getReviewedProteinCount())
                    .append("; ")
                    .append("annotated:")
                    .append(literatureEntry.getStatistics().getUnreviewedProteinCount());
        }
        return result.toString();
    }
}
