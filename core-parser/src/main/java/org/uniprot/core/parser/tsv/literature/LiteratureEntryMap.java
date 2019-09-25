package org.uniprot.core.parser.tsv.literature;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.citation.Author;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 2019-07-04
 */
public class LiteratureEntryMap implements NamedValueMap {

    private final LiteratureEntry literatureEntry;

    public LiteratureEntryMap(LiteratureEntry literatureEntry) {
        this.literatureEntry = literatureEntry;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(literatureEntry.getPubmedId()));
        map.put("doi", Utils.nullToEmpty(literatureEntry.getDoiId()));
        map.put("title", Utils.nullToEmpty(literatureEntry.getTitle()));
        map.put("lit_abstract", Utils.nullToEmpty(literatureEntry.getLiteratureAbstract()));
        map.put("author", getAuthors());
        map.put("authoring_group", getAuthoringGroup());
        map.put("author_and_group", getAuthorsAndAuthoringGroups());
        map.put("journal", getJournal());
        map.put("publication", getPublication());
        map.put("reference", getReference());
        map.put("mapped_references", getMappedReferences());
        map.put("statistics", getStatistics());

        return map;
    }

    private String getPublication() {
        String result = "";
        if (literatureEntry.hasPublicationDate()) {
            result = literatureEntry.getPublicationDate().getValue();
        }
        return result;
    }

    private String getJournal() {
        String result = "";
        if (literatureEntry.hasJournal()) {
            result = literatureEntry.getJournal().getName();
        }
        return result;
    }

    private String getAuthorsAndAuthoringGroups() {
        String result = getAuthoringGroup();
        if (Utils.notNullOrEmpty(result)) {
            result += "; " + getAuthors();
        }
        return result;
    }

    private String getAuthors() {
        return literatureEntry.getAuthors().stream()
                .map(Author::getValue)
                .collect(Collectors.joining(", "));
    }

    private String getAuthoringGroup() {
        return String.join(", ", literatureEntry.getAuthoringGroup());
    }

    private String getMappedReferences() {
        return literatureEntry.getLiteratureMappedReferences().stream()
                .map(mapped -> mapped.getSource() + "; " +
                        mapped.getSourceId() + "; " +
                        String.join(", ", mapped.getSourceCategory()) + "; " +
                        mapped.getAnnotation())
                .collect(Collectors.joining(", "));
    }

    private String getReference() {
        StringBuilder result = new StringBuilder();
        if (literatureEntry.hasJournal()) {
            result.append(literatureEntry.getJournal().getName());
        }
        if (literatureEntry.hasVolume()) {
            result.append(" ").append(literatureEntry.getVolume());
        }
        if (result.length() > 0) {
            result.append(":");
        }
        if (literatureEntry.hasFirstPage()) {
            result.append(literatureEntry.getFirstPage());

            if (literatureEntry.hasLastPage()) {
                result.append("-").append(literatureEntry.getLastPage());
            }
        }
        if (literatureEntry.hasPublicationDate()) {
            result.append("(").append(literatureEntry.getPublicationDate().getValue()).append(")");
        }
        return result.toString();
    }

    private String getStatistics() {
        StringBuilder result = new StringBuilder();
        if (literatureEntry.hasStatistics()) {
            result.append("mapped:").append(literatureEntry.getStatistics().getMappedProteinCount()).append("; ")
                    .append("reviewed:").append(literatureEntry.getStatistics().getReviewedProteinCount()).append("; ")
                    .append("annotated:").append(literatureEntry.getStatistics().getUnreviewedProteinCount());
        }
        return result.toString();
    }

}
