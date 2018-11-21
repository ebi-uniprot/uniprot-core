package uk.ac.ebi.uniprot.domain.citation.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.citation.Unpublished;
@JsonInclude(JsonInclude.Include.NON_EMPTY)  
public class UnpublishedImpl extends AbstractCitationImpl implements Unpublished {
    @JsonCreator
    public UnpublishedImpl(@JsonProperty("authoringGroup") List<String> authoringGroup,
			@JsonProperty("authors") List<Author> authors, @JsonProperty("citationXrefs") CitationXrefs citationXrefs,
			@JsonProperty("title") String title, @JsonProperty("publicationDate") PublicationDate publicationDate) {
        super(CitationType.UNPUBLISHED, authoringGroup, authors, citationXrefs, title, publicationDate);

    }
    

}
