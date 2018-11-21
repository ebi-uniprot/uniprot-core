package uk.ac.ebi.uniprot.domain.citation.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.Journal;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JournalArticleImpl extends AbstractCitationImpl implements JournalArticle {
    
    private final Journal journal;
    private final String firstPage;
    private final String lastPage;
    private final String volume;
    public JournalArticleImpl(
    		 List<String> authoringGroup,
			 List<Author> authors, CitationXrefs citationXrefs,
			 String title, PublicationDate publicationDate,
		String journalName,
			String firstPage,  String lastPage, 
		 String volume) {
    	this(authoringGroup, authors, citationXrefs, title, publicationDate,
    			new JournalImpl(journalName), firstPage, lastPage, volume);
    }
    
	@JsonCreator
    public JournalArticleImpl(
    		@JsonProperty("authoringGroup") List<String> authoringGroup,
			@JsonProperty("authors") List<Author> authors, @JsonProperty("citationXrefs") CitationXrefs citationXrefs,
			@JsonProperty("title") String title, @JsonProperty("publicationDate") PublicationDate publicationDate,
			@JsonProperty("journal") Journal journal,
			@JsonProperty("firstPage") String firstPage, @JsonProperty("lastPage") String lastPage, 
			@JsonProperty("volume") String volume) {
        super(CitationType.JOURNAL_ARTICLE, authoringGroup, authors, citationXrefs, title, publicationDate);
        this.journal = journal;
        this.firstPage = resetNull(firstPage);
		this.lastPage = resetNull(lastPage);
		this.volume =  resetNull(volume);

    }
	
    @Override
    public Journal getJournal() {
        return journal;
    }

    @Override
    public String getFirstPage() {
       return firstPage;
    }

    @Override
    public String getLastPage() {
       return lastPage;
    }

    @Override
    public String getVolume() {
       return volume;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((firstPage == null) ? 0 : firstPage.hashCode());
		result = prime * result + ((journal == null) ? 0 : journal.hashCode());
		result = prime * result + ((lastPage == null) ? 0 : lastPage.hashCode());
		result = prime * result + ((volume == null) ? 0 : volume.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JournalArticleImpl other = (JournalArticleImpl) obj;
		if (firstPage == null) {
			if (other.firstPage != null)
				return false;
		} else if (!firstPage.equals(other.firstPage))
			return false;
		if (journal == null) {
			if (other.journal != null)
				return false;
		} else if (!journal.equals(other.journal))
			return false;
		if (lastPage == null) {
			if (other.lastPage != null)
				return false;
		} else if (!lastPage.equals(other.lastPage))
			return false;
		if (volume == null) {
			if (other.volume != null)
				return false;
		} else if (!volume.equals(other.volume))
			return false;
		return true;
	}


}
