package uk.ac.ebi.uniprot.parser.tsv.literature;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.literature.LiteratureEntry;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        if (Utils.notEmpty(result)) {
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
        String result = "";
        if (literatureEntry.hasJournal()) {
            result += literatureEntry.getJournal().getName();
        }
        if (literatureEntry.hasVolume()) {
            result += " " + literatureEntry.getVolume();
        }
        if (Utils.notEmpty(result)) {
            result = result.trim() + ":";
        }
        if (literatureEntry.hasFirstPage()) {
            result += literatureEntry.getFirstPage();

            if (literatureEntry.hasLastPage()) {
                result += "-" + literatureEntry.getLastPage();
            }
        }
        if (literatureEntry.hasPublicationDate()) {
            result += "(" + literatureEntry.getPublicationDate().getValue() + ")";
        }
        return result.trim();
    }

    private String getStatistics() {
        String result = "";
        if (literatureEntry.hasStatistics()) {
            result = "mapped:" + literatureEntry.getStatistics().getMappedProteinCount() + "; " +
                    "reviewed:" + literatureEntry.getStatistics().getReviewedProteinCount() + "; " +
                    "annotated:" + literatureEntry.getStatistics().getUnreviewedProteinCount();
        }
        return result;
    }

}
