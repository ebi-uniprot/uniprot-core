package org.uniprot.core.parser.tsv.literature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.*;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 2019-07-04
 */
public class LiteratureEntryValueMapper implements EntityValueMapper<LiteratureEntry> {

    @Override
    public Map<String, String> mapEntity(LiteratureEntry entry, List<String> fields) {
        Citation literature = entry.getCitation();
        Map<String, String> map = new HashMap<>();
        map.put("id", getPubmedId(literature));
        map.put("doi", getDoiId(literature));
        map.put("title", getTittle(literature));
        map.put("authors", getAuthors(literature));
        map.put("authoring_group", getAuthoringGroup(literature));
        map.put("author_and_group", getAuthorsAndAuthoringGroups(literature));
        map.put("publication_date", getPublication(literature));
        if(literature instanceof JournalArticle) {
            JournalArticle journalArticle = (JournalArticle) literature;
            map.put("journal", getJournal(journalArticle));
            map.put("first_page", getFirstPage(journalArticle));
            map.put("last_page", getLastPage(journalArticle));
            map.put("volume", getVolume(journalArticle));
            map.put("reference", getReference(journalArticle));
        } else {
            map.put("journal", "");
            map.put("first_page", "");
            map.put("last_page", "");
            map.put("volume", "");
            map.put("reference", "");
        }
        if(literature instanceof Literature) {
            Literature lit = (Literature) literature;
            map.put("lit_abstract", getAbstract(lit));
        } else {
            map.put("lit_abstract", "");
        }
        map.put("statistics", getStatistics(entry));

        return map;
    }

    private String getLastPage(JournalArticle literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = Utils.emptyOrString(literature.getLastPage());
        }
        return result;
    }

    private String getFirstPage(JournalArticle literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = Utils.emptyOrString(literature.getFirstPage());
        }
        return result;
    }

    private String getVolume(JournalArticle literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = Utils.emptyOrString(literature.getVolume());
        }
        return result;
    }

    private String getAbstract(Literature literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = Utils.emptyOrString(literature.getLiteratureAbstract());
        }
        return result;
    }

    private String getTittle(Citation literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = Utils.emptyOrString(literature.getTitle());
        }
        return result;
    }

    private String getDoiId(Citation literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = literature.getCitationCrossReferenceByType(CitationDatabase.DOI)
                    .map(CrossReference::getId)
                    .orElse("");
        }
        return result;
    }

    private String getPubmedId(Citation literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = literature.getCitationCrossReferenceByType(CitationDatabase.PUBMED)
                    .map(CrossReference::getId)
                    .orElse("");
        }
        return result;
    }

    private String getPublication(Citation literature) {
        String result = "";
        if (Utils.notNull(literature) && literature.hasPublicationDate()) {
            result = literature.getPublicationDate().getValue();
        }
        return result;
    }

    private String getJournal(JournalArticle literature) {
        String result = "";
        if (Utils.notNull(literature) && literature.hasJournal()) {
            result = literature.getJournal().getName();
        }
        return result;
    }

    private String getAuthorsAndAuthoringGroups(Citation literature) {
        String result = getAuthoringGroup(literature);
        if (Utils.notNullNotEmpty(result)) {
            result += "; " + getAuthors(literature);
        }
        return result;
    }

    private String getAuthors(Citation literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result =
                    literature.getAuthors().stream()
                            .map(Author::getValue)
                            .collect(Collectors.joining(", "));
        }
        return result;
    }

    private String getAuthoringGroup(Citation literature) {
        String result = "";
        if (Utils.notNull(literature)) {
            result = String.join(", ", literature.getAuthoringGroups());
        }
        return result;
    }

    private String getReference(JournalArticle literature) {
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
                    .append(literatureEntry.getStatistics().getComputationallyMappedProteinCount())
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
