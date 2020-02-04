package org.uniprot.core.parser.tsv.literature;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.Literature;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 2019-07-04
 */
public class LiteratureEntryMap implements NamedValueMap {

    private final LiteratureEntry literatureEntry;
    private final Literature literature;

    public LiteratureEntryMap(LiteratureEntry literatureEntry) {
        this.literatureEntry = literatureEntry;
        this.literature = (Literature) literatureEntry.getCitation();
    }

    @Override
    public Map<String, String> attributeValues() {

        Map<String, String> map = new HashMap<>();
        map.put("id", getPubmedId());
        map.put("doi", getDoiId());
        map.put("title", getTittle());
        map.put("lit_abstract", getAbstract());
        map.put("author", getAuthors());
        map.put("authoring_group", getAuthoringGroup());
        map.put("author_and_group", getAuthorsAndAuthoringGroups());
        map.put("journal", getJournal());
        map.put("publication", getPublication());
        map.put("reference", getReference());
        map.put("statistics", getStatistics());

        return map;
    }

    private String getAbstract() {
        String result = "";
        if (Utils.notNull(literature)) {
            result = Utils.emptyOrString(literature.getLiteratureAbstract());
        }
        return result;
    }

    private String getTittle() {
        String result = "";
        if (Utils.notNull(literature)) {
            result = Utils.emptyOrString(literature.getTitle());
        }
        return result;
    }

    private String getDoiId() {
        String result = "";
        if (Utils.notNull(literature)) {
            result = Utils.emptyOrString(literature.getDoiId());
        }
        return result;
    }

    private String getPubmedId() {
        String result = "";
        if (Utils.notNull(literature)) {
            result = String.valueOf(literature.getPubmedId());
        }
        return result;
    }

    private String getPublication() {
        String result = "";
        if (Utils.notNull(literature) && literature.hasPublicationDate()) {
            result = literature.getPublicationDate().getValue();
        }
        return result;
    }

    private String getJournal() {
        String result = "";
        if (Utils.notNull(literature) && literature.hasJournal()) {
            result = literature.getJournal().getName();
        }
        return result;
    }

    private String getAuthorsAndAuthoringGroups() {
        String result = getAuthoringGroup();
        if (Utils.notNullNotEmpty(result)) {
            result += "; " + getAuthors();
        }
        return result;
    }

    private String getAuthors() {
        String result = "";
        if (Utils.notNull(literature)) {
            result =
                    literature.getAuthors().stream()
                            .map(Author::getValue)
                            .collect(Collectors.joining(", "));
        }
        return result;
    }

    private String getAuthoringGroup() {
        String result = "";
        if (Utils.notNull(literature)) {
            result = String.join(", ", literature.getAuthoringGroups());
        }
        return result;
    }

    private String getReference() {
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

    private String getStatistics() {
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
